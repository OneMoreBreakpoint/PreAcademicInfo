$(document).ready(function () {
    $(".modal-close").click(() => {
        displayModal('.modal-box', false);
    });
});

function displayModal(cssSelector, display) {
    let displayMode = (display ? 'flex' : 'none');
    $(cssSelector).css("display", displayMode);
}