$(document).ready(() = > {
    $("#btn_submitLoginForm"
).
click(validateLoginForm);
})
;

function validateLoginForm(event) {
    let form = $("#form_login").serializeArray();
    let ok = true;
    let err_username = $("#err_username"), err_password = $("#err_password");
    if (form[0].value == undefined || form[0].value == "") {
        $(err_username).text(msgs.usernameRequired);
        $(err_username).removeClass("invisible");
        ok = false;
    } else {
        $(err_username).text("*");
        $(err_username).addClass("invisible");
    }
    if (form[1].value == undefined || form[1].value == "") {
        $(err_password).text(msgs.passwordRequired);
        $(err_password).removeClass("invisible");
        ok = false
    } else {
        $(err_password).text("*");
        $(err_password).addClass("invisible");

    }
    if (!ok) {
        event.preventDefault();
    }
}