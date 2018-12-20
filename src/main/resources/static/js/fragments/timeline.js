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
    let column_semAttendance = $("th.sem-attendances-cell, td.sem-attendances-cell");
    let column_labAttendance = $("th.lab-attendances-cell, td.lab-attendances-cell");
    let column_bonus = $("th.bonus-cell, td.bonus-cell");
    //it is possible that average column should not be shown
    let columnAverageWidth = getColumnWidth(column_average);
    let columnLabAttendanceWidth = getColumnWidth(column_labAttendance);
    let columnSemAttendanceWidth = getColumnWidth(column_semAttendance);
    $(column_average).css({
        "position": "sticky",
        "right": 0
    });
    $(column_labAttendance).css({
        "position": "sticky",
        "right": `${columnAverageWidth}px`
    });
    $(column_semAttendance).css({
        "position": "sticky",
        "right": `${columnAverageWidth + columnLabAttendanceWidth}px`
    });
    $(column_bonus).css({
        "position": "sticky",
        "right": `${columnAverageWidth + columnLabAttendanceWidth + columnSemAttendanceWidth}px`
    });
}

function getColumnWidth(column) {
    return $(column).length > 0 ? $(column).outerWidth() : 0;
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
    let nrOfLaboratories = getNrOfLessonsOfType(enrollments[enrlId], "LABORATORY"),
        nrOfSeminars = getNrOfLessonsOfType(enrollments[enrlId], "SEMINAR");
    let totalLabAttendance = (nrOfLaboratories > 0 ? (nrOfLabAttendances / nrOfLaboratories) * 100 : undefined);
    let totalSemAttendance = (nrOfSeminars > 0 ? (nrOfSemAttendances / nrOfSeminars) * 100 : undefined);
    enrollments[enrlId].seminarAttendance = totalSemAttendance;
    enrollments[enrlId].laboratoryAttendance = totalLabAttendance;
    updateTotalAttendanceView(enrlRow, totalLabAttendance, totalSemAttendance);
}

function updateTotalAttendanceView(enrlRow, totalLabAttendance, totalSemAttendance) {
    let semAttendanceCell = $(enrlRow).children("td.sem-attendances-cell").first();
    let labAttendanceCell = $(enrlRow).children("td.lab-attendances-cell").first();
    if (totalSemAttendance !== undefined) {
        $(semAttendanceCell).text(`${totalSemAttendance.toFixed(2)}%`);
    } else {
        $(semAttendanceCell).text('');
    }
    if (totalLabAttendance !== undefined) {
        $(labAttendanceCell).text(`${totalLabAttendance.toFixed(2)}%`);
    } else {
        $(labAttendanceCell).text('');
    }
    if (!hasMinimumAttendance(totalSemAttendance, 100)) {
        $(semAttendanceCell).addClass('err');
    } else {
        $(semAttendanceCell).removeClass('err');
    }
    if (!hasMinimumAttendance(100, totalLabAttendance)) {
        $(labAttendanceCell).addClass('err');
    } else {
        $(labAttendanceCell).removeClass('err');
    }
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

function getNrOfLessonsOfType(enrollment, lessonType) {
    return enrollment.course.lessonTemplates.filter((lessonTemplate) => {
        return lessonTemplate.type === lessonType;
    }).length;
}

function hasMinimumGrade(grade) {
    return grade >= 5;
}
