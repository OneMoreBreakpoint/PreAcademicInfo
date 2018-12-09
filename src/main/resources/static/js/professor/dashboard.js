let courseDivClass = "course-div";
let groupDivClass = "group-div";

$(document).ready(function() {
    console.log(courses);
    courses.forEach(
        course => createCourseDiv(course));
});


function createCourseDiv(course) {
    let courseDiv=$('<div/>', {
        id: course.courseDto.code,
        class: courseDivClass,
        html:course.courseDto.name});
        createGroupDiv(course.groups,courseDiv);
        courseDiv.appendTo($("#courses"));
}

function createGroupDiv(groups,parent){
    console.log(groups);
    groups.forEach(
        group => {
        $('<div/>', {
            id: group.id,
            class: groupDivClass,
            html:group.code
        }).appendTo(parent);
    });
}