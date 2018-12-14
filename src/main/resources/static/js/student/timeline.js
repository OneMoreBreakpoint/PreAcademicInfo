function updateTotalAttendanceView(enrlRow, totalLabAttendance, totalSemAttendance) {
    let totalAttendanceCell = $(enrlRow).children("td.attendances-cell").first();
    let precisionLab = (totalLabAttendance < 100 ? 2 : 0), precisionSem = (totalSemAttendance < 100 ? 2 : 0);
    let totalAttendanceViewText;
    totalAttendanceViewText = `${totalSemAttendance.toFixed(precisionSem)}% / ${totalLabAttendance.toFixed(precisionLab)}%`;
    $(totalAttendanceCell).text(totalAttendanceViewText);
    if (!hasMinimumAttendance(totalSemAttendance, totalLabAttendance)) {
        $(totalAttendanceCell).addClass('err');
    } else {
        $(totalAttendanceCell).removeClass('err');
    }
}

function hasMinimumAttendance(seminarAttendance, laboratoryAttendance) {
    const MIN_SEM_ATT = 75, MIN_LAB_ATT = 90;
    return seminarAttendance >= MIN_SEM_ATT && laboratoryAttendance >= MIN_LAB_ATT;
}