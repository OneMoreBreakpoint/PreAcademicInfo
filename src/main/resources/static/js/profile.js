const TYPE_STUDENT = "STUDENT";
const TYPR_PROFESOR = "PROFESSOR";
const UPDATE_STUDENT_URL = "/app/student/";
const UPDATE_PROFESSOR_URL = "/app/professor/";

$(document).ready(function () {
    create_user_details();
});


function create_user_details() {
    create_profile_photo();
    create_personal_details(user);
    create_password_settings();
    create_user_settings(user);
}

function delete_photo() {
    user.profilePhoto = null;
    document.getElementById('profile-img-input').src = "../images/profile.jpg";
    save();
}

function create_profile_photo() {
    var preview = document.getElementById('profile-img-input');
    if (user.profilePhoto != undefined) {
        preview.src = user.profilePhoto;
    }
}

function create_personal_details(user_data) {
    (user_data.role === TYPE_STUDENT) ? create_student_details(user_data) : create_profesor_details(user_data);
    $("#personal-details").after(create_row(`${i18n.email}:`, user_data.email));
    $("#personal-details").after(create_row(`${i18n.username}:`, user_data.username));
    $("#personal-details").after(create_row(`${i18n.lastName}:`, user_data.lastName));
    $("#personal-details").after(create_row(`${i18n.firstName}:`, user_data.firstName));
}

function create_student_details(user_details) {
    $("#personal-details").after(create_row(`${i18n.group}`, user_details["group"]["code"]));
    $("#personal-details").after(create_row(`${i18n.registrationNr}`, user_details.registrationNr));
}

function create_profesor_details(user_details) {
    $("#personal-details").after(
        `<div class='row'>` +
        `<div class='col-md-4'><label id='lbl_webPage'>${i18n.webpage}: </label></div>` +
        `<div class='col-md-8'>` +
        `<input max='50' min='1'  type='url' id='web-page' name='web-page' class='form-control' onchange='update_web_page()' data-bv-uri-message='The website address is not valid'>` +
        `<small id = "error-msg" class="help-block" data-bv-validator="uri" data-bv-for="web-page" data-bv-result="INVALID" >${i18n.invalidWebPage}</small>` +
        `</div>` +
        `</div>`
    );
    $("#web-page").val(user_details.webPage);
}

function update_web_page() {
    let page = $("#web-page").val();
    if (valid_URL(page)) {
        user.webPage = page;
        valid_field("#web-page");
        save();
    }
    else {
        invalid_field("#web-page");
    }
}

function invalid_field(input) {
    $(input).css('border-color', '#a94442');
    $("#error-msg").css('display', 'block');
}

function valid_field(input) {
    $(input).css('border-color', "#3c763d");
    $("#error-msg").css('display', 'none');
}

function valid_URL(str) {
    try {
        var pattern = new RegExp('^(https?:\\/\\/)?' +
            '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' +
            '((\\d{1,3}\\.){3}\\d{1,3}))' +
            '(\\:\\d+)?' +
            '(\\/[-a-z\\d%@_.~+&:]*)*' +
            '(\\?[;&a-z\\d%@_.,~+&:=-]*)?' +
            '(\\#[-a-z\\d_]*)?$', 'i');
        return pattern.test(str);
    } catch (e) {
        return false;
    }
}

function create_row(name, value) {
    var row_div = document.createElement('div');
    var col_name = document.createElement('div');
    $(col_name)
        .addClass('col-md-4')
        .append("<label style='color: #8c8c8c'>" + name + "</label>");
    var col_value = document.createElement('div');
    $(col_value)
        .addClass('col-md-8')
        .append("<p>" + value + "</p>");
    $(row_div)
        .addClass('row')
        .append(col_name, col_value);
    return row_div;
}

function create_password_settings() {
    $("#password-settings").after(
        `<label>${i18n.currentPassword}: </label>` +
        "<input disabled type='password' id='current-password' class='form-control'>" +
        `<label>${i18n.newPassword}: </label>` +
        "<input disabled type='password' id='new-password' class='form-control'>" +
        `<label>${i18n.repeatPassword}: </label>` +
        "<input disabled type='password' id='repeat-password' class='form-control' name='passwd'>" +
        '<small id = "error-passwd-msg" class="help-block" data-bv-validator="uri" data-bv-for="passwd" ' +
        `data-bv-result="INVALID">${i18n.invalidPassword}</small>`
    );
}

function create_user_settings(user_details) {
    if (user_details.role === TYPR_PROFESOR) {
        $("#profile-settings").css("display", "none");
        return;
    }

    $("#profile-settings").after(
        "<li>" +
        "<div class='material-switch'>" +
        "<input id='notify-by-email' name='notify-by-email' type='checkbox' onchange='update_user_settings()'>" +
        "<label for='notify-by-email' class='label-default'></label>" +
        `&nbsp;&nbsp;${i18n.notifyThroughEmail}.` +
        "</div>" +
        "</li>");
    if (user_details.notifiedByEmail) {
        $("#notify-by-email").prop("checked", "true");
    }
}

function update_user_settings() {
    let notify = $("#notify-by-email").is(":checked");
    user.notifiedByEmail = notify;
    save();
}

function save_passwd() {
    let new_passwd = $("#new-password").val();
    let repeat_passwd = $("#repeat-password").val();
    let crt_password = $("#current-password").val();
    if (!validatePasswords(crt_password, new_passwd, repeat_passwd)) {
        $("#error-passwd-msg").css('display', 'block');
        return;
    }
    $("#error-passwd-msg").css('display', 'none');
    $.ajax({
        url: `/app/password?crtPassword=${crt_password}&newPassword=${new_passwd}`,
        type: "PUT",
        contentType: "application/json; charset=utf-8",
        success: (response, textStatus, xhr) => {
            if (xhr.status == 200) {
                displayModal("#modal_success", true);
                disable_edit();
            }
        },
        error: (xhr) => {
            if (xhr.status == 403) {
                displayModal("#modal_invalid_pwd", true);
            }
            displayModal("#modal_failure", true);
        }
    });
}

function change_password() {
    $("#change_passwd-btn").css('display', "none");
    $("#save-btn").css('display', "");
    $("#cancel-btn").css('display', "");
    $("#current-password").prop("disabled", false);
    $("#new-password").prop("disabled", false);
    $("#repeat-password").prop("disabled", false);
}

function save() {
    url = (user.role == TYPE_STUDENT) ? UPDATE_STUDENT_URL : UPDATE_PROFESSOR_URL;
    let reqBody = JSON.stringify(user);
    $.ajax({
        url: url,
        type: "PUT",
        data: reqBody,
        contentType: "application/json; charset=utf-8",
        success: (response, textStatus, xhr) => {
            if (xhr.status = 200) {
                displayModal("#modal_success", true);
                disable_edit();
            }
        },
        error: () => {
            displayModal("#modal_failure", true);
        }
    });
}

function disable_edit() {
    $("#change_passwd-btn").css('display', "");
    $("#save-btn").css('display', "none");
    $("#cancel-btn").css('display', "none");
    $("#current-password")
        .val("")
        .prop("disabled", true);
    $("#new-password")
        .val("")
        .prop("disabled", true);
    $("#repeat-password")
        .val("")
        .prop("disabled", true);
}

function cancel() {
    disable_edit();
}

function previewFile() {
    var preview = document.getElementById('profile-img-input'); //selects the query named img
    var file = document.querySelector('input[type=file]').files[0]; //sames as here
    var reader = new FileReader();

    reader.onloadend = function (event) {
        let image = event.target.result;
        preview.src = image;
        user.profilePhoto = image;
        save();
    };

    if (file) {
        if (file.size > 1048576) {
            alert("The dimension file has to be less than 1 MB.");
            return;
        }
        reader.readAsDataURL(file); //reads the data as a URL
    }
}

function validatePasswords(crtPassword, newPassword, repeatPassword){
    return newPassword === repeatPassword &&
        newPassword != undefined && newPassword != "" &&
        repeatPassword != undefined && repeatPassword != "" &&
        newPassword.length >= 6 && newPassword.length <= 32 ;
}
