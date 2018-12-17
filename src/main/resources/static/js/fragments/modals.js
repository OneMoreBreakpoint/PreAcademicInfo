$(document).ready(function () {
    $(".modal-close").click(() => {
        displayModal('.modal-box', false);
    });
    $(".modal-window").click((event) => {
        event.stopPropagation();
    });
});

function displayModal(cssSelector, display) {
    let displayMode = (display ? 'flex' : 'none');
    $(cssSelector).css("display", displayMode);
}