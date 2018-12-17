$(document).ready(function () {
    $(".professor-course").each((index, profCourse) => {
        let profCourseId = getNumericIdFromDomId(profCourse.id);
        $(profCourse).find(".professor-course-settings-btn").click(() => {
            displayModal(`#professor_course_settings_modal-${profCourseId}`, true);
        });
    });
});