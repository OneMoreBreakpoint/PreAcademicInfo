convertProfessorCoursesListToProfessorCoursesMap();
lastLessonTemplateId = 0;
professorCoursesBackup = deepClone(professorCourses);

$(document).ready(function () {
    assignProfessorCourseHandlers();
    validateWeights();
});

function convertProfessorCoursesListToProfessorCoursesMap() {
    let professorCoursesList = professorCourses, professorCoursesMap = {};
    for (let i = 0; i < professorCoursesList.length; i++) {
        let lessonTemplatesMap = {}, lessonTemplatesList = professorCoursesList[i].course.lessonTemplates;
        for (let j = 0; j < lessonTemplatesList.length; j++) {
            lessonTemplatesMap[lessonTemplatesList[j].id] = lessonTemplatesList[j];
        }
        professorCoursesList[i].course.lessonTemplates = lessonTemplatesMap;
        professorCoursesMap[professorCoursesList[i].course.id] = professorCoursesList[i];
    }
    professorCourses = professorCoursesMap;
}

function assignProfessorCourseHandlers() {
    $(".professor-course").each((index, profCourse) => {
        let profCourseId = getNumericIdFromDomId(profCourse.id);
        $(profCourse).find(".professor-course-settings-btn").off("click");
        $(profCourse).find(".professor-course-settings-btn").click(() => {
            displayModal(`#professor_course_settings_modal-${profCourseId}`, true);
        });
        $(profCourse).find(".professor-course-settings-modal").each((index, settingsModal) => {
            assignProfessorCourseSettingsModalHandlers(profCourse, settingsModal);
        });
    });
}

function assignProfessorCourseSettingsModalHandlers(profCourse, settingsModal) {
    assignNrOfPartialExamsHandlers(profCourse, settingsModal);
    assignLessonWeightHandlers(profCourse, settingsModal);
    assignModalBtnsHandlers(profCourse, settingsModal);
}

function assignNrOfPartialExamsHandlers(profCourse, settingsModal) {
    let profCourseId = getNumericIdFromDomId(profCourse.id);
    let spinner_nrOfSeminarPartialExams = $(settingsModal).find("#spinner_nrOfSeminarPartialExams");
    let spinner_nrOfLaboratoryPartialExams = $(settingsModal).find("#spinner_nrOfLaboratoryPartialExams");
    let spinner_nrOfCoursePartialExams = $(settingsModal).find("#spinner_nrOfCoursePartialExams");

    $(spinner_nrOfSeminarPartialExams).off("change");
    $(spinner_nrOfSeminarPartialExams).change((event) => {
        handleNrOfLessonsChanged(event, profCourseId, settingsModal, "PARTIAL_EXAM_SEMINAR");
    });

    $(spinner_nrOfLaboratoryPartialExams).off("change");
    $(spinner_nrOfLaboratoryPartialExams).change((event) => {
        handleNrOfLessonsChanged(event, profCourseId, settingsModal, "PARTIAL_EXAM_LABORATORY");
    });

    $(spinner_nrOfCoursePartialExams).off("change");
    $(spinner_nrOfCoursePartialExams).change((event) => {
        handleNrOfLessonsChanged(event, profCourseId, settingsModal, "PARTIAL_EXAM_COURSE");
    });
}

function assignLessonWeightHandlers(profCourse, settingsModal) {
    let profCourseId = getNumericIdFromDomId(profCourse.id);
    let inputs_lessonWeight = $(settingsModal).find("input.lesson-weight");

    $(inputs_lessonWeight).off("change");
    $(inputs_lessonWeight).change((event) => {
        let lessonWeightId = getNumericIdFromDomId(event.target.id);
        let newValue = $(event.target).val();
        let validValue = getValidWeight(newValue);
        if (validValue !== newValue || newValue === "") {
            $(event.target).val(validValue);
        }
        professorCourses[profCourseId].course.lessonTemplates[lessonWeightId].weight = validValue;
        validateWeights();
    });
}

function assignModalBtnsHandlers(profCourse, settingsModal) {
    let profCourseId = getNumericIdFromDomId(profCourse.id);
    let btn_professorCourseDiscard = $(settingsModal).find(".professor-course-discard-btn");
    let btn_professorCourseSave = $(settingsModal).find(".professor-course-save-btn");

    $(btn_professorCourseDiscard).off("click.professorCourse");
    $(btn_professorCourseDiscard).on("click.professorCourse", (event) => {
        professorCourses[profCourseId].course = deepClone(professorCoursesBackup[profCourseId].course);
        renderLessonTemplates();
        validateWeights();
    });

    $(btn_professorCourseSave).off("click.professorCourse");
    $(btn_professorCourseSave).on("click.professorCourse", (event) => {
        if (!confirm(msgs.confirmSaveChanges)) {
            return;
        }
        let reqBody = JSON.stringify(getCourse(profCourseId));
        $.ajax({
            url: "/app/professor/course",
            type: "PUT",
            data: reqBody,
            contentType: "application/json; charset=utf-8",
            success: (response, textStatus, xhr) => {
                if (xhr.status = 200) {
                    displayModal("#modal_success", true);
                    refreshCourseState(profCourseId);
                }
            },
            error: () => {
                displayModal("#modal_failure", true);
            }
        });
    });
}

function refreshCourseState(courseId) {
    let settingsBtns = $(`#professor_course-${courseId} .professor-course-settings-btn`);
    $(settingsBtns).off("click");

    let courseCode = professorCourses[courseId].course.code;
    $.ajax({
        url: `/app/professor/course/${courseCode}`,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: (response, textStatus, xhr) => {
            if (xhr.status = 200) {
                professorCoursesBackup[courseId].course = response;
                professorCourses[courseId].course = response;
                assignProfessorCourseHandlers();
            }
        }
    });
}

function handleNrOfLessonsChanged(event, profCourseId, settingsModal, lessonTemplateType) {
    let newValue = $(event.target).val();
    let validNrOfLessons = getValidNrOfLessons(newValue);
    if (validNrOfLessons !== newValue) {
        $(event.target).val(validNrOfLessons);
    }
    let lessonTemplateCssSelector = toCssClassSelector(lessonTemplateType);
    let partialExamSeminarList = $(settingsModal).find(lessonTemplateCssSelector);
    while (validNrOfLessons < partialExamSeminarList.length) {
        removeLesson(profCourseId, $(partialExamSeminarList).last()[0]);
        validateWeights();
        partialExamSeminarList = $(settingsModal).find(lessonTemplateCssSelector);
    }
    while (validNrOfLessons > partialExamSeminarList.length) {
        addLesson(profCourseId, settingsModal, partialExamSeminarList, lessonTemplateType);
        validateWeights();
        partialExamSeminarList = $(settingsModal).find(lessonTemplateCssSelector);
    }
}

function validateWeights() {
    for (let key in professorCourses) {
        let professorCourseDiv = $(`#professor_course-${key}`);
        let btn_professorCourseSave = $(professorCourseDiv).find(".professor-course-save-btn");

        let totalWeightIsValid = validateTotalLessonWeights(professorCourses[key]);
        let partialExamWeightsAreValid = validatePartialExamsWeights(professorCourses[key]);
        let seminarPartialCanBeAdded = validateSeminarPartialAdd(professorCourses[key]);
        let isValid = totalWeightIsValid && partialExamWeightsAreValid && seminarPartialCanBeAdded;
        $(btn_professorCourseSave).prop('disabled', !isValid);
    }
}

function validateSeminarPartialAdd(professorCourse) {
    let span_cannotAddSeminarPartial = $(`#professor_course-${professorCourse.course.id} .cannot-add-seminar-partial-err`);
    let lessonTemplates = professorCourse.course.lessonTemplates;
    for (let key in lessonTemplates) {
        if (lessonTemplates[key].type === "PARTIAL_EXAM_SEMINAR") {
            for (let jkey in lessonTemplates) {
                if (lessonTemplates[jkey].type === "SEMINAR") {
                    $(span_cannotAddSeminarPartial).css({'display': 'none'});
                    return true;
                }
            }
            $(span_cannotAddSeminarPartial).css({'display': 'block'});
            return false;
        }
    }
    $(span_cannotAddSeminarPartial).css({'display': 'none'});
    return true;
}

function validateTotalLessonWeights(professorCourse) {
    let professorCourseDiv = $(`#professor_course-${professorCourse.course.id}`);
    let lessonTemplates = professorCourse.course.lessonTemplates;
    let weight = 0;
    for (let jkey in lessonTemplates) {
        if (lessonTemplates[jkey].weight != null) {
            weight += lessonTemplates[jkey].weight;
        }
    }
    let span_sumOfWeightsLt100Err = $(professorCourseDiv).find(".sum-of-weights-lt-100-err");
    let span_sumOfWeightsGt100Err = $(professorCourseDiv).find(".sum-of-weights-gt-100-err");
    if (weight < 100) {
        $(span_sumOfWeightsLt100Err).css({display: 'block'});
        $(span_sumOfWeightsGt100Err).css({display: 'none'});
        return false;
    } else if (weight > 100) {
        $(span_sumOfWeightsLt100Err).css({display: 'none'});
        $(span_sumOfWeightsGt100Err).css({display: 'block'});
        return false;
    } else {
        $(span_sumOfWeightsLt100Err).css({display: 'none'});
        $(span_sumOfWeightsGt100Err).css({display: 'none'});
        return true;
    }
}

function validatePartialExamsWeights(professorCourse) {
    let professorCourseDiv = $(`#professor_course-${professorCourse.course.id}`);
    let span_partialsMustHaveNon0WeightErr = $(professorCourseDiv).find(".partials-must-have-non0-weight-err");
    let lessonTemplates = professorCourse.course.lessonTemplates;
    for (let jkey in lessonTemplates) {
        if (["PARTIAL_EXAM_SEMINAR", "PARTIAL_EXAM_LABORATORY", "PARTIAL_EXAM_COURSE"]
                .indexOf(lessonTemplates[jkey].type) !== -1
            && lessonTemplates[jkey].weight === 0) {
            $(span_partialsMustHaveNon0WeightErr).css({display: 'block'});
            return false;
        }
    }
    $(span_partialsMustHaveNon0WeightErr).css({display: 'none'});
    return true;

}

function renderLessonTemplates() {
    $(".professor-course").each((index, profCourse) => {
        let profCourseId = getNumericIdFromDomId(profCourse.id);
        if (!professorCourses[profCourseId].coordinator) {
            return;
        }
        let lessonTemplates = professorCourses[profCourseId].course.lessonTemplates;

        let settingsModal = $(profCourse).find(".professor-course-settings-modal");
        $(settingsModal).find("div.lesson-weight").remove();


        $(settingsModal).find("#spinner_nrOfSeminarPartialExams")
            .val(getNrOfLessonTemplatesOfType(lessonTemplates, "PARTIAL_EXAM_SEMINAR"));
        $(settingsModal).find("#spinner_nrOfLaboratoryPartialExams")
            .val(getNrOfLessonTemplatesOfType(lessonTemplates, "PARTIAL_EXAM_LABORATORY"));
        $(settingsModal).find("#spinner_nrOfCoursePartialExams")
            .val(getNrOfLessonTemplatesOfType(lessonTemplates, "PARTIAL_EXAM_COURSE"));

        for (let key in lessonTemplates) {
            let lessonTemplate = lessonTemplates[key];
            if (lessonTemplate.type !== "SEMINAR") {
                addLessonToView(settingsModal, lessonTemplate);
            }
        }
    });
}

function getValidWeight(actualWeight) {
    let validNr = getValidNumber(actualWeight, 0, 100);
    return validNr !== undefined ? validNr : 0;
}

function getValidNrOfLessons(actualNrOfLessons) {
    let validNr = getValidNumber(actualNrOfLessons, 0, 5);
    return validNr !== undefined ? validNr : 0;
}

function removeLesson(profCourseId, lessonToBeRemoved) {
    let lessonToBeRemovedId = getNumericIdFromDomId(lessonToBeRemoved.id);
    delete professorCourses[profCourseId].course.lessonTemplates[lessonToBeRemovedId];
    $(lessonToBeRemoved).remove();
}

function addLesson(profCourseId, settingsModal, crtLessonTemplateList, lessonTemplateType) {
    let newLessonTemplate = addLessonToJs(profCourseId, settingsModal, crtLessonTemplateList, lessonTemplateType);
    addLessonToView(settingsModal, newLessonTemplate);
}

function addLessonToJs(profCourseId, settingsModal, crtLessonTemplateList, lessonTemplateType) {
    let nrOfNewLessonTemplate;
    nrOfNewLessonTemplate = findMaxNrOfLessonTemplateOfType(profCourseId, lessonTemplateType) + 1;
    let newLessonTemplate = {
        id: --lastLessonTemplateId,
        nr: nrOfNewLessonTemplate,
        weight: lessonTemplateType === "SEMINAR" ? null : 0,
        type: lessonTemplateType,
        rightType: null
    };
    professorCourses[profCourseId].course.lessonTemplates[newLessonTemplate.id] = newLessonTemplate;
    return newLessonTemplate;
}

function addLessonToView(settingsModal, newLessonTemplate) {
    let newLessonTemplateLabel = $('<label>');
    switch (newLessonTemplate.type) {
        case "SEMINAR":
            newLessonTemplateLabel.text(`${msgs.seminar} ${newLessonTemplate.nr}`);
            break;
        case "LABORATORY":
            newLessonTemplateLabel.text(`${msgs.laboratory} ${newLessonTemplate.nr}`);
            break;
        case "PARTIAL_EXAM_SEMINAR":
            newLessonTemplateLabel.text(`${msgs.partial} ${msgs.seminar} ${newLessonTemplate.nr}`);
            break;
        case "PARTIAL_EXAM_LABORATORY":
            newLessonTemplateLabel.text(`${msgs.partial} ${msgs.laboratory} ${newLessonTemplate.nr}`);
            break;
        case "PARTIAL_EXAM_COURSE":
            newLessonTemplateLabel.text(`${msgs.partial} ${msgs.course} ${newLessonTemplate.nr}`);
            break;
    }
    let newLessonTemplateInput = $('<input type="number" min="0" max="100" step="1" class="lesson-weight">')
        .prop('id', `lessonWeight-${newLessonTemplate.id}`)
        .val(newLessonTemplate.weight);
    let percentSpan = $('<span>%</span>');
    let newLessonTemplateDiv = $('<div>')
        .addClass(`lesson-weight ${toCssClassSelector(newLessonTemplate.type, false)}`)
        .prop('id', `lesson_template-${newLessonTemplate.id}`)
        .append(newLessonTemplateLabel, newLessonTemplateInput, percentSpan);
    let lastLessonWeight = $(settingsModal).find("div.lesson-weight").last();
    if (lastLessonWeight.length > 0) {
        $(lastLessonWeight).after(newLessonTemplateDiv);
    } else {
        $(settingsModal).find(".modal-body > *").last().after(newLessonTemplateDiv);
    }
    assignProfessorCourseSettingsModalHandlers($(settingsModal).parents('.professor-course')[0], settingsModal);
}

function toCssClassSelector(lessonTemplateType, includeDot = true) {
    let cssClass = lessonTemplateType.toLowerCase().replace(/_/g, '-');
    return includeDot ? `.${cssClass}` : cssClass;
}

function findMaxNrOfLessonTemplateOfType(profCourseId, lessonTemplateType) {
    let lessonTemplates = professorCourses[profCourseId].course.lessonTemplates;
    let max = 0;
    for (let key in lessonTemplates) {
        if (lessonTemplates[key].type === lessonTemplateType && lessonTemplates[key].nr > max) {
            max = lessonTemplates[key].nr;
        }
    }
    return max;
}

function getNrOfLessonTemplatesOfType(lessonTemplates, lessonTemplateType) {
    let nr = 0;
    for (let key in lessonTemplates) {
        if (lessonTemplates[key].type === lessonTemplateType) {
            nr++;
        }
    }
    return nr;
}


function getCourse(profCourseId) {
    let course = deepClone(professorCourses[profCourseId].course);
    let lessonTemplateList = [], lessonTemplateMap = professorCourses[profCourseId].course.lessonTemplates;
    for (let key in lessonTemplateMap) {
        let lessonTemplate = lessonTemplateMap[key];
        if (lessonTemplate.id < 0) {
            lessonTemplate.id = undefined;
        }
        lessonTemplateList.push(deepClone(lessonTemplate));
    }
    course.lessonTemplates = lessonTemplateList;
    return course;
}