let dataHasBeenChanged = false;
let crtGroupCode;
convertEnrollmentListToEnrollmentMap();

$(document).ready(function () {
    crtGroupCode = $("#combo_groupCode").val();
    updateAll();
    stickRightColumns();
    assignHandlers();
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

function stripReadonlyLessonsAndExams(enrollments) {
    for (let ikey in enrollments) {
        let lessons = enrollments[ikey].lessons;
        let exams = enrollments[ikey].partialExams;
        for (let jkey in lessons) {
            if (lessons[jkey].readonly == true) {
                delete lessons[jkey];
            }
        }
        for (let jkey in exams) {
            if (exams[jkey].readonly == true) {
                delete exams[jkey];
            }
        }
    }
    return enrollments;
}

function convertEnrollmentMapToEnrollmentList(enrollmentMap) {
    let enrollmentList = [];
    for (let key in enrollmentMap) {
        enrollmentList.push(deepClone(enrollmentMap[key]));
    }
    for (i = 0; i < enrollmentList.length; i++) {
        let lessons = [], partialExams = [];
        for (let key in enrollmentList[i].lessons) {
            lessons.push(enrollmentList[i].lessons[key]);
        }
        for (let key in enrollmentList[i].partialExams) {
            partialExams.push(enrollmentList[i].partialExams[key]);
        }
        enrollmentList[i].lessons = lessons;
        enrollmentList[i].partialExams = partialExams;
    }
    return enrollmentList;
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

function setDataHasBeenChangedFlag(value) {
    if (dataHasBeenChanged !== value) {
        dataHasBeenChanged = value;
        $("#btn_submitChanges").prop('disabled', !value);
    }
    if (dataHasBeenChanged) {
        window.onbeforeunload = () => {
            return true
        }; //browsers won't run code inside handler from security reasons
    } else {
        window.onbeforeunload = undefined;
    }
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
    let combo_groupCode = $("#combo_groupCode");
    $(combo_groupCode).change((event) => {
        let newUrl = updateQueryParams(window.location.toString(), {group: event.target.value});
        window.location.replace(newUrl);
        setTimeout(() => {
            $(combo_groupCode).val(crtGroupCode)
        }, 1); //if user did not leave page
    });
}

function assignActionHandlers() {
    $("#btn_submitChanges").click(() => {
        debugger;
        let strippedEnrollmentMap = stripReadonlyLessonsAndExams(deepClone(enrollments));
        let reqBody = JSON.stringify(convertEnrollmentMapToEnrollmentList(strippedEnrollmentMap));
        $.ajax({
            url: "/app/professor/enrollments",
            type: "PUT",
            data: reqBody,
            contentType: "application/json; charset=utf-8",
            success: (response, textStatus, xhr) => {
                if (xhr.status = 200) {
                    displayModal("#modal_success", true);
                    setDataHasBeenChangedFlag(false);
                }
            },
            error: () => {
                displayModal("#modal_failure", true);
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
        setDataHasBeenChangedFlag(true);
        updateTotalAttendance(enrlRow);
    });
    $(gradeField).change((event) => {
        let lessonId = getNumericIdFromDomId(lessonCell.id);
        let actualValue = getValidGrade(event.target.value);
        if (actualValue != event.target.value) {
            $(gradeField).val(actualValue);
        }
        enrollments[enrlId].lessons[lessonId].grade = actualValue;
        setDataHasBeenChangedFlag(true);
        updateAverageGrade(enrlRow);
    });
    $(bonusField).change((event) => {
        let lessonId = getNumericIdFromDomId(lessonCell.id);
        let actualValue = getValidBonus(event.target.value);
        if (actualValue != event.target.value) {
            $(bonusField).val(actualValue);
        }
        enrollments[enrlId].lessons[lessonId].bonus = actualValue;
        setDataHasBeenChangedFlag(true);
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
        enrollments[enrlId].partialExams[examId].grade = actualValue;
        setDataHasBeenChangedFlag(true);
        updateAverageGrade(enrlRow);
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
    let hasSeminarRights = teachingHasSeminarRightsOverGroup(teaching, crtGroupCode);
    let hasLabRights = teachingHasLaboratoryRightsOverGroup(teaching, crtGroupCode);
    debugger;
    let hasCoordinatorRights = teachingHasCoordinatorRights(teaching);
    let totalAttendanceViewText;
    if (hasCoordinatorRights) {
        totalAttendanceViewText = `${totalSemAttendance.toFixed(precisionSem)}% / ${totalLabAttendance.toFixed(precisionLab)}%`;
    } else if (hasSeminarRights && !hasLabRights) {
        totalAttendanceViewText = `${totalSemAttendance.toFixed(precisionSem)}%`;
    } else if (!hasSeminarRights && hasLabRights) {
        totalAttendanceViewText = `${totalLabAttendance.toFixed(precisionLab)}%`;
    }
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
    let hasSeminarRights = teachingHasSeminarRightsOverGroup(teaching, crtGroupCode);
    let hasLabRights = teachingHasLaboratoryRightsOverGroup(teaching, crtGroupCode);
    let hasCoordinatorRights = teachingHasCoordinatorRights(teaching);
    if (hasCoordinatorRights) {
        return seminarAttendance >= MIN_SEM_ATT && laboratoryAttendance >= MIN_LAB_ATT;
    } else if (hasSeminarRights && !hasLabRights) {
        return seminarAttendance >= MIN_SEM_ATT;
    } else if (!hasSeminarRights && hasLabRights) {
        return laboratoryAttendance >= MIN_LAB_ATT;
    }
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

function teachingHasSeminarRightsOverGroup(teaching, groupCode) {
    for (let i = 0; i < teaching.seminarGroups.length; i++) {
        if (teaching.seminarGroups[i].code == groupCode) {
            return true;
        }
    }
    return false;
}

function teachingHasLaboratoryRightsOverGroup(teaching, groupCode) {
    for (let i = 0; i < teaching.laboratoryGroups.length; i++) {
        if (teaching.laboratoryGroups[i].code == groupCode) {
            return true;
        }
    }
    return false;
}

function teachingHasCoordinatorRights(teaching) {
    return teaching.course.coordinator.username == teaching.professor.username;
}