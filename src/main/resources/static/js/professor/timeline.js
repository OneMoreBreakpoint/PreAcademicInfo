let dataHasBeenChanged = false;
let crtGroupCode;

$(document).ready(function () {
    stickRightColumns();
    console.log(enrollments);
    crtGroupCode = $("#combo_groupCode").val();
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

function convertEnrollmentMapToEnrollmentList() {
    let enrollmentList = [], enrollmentMap = enrollments;
    for(let key in enrollmentMap){
        enrollmentList.push(enrollmentMap[key]);
    }
    for(i=0; i < enrollmentList.length; i++){
        let lessons = [], partialExams = [];
        for(let key in enrollmentList[i].lessons){
            lessons.push(enrollmentList[i].lessons[key]);
        }
        for(let key in enrollmentList[i].partialExams){
            partialExams.push(enrollmentList[i].partialExams[key]);
        }
        enrollmentList[i].lessons = lessons;
        enrollmentList[i].partialExams = partialExams;
    }
    return enrollmentList;
}

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
    assignTimelineHandlers();
    assignFilterHandlers();
    assignActionHandlers();
}

function assignTimelineHandlers() {
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
    });
}

function assignFilterHandlers() {
    $("#div_filters input[type=checkbox]").change(() => {
        let enrlRows = $(".timeline tr.enrollment-row");
        $(enrlRows).show();
        if ($("#check_excludePassingGrade").is(":checked")) {
            $(enrlRows).filter((index, enrlRow) => {
                let enrlId = getNumericIdFromDomId(enrlRow.id);
                return hasMinimumGrade(enrollments[enrlId].averageGrade);
            }).hide();
        }
        if ($("#check_excludePassingAttendances").is(":checked")) {
            $(enrlRows).filter((index, enrlRow) => {
                let enrlId = getNumericIdFromDomId(enrlRow.id);
                return hasMinimumAttendance(enrollments[enrlId].seminarAttendance, enrollments[enrlId].laboratoryAttendance);
            }).hide();
        }
    });
    $("#combo_groupCode").change((event)=>{
        if(!dataHasBeenChanged || confirm(msgs.leavePage)){
            let newUrl = updateQueryParams(window.location.toString(),{group: event.target.value});
            window.location.replace(newUrl);
        }else{
            event.target.value = crtGroupCode;
        }
    });
}

function assignActionHandlers() {
    $("#btn_submitChanges").click(()=>{
        let reqBody = JSON.stringify(convertEnrollmentMapToEnrollmentList());
        console.log(reqBody);
        $.ajax({
            url: "/app/professor/enrollments",
            type: "PUT",
            data: reqBody,
            contentType: "application/json; charset=utf-8",
            success: (response, textStatus, xhr)=>{
                if(xhr.status = 200){

                }
            },
            error: ()=>{
                console.log("error");
            }
        });
    });
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
        dataHasBeenChanged = true;
        updateTotalAttendance(enrlRow);
    });
    $(gradeField).change((event) => {
        let lessonId = getNumericIdFromDomId(lessonCell.id);
        let actualValue = getValidGrade(event.target.value);
        if (actualValue != event.target.value) {
            $(gradeField).val(actualValue);
        }
        enrollments[enrlId].lessons[lessonId].grade = actualValue;
        dataHasBeenChanged = true;
        updateAverageGrade(enrlRow);
    });
    $(bonusField).change((event) => {
        let lessonId = getNumericIdFromDomId(lessonCell.id);
        let actualValue = getValidBonus(event.target.value);
        if (actualValue != event.target.value) {
            $(bonusField).val(actualValue);
        }
        enrollments[enrlId].lessons[lessonId].bonus = actualValue;
        dataHasBeenChanged = true;
        updateTotalBonus(enrlRow);
    });
}

function assignExamCellHandlers(enrlRow, examCell) {
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let gradeField = $(examCell).find("input.grade-field");
    $(gradeField).change((event) => {
        let examId = getNumericIdFromDomId(examCell.id);
        let actualValue = getValidGrade(event.target.value);
        if (actualValue != event.target.value) {
            $(gradeField).val(actualValue);
        }
        enrollments[enrlId].lessons[examId].grade = actualValue;
        dataHasBeenChanged = true;
        updateAverageGrade(enrlRow);
    });
}

function updateTotalBonus(enrlRow) {
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let totalBonusCell = $(enrlRow).children("td.bonus-cell").first();
    let lessons = enrollments[enrlId].lessons;
    let totalBonusValue = 0;
    for (let id in lessons) {
        if (lessons[id].bonus != undefined) {
            totalBonusValue += lessons[id].bonus;
        }
    }
    enrollments[enrlId].totalBonus = totalBonusValue;
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
    if (!hasMinimumAttendance(totalSemAttendance, totalLabAttendance)) {
        $(totalAttendanceCell).addClass('err');
    } else {
        $(totalAttendanceCell).removeClass('err');
    }
    enrollments[enrlId].seminarAttendance = totalSemAttendance;
    enrollments[enrlId].laboratoryAttendance = totalLabAttendance;
}

function updateAverageGrade(enrlRow) {
    debugger;
    let enrlId = getNumericIdFromDomId(enrlRow.id);
    let averageCell = $(enrlRow).children("td.average-cell").first();
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
    return seminarAttendance >= 75 && laboratoryAttendance >= 90;
}

function hasMinimumGrade(grade) {
    return grade >= 5;
}

function getValidGrade(actualValue) {
    return getValidNumber(actualValue, 1, 10);
}

function getValidBonus(actualValue) {
    return getValidNumber(actualValue, -10, 10);
}

function getValidNumber(actualValue, minValue, maxValue) {
    if (typeof(actualValue) === "string") {
        actualValue = parseInt(actualValue);
    }
    if (actualValue > maxValue) {
        return maxValue;
    } else if (actualValue < minValue) {
        return minValue;
    }
    return actualValue;
}