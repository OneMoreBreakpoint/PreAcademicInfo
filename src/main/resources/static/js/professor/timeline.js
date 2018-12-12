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
        $(photoCell).click(() => {
            //TODO: open modal
        });
        $(nameCell).click(() => {
            //TODO: open modal
        });
        $(lessonCells).each((index, lessonCell) => {
            assignLessonCellHandlers(enrlRow, lessonCell);
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
        let reqBody = JSON.stringify(stripReadOnlyLessons(getLessonList()));
        console.log(reqBody);
        $.ajax({
            url: "/app/professor/lessons",
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
        if (checkedState === false) {
            $(bonusField).val(undefined);
            $(bonusField).trigger('change');
        }
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
        if (actualValue != undefined && actualValue !== 0) {
            $(attendanceField).prop('checked', true);
            $(attendanceField).trigger('change');
        }
        setDataHasBeenChangedFlag(true);
        updateTotalBonus(enrlRow);
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
    let hasSeminarRights = profHasRight("SEMINAR", "READ");
    let hasLabRights = profHasRight("LABORATORY", "READ");
    let hasCoordinatorRights = profHasRight("PARTIAL_EXAM_COURSE", "WRITE");
    let totalAttendanceViewText;
    if ((hasSeminarRights && hasLabRights) || hasCoordinatorRights) {
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
    let hasSeminarRights = profHasRight("SEMINAR", "READ");
    let hasLabRights = profHasRight("LABORATORY", "READ");
    let hasCoordinatorRights = profHasRight("PARTIAL_EXAM_COURSE", "WRITE");
    if ((hasSeminarRights && hasLabRights) || hasCoordinatorRights) {
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
    return getValidNumber(actualValue, 0, 10);
}

function getValidBonus(actualValue) {
    return getValidNumber(actualValue, -10, 10);
}

function getValidNumber(actualValue, minValue, maxValue) {
    if (typeof(actualValue) === "string") {
        actualValue = parseInt(actualValue);
    }
    if (isNaN(actualValue)) {
        return undefined;
    }
    if (actualValue > maxValue) {
        return maxValue;
    } else if (actualValue < minValue) {
        return minValue;
    }
    return actualValue;
}

function profHasRight(lessonType, rightType) {
    for (let i = 0; i < rights.length; i++) {
        if (rights[i].lessonType === lessonType && rights[i].rightType === rightType) {
            return true;
        }
    }
    return false;
}

function getLessonList() {
    let lessons = [];
    for (let ikey in enrollments) {
        for (let jkey in enrollments[ikey].lessons) {
            lessons.push(enrollments[ikey].lessons[jkey]);
        }
    }
    return lessons;
}

function stripReadOnlyLessons(lessons) {
    let result = [];
    for (let i = 0; i < lessons.length; i++) {
        if (lessons[i].rightType === "WRITE") {
            result.push(lessons[i]);
        }
    }
    return result;
}