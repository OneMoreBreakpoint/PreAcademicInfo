$(document).ready(() => {
    console.log("ready");
    $("#combo_lang").change((event) => {
        console.log("change");
        window.location.replace("?lang=" + event.value);
    });
});