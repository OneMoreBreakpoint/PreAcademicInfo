function create_user_details() {
    var user_details = get_user_details();
    // create_profile_photo();
    create_personal_details(user_details);
    create_password_settings(user_details);
    create_user_settings(user_details);
}

function create_profile_photo() {

}

function create_personal_details(user_data) {
    $("#personal-details").after(create_row("Email: ", user_data['Email']));
    $("#personal-details").after(create_row("User Name: ", user_data['UserName']));
    $("#personal-details").after(create_row("Last Name: ", user_data['Prenume']));
    $("#personal-details").after(create_row("First Name: ", user_data['Nume']));
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

function create_password_settings(user_details) {
    $("#password-settings").after(
        "<label style='color: #8c8c8c'>Current Password: </label>" +
        "<input style='width: 40%' type='text' id='current-password' class='form-control'>" +
        "<label style='color: #8c8c8c'>New Password: </label>" +
        "<input style='width: 40%' type='text' id='new-password' class='form-control'>" +
        "<label style='color: #8c8c8c'>Repeat password: </label>" +
        "<input style='width: 40%' type='text' id='repeat-password' class='form-control'>"
    );
}

function create_user_settings(user_details) {
    // $("#profile-settings").after("<input type='checkbox'><label>Automatically notify me through email when I receive a grade.</label>");
    $("#profile-settings").after(
        "<li style='display: block; padding-top: 10px'>" +
        "<div class='material-switch'>" +
        "<input id='someSwitchOptionDefault' name='someSwitchOption001' type='checkbox' style='padding: 0px'>" +
        "<label for='someSwitchOptionDefault' class='label-default' style='padding: 0px'></label>" +
        "&nbsp;&nbsp;Automatically notify me through email when I receive a grade." +
        "</div>" +
        "</li>");
}


function get_user_details() {
    var user_data = {"Nume": "Oana", "Prenuma": "Albu", "Email": "oana@email.com", "UserName": "oalbu2135"};
    return user_data;
}