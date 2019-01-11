let crtGroupCode;
let enrollmentsSnapshot = deepClone(enrollments);

$(document).ready(function () {
    crtGroupCode = $("#combo_groupCode").val();
    assignTimelineHandlers();
    assignFilterHandlers();
    assignActionHandlers();
});


function setDataHasBeenChangedFlag(value) {
    if(!value){
        enrollmentsSnapshot = deepClone(enrollments);
    }
    let dataHasBeenChanged = enrollmentsStateHasChanged();
    $("#btn_submitChanges").prop('disabled', !dataHasBeenChanged);
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
        updateTotalAttendance(enrlRow);
        setDataHasBeenChangedFlag(true);
    });
    $(gradeField).change((event) => {
        let lessonId = getNumericIdFromDomId(lessonCell.id);
        let actualValue = getValidGrade(event.target.value);
        if (actualValue != event.target.value) {
            $(gradeField).val(actualValue);
        }
        enrollments[enrlId].lessons[lessonId].grade = actualValue;
        updateAverageGrade(enrlRow);
        setDataHasBeenChangedFlag(true);
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
        updateTotalBonus(enrlRow);
        setDataHasBeenChangedFlag(true);
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
        let reqBody = JSON.stringify(stripReadOnlyLessons(getModifiedLessonList()));
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

function hasMinimumAttendance(seminarAttendance, laboratoryAttendance) {
    if (seminarAttendance === undefined) {
        seminarAttendance = 100;
    }
    if (laboratoryAttendance === undefined) {
        laboratoryAttendance = 100;
    }
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

function getModifiedLessonList() {
    let lessons = [];
    for (let enrlId in enrollments) {
        for (let lessonId in enrollments[enrlId].lessons) {
            if(!equals(enrollments[enrlId].lessons[lessonId], enrollmentsSnapshot[enrlId].lessons[lessonId])){
                lessons.push(enrollments[enrlId].lessons[lessonId]);
            }
        }
    }
    return lessons;
}

function stripReadOnlyLessons(lessons) {
    let result = [];
    for (let i = 0; i < lessons.length; i++) {
        if (lessons[i].template.rightType === "WRITE") {
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

function enrollmentsStateHasChanged(){
    for(let enrlId in enrollments){
        for(let lessonId in enrollments[enrlId].lessons){
            if(!equals(enrollments[enrlId].lessons[lessonId], enrollmentsSnapshot[enrlId].lessons[lessonId])){
                return true;
            }
        }
    }
    return false;
}

function equals(firstLesson, secondLesson){
    return firstLesson.attended == secondLesson.attended &&
        firstLesson.bonus == secondLesson.bonus &&
        firstLesson.grade == secondLesson.grade;
}