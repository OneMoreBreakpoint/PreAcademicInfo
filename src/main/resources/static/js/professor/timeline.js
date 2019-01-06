let dataHasBeenChanged = false;
let crtGroupCode;

$(document).ready(function () {
    crtGroupCode = $("#combo_groupCode").val();
    assignTimelineHandlers();
    assignFilterHandlers();
    assignActionHandlers();
});


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