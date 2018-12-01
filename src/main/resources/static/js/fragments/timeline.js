$(document).ready(function () {
    stickRightColumns();
});

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
        "right":`${$(column_average).outerWidth()}px`
    });
    $(column_bonus).css({
        "position": "sticky",
        "right": `${$(column_average).outerWidth() + $(column_attendance).outerWidth()}px`
    });
}