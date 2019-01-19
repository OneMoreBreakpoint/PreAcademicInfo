function doExport() {
    let public = document.getElementById("contactChoice1").checked;
    let url = `/app/professor/export?publicType=${public}&course=${getCourseCode()}&group=${getGroupCode()}`;
    $.ajax({
        "Content-Type": "application/json; charset=utf-8",
        Accept: "text/plain; charset=utf-8",
        url: url,
        type: "GET",
        success: (response, textStatus, xhr) => {
            downloadFile(response);
        },
        error: () => {
            displayModal("#modal_failure", true);
        }
    });

}

function getCourseCode(){
    for (let enrlId in enrollments){
        return enrollments[enrlId].course.code;
    }
}

function getGroupCode(){
    for (let enrlId in enrollments){
        return enrollments[enrlId].student.group.code;
    }
}

function downloadFile(filePath){
    let btnWizardDownload = $("#btnWizardDownload");
    $(btnWizardDownload).attr('href', `/${filePath}`);
    $(btnWizardDownload).show();
    $("#btnWizardExport").hide();
    $("#export").click();
}