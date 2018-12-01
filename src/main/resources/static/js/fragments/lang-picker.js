$(document).ready(() => {
    let combo_lang = $("#combo_lang");
    let iconSrc = "/images/" + $(combo_lang).val() + "-ico.png";
    $("#img_langPicker").attr("src", iconSrc);
    $(combo_lang).change((event) => {
        let newUrl = updateQueryParams(window.location.toString(), {lang: event.target.value});
        window.location.replace(newUrl);
    });
});