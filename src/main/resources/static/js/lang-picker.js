$(document).ready(() => {
    let combo_lang = $("#combo_lang");
    let iconSrc = "/images/" + $(combo_lang).val() + "-ico.png";
    $("#img_langPicker").attr("src", iconSrc);
    $(combo_lang).change((event) => {
        window.location.replace("?lang=" + event.target.value);
    });
});