convertEnrollmentListToEnrollmentMap();

$(document).ready(function () {
    updateAll();
    stickRightColumns();
});

function convertEnrollmentListToEnrollmentMap() {
    let enrollmentList = enrollments, enrollmentMap = {};
    for (let i = 0; i < enrollmentList.length; i++) {
        let enrollment = {};
        let lessonMap = {};
        let lessonList = enrollmentList[i].lessons;
        for (let key in enrollmentList[i]) {
            enrollment[key] = enrollmentList[i][key];
        }
        for (let j = 0; j < lessonList.length; j++) {
            let lesson = lessonList[j];
            lessonMap[lesson.id] = lesson;
        }
        enrollment.lessons = lessonMap;
        enrollmentMap[enrollment.id] = enrollment;
    }
    enrollments = enrollmentMap;
}


function stickRightColumns() {
    let column_average = $("th.average-cell, td.average-cell");
    let column_attendance = $("th.attendances-cell, td.attendances-cell");
    let column_bonus = $("th.bonus-cell, td.bonus-cell");
    //it is possible that average column should not be shown
    let columnAverageWidth = ($(column_average).length > 0 ? $(column_average).outerWidth() : 0);
    $(column_average).css({
        "position": "sticky",
        "right": 0
    });
    $(column_attendance).css({
        "position": "sticky",
        "right": `${columnAverageWidth}px`
    });
    $(column_bonus).css({
        "position": "sticky",
        "right": `${columnAverageWidth + $(column_attendance).outerWidth()}px`
    });
}

function updateAll() {
    let enrlRows = $(".timeline tr.enrollment-row");
    $(enrlRows).each((index, enrlRow) => {
        updateTotalBonus(enrlRow);
        updateTotalAttendance(enrlRow);
        updateAverageGrade(enrlRow);
    });
}

function updateTotalBonus(enrlRow) {
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let lessons = enrollments[enrlId].lessons;
    let totalBonusValue = 0;
    for (let id in lessons) {
        if (lessons[id].bonus != undefined) {
            totalBonusValue += lessons[id].bonus;
        }
    }
    enrollments[enrlId].totalBonus = totalBonusValue;
    updateTotalBonusView(enrlRow, totalBonusValue);
}

function updateTotalBonusView(enrlRow, value) {
    let totalBonusCell = $(enrlRow).children("td.bonus-cell").first();
    $(totalBonusCell).text(value);
}

function updateTotalAttendance(enrlRow) {
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let lessons = enrollments[enrlId].lessons;
    let nrOfLabAttendances = 0, nrOfSemAttendances = 0;
    for (let id in lessons) {
        if (lessons[id].attended) {
            switch (lessons[id].template.type) {
                case "LABORATORY":
                    nrOfLabAttendances++;
                    break;
                case "SEMINAR":
                    nrOfSemAttendances++;
                    break;
            }
        }
    }
    let nrOfLaboratories = getNrOfLaboratories(), nrOfSeminars = getNrOfSeminars();
    let totalLabAttendance = (nrOfLaboratories > 0 ? (nrOfLabAttendances / nrOfLaboratories) * 100 : 100);
    let totalSemAttendance = (nrOfSeminars > 0 ? (nrOfSemAttendances / nrOfSeminars) * 100 : 100);
    enrollments[enrlId].seminarAttendance = totalSemAttendance;
    enrollments[enrlId].laboratoryAttendance = totalLabAttendance;
    updateTotalAttendanceView(enrlRow, totalLabAttendance, totalSemAttendance);
}

function updateAverageGrade(enrlRow) {
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let n = 0, sum = 0;
    let lessons = enrollments[enrlId].lessons;
    for (let id in lessons) {
        if (lessons[id].grade != undefined) {
            sum += lessons[id].grade;
            n++;
        }
    }
    let average = (n > 0 ? sum / n : 0.0);
    enrollments[enrlId].averageGrade = average;
    updateAverageGradeView(enrlRow, average);
}

function updateAverageGradeView(enrlRow, average) {
    let averageCell = $(enrlRow).children("td.average-cell").first();
    $(averageCell).text(average.toFixed(2));
    if (!hasMinimumGrade(average)) {
        $(averageCell).addClass('err');
    } else {
        $(averageCell).removeClass('err');
    }
}

function getNrOfLaboratories() {
    for (let key in enrollments) {
        return enrollments[key].course.nrOfLaboratories;
    }
}

function getNrOfSeminars() {
    for (let key in enrollments) {
        return enrollments[key].course.nrOfSeminars;
    }
}

function hasMinimumGrade(grade) {
    return grade >= 5;
}
