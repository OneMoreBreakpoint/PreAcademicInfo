$(document).ready(function () {
    stickRightColumns();
    console.log(enrollments);
    assignHandlers();
});

(function convertEnrollmentListToEnrollmentMap() {
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
})();

function stickRightColumns() {
    let column_average = $("th:nth-last-child(1), td:nth-last-child(1)");
    let column_attendance = $("th:nth-last-child(2), td:nth-last-child(2)");
    let column_bonus = $("th:nth-last-child(3), td:nth-last-child(3)");
    $(column_average).css({
        "position": "sticky",
        "right": 0
    });
    $(column_attendance).css({
        "position": "sticky",
        "right": `${$(column_average).outerWidth()}px`
    });
    $(column_bonus).css({
        "position": "sticky",
        "right": `${$(column_average).outerWidth() + $(column_attendance).outerWidth()}px`
    });
}

function assignHandlers() {
    let enrlRows = $(".timeline tbody > tr");
    $(enrlRows).each((index, enrlRow) => {
        let photoCell = $(enrlRow).children("td.photo-cell").first();
        let nameCell = $(enrlRow).children("td.name-cell").first();
        let lessonCells = $(enrlRow).children("td.lesson-cell");
        let examCells = $(enrlRow).children("td.exam-cell");
        $(photoCell).click(() => {
            //TODO: open modal
        });
        $(nameCell).click(() => {
            //TODO: open modal
        });
        $(lessonCells).each((index, lessonCell) => {
            assignLessonCellHandlers(enrlRow, lessonCell);
        });
        $(examCells).each((index, examCell) => {
            assignExamCellHandlers(enrlRow, examCell);
        });
    })
}

function assignLessonCellHandlers(enrlRow, lessonCell) {
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let attendanceField = $(lessonCell).find("input.attendance-field");
    let gradeField = $(lessonCell).find("input.grade-field");
    let bonusField = $(lessonCell).find("input.bonus-field");
    $(attendanceField).change((event) => {
        let lessonId = getNumericIdFromDomId(lessonCell.id);
        let checkedState = $(event.target).is(':checked');
        enrollments[enrlId].lessons[lessonId].attended = checkedState;
        updateTotalAttendance(enrlRow);
    });
    $(gradeField).change((event) => {
        let lessonId = getNumericIdFromDomId(lessonCell.id);
        let actualValue = getValidGrade(event.target.value);
        if(actualValue != event.target.value){
            $(gradeField).val(actualValue);
        }
        enrollments[enrlId].lessons[lessonId].grade = actualValue;
        updateAverageGrade(enrlRow);
    });
    $(bonusField).change((event) => {
        let lessonId = getNumericIdFromDomId(lessonCell.id);
        let actualValue = getValidBonus(event.target.value);
        if(actualValue != event.target.value){
            $(bonusField).val(actualValue);
        }
        enrollments[enrlId].lessons[lessonId].bonus = actualValue;
        updateTotalBonus(enrlRow);
    });
}

function assignExamCellHandlers(enrlRow, examCell) {
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let gradeField = $(examCell).find("input.grade-field");
    $(gradeField).change((event) => {
        let examId = getNumericIdFromDomId(examCell.id);
        let actualValue = getValidGrade(event.target.value);
        if(actualValue != event.target.value){
            $(gradeField).val(actualValue);
        }
        enrollments[enrlId].lessons[examId].grade = actualValue;
        updateAverageGrade(enrlRow);
    });
}

function updateTotalBonus(enrlRow) {
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let totalBonusCell = $(enrlRow).children("td.bonus-cell").first();
    let lessons = enrollments[enrlId].lessons;
    let totalBonusValue = 0;
    for (let id in lessons) {
        if(lessons[id].bonus != undefined){
            totalBonusValue += lessons[id].bonus;
        }
    }
    $(totalBonusCell).text(totalBonusValue);
}

function updateTotalAttendance(enrlRow) {
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let totalAttendanceCell = $(enrlRow).children("td.attendances-cell").first();
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
    let precisionLab = (totalLabAttendance < 100 ? 2 : 0), precisionSem = (totalSemAttendance < 100 ? 2 : 0);
    let totalAttendance = `${totalSemAttendance.toFixed(precisionSem)}% / ${totalLabAttendance.toFixed(precisionLab)}%`;
    $(totalAttendanceCell).text(totalAttendance);
    if(!hasMinimumAttendance(totalSemAttendance, totalLabAttendance)){
        $(totalAttendanceCell).addClass('err');
    }else{
        $(totalAttendanceCell).removeClass('err');
    }
}

function updateAverageGrade(enrlRow) {
    debugger;
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let averageCell = $(enrlRow).children("td.average-cell").first();
    let n = 0, sum = 0;
    let lessons = enrollments[enrlId].lessons;
    for(let id in lessons){
        if(lessons[id].grade != undefined){
            sum+=lessons[id].grade;
            n++;
        }
    }
    let average = (n > 0 ? sum/n : 0.0);
    $(averageCell).text(average.toFixed(2));
    if(!hasMinimumGrade(average)){
        $(averageCell).addClass('err');
    }else{
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

function hasMinimumAttendance(seminarAttendance, laboratoryAttendance){
    return seminarAttendance >= 75 && laboratoryAttendance >= 90;
}

function hasMinimumGrade(grade){
    return grade >= 5;
}

function getValidGrade(actualValue) {
    if(typeof(actualValue) === "string"){
        actualValue = parseInt(actualValue);
    }
    if(actualValue > 10){
        return 10;
    }else if(actualValue < 1){
        return 1;
    }
    return actualValue;
}

function getValidBonus(actualValue) {
    if(typeof(actualValue) === "string"){
        actualValue = parseInt(actualValue);
    }
    if(actualValue > 10){
        return 10;
    }else if(actualValue < -10){
        return -10;
    }
    return actualValue;
}