convertEnrollmentListToEnrollmentMap();

$(document).ready(function () {
    stickRightColumns();
    updateAll()
});


function convertEnrollmentListToEnrollmentMap() {
    let enrollmentList = enrollments, enrollmentMap = {};
    for (let i = 0; i < enrollmentList.length; i++) {
        let enrollment = {};
        let lessonMap = {}, examMap = {};
        let lessonList = enrollmentList[i].lessons, examList = enrollmentList[i].partialExams;
        for (let key in enrollmentList[i]) {
            enrollment[key] = enrollmentList[i][key];
        }
        for (let j = 0; j < lessonList.length; j++) {
            let lesson = lessonList[j];
            lessonMap[lesson.id] = lesson;
        }
        for (let j = 0; j < examList.length; j++) {
            let exam = examList[j];
            examMap[exam.id] = exam;
        }
        enrollment.lessons = lessonMap;
        enrollment.partialExams = examMap;
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
            switch (lessons[id].type) {
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

function updateTotalAttendanceView(enrlRow, totalLabAttendance, totalSemAttendance) {
    let totalAttendanceCell = $(enrlRow).children("td.attendances-cell").first();
    let precisionLab = (totalLabAttendance < 100 ? 2 : 0), precisionSem = (totalSemAttendance < 100 ? 2 : 0);
    debugger;
    let totalAttendanceViewText;
    totalAttendanceViewText = `${totalSemAttendance.toFixed(precisionSem)}% / ${totalLabAttendance.toFixed(precisionLab)}%`;
    console.log(totalAttendanceViewText)
    $(totalAttendanceCell).text(totalAttendanceViewText);
    if (!hasMinimumAttendance(totalSemAttendance, totalLabAttendance)) {
        $(totalAttendanceCell).addClass('err');
    } else {
        $(totalAttendanceCell).removeClass('err');
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
    debugger
    console.log(average)
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

function hasMinimumAttendance(seminarAttendance, laboratoryAttendance) {
    const MIN_SEM_ATT = 75, MIN_LAB_ATT = 90;
    return seminarAttendance >= MIN_SEM_ATT && laboratoryAttendance >= MIN_LAB_ATT;
}

function hasMinimumGrade(grade) {
    return grade >= 5;
}