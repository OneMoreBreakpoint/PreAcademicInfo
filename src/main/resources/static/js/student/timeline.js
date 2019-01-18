function hasMinimumAttendance(seminarAttendance, laboratoryAttendance) {
    seminarAttendance = (seminarAttendance === undefined) ? 100 : seminarAttendance;
    laboratoryAttendance = (laboratoryAttendance === undefined) ? 100 : laboratoryAttendance;
    const MIN_SEM_ATT = 75, MIN_LAB_ATT = 90;
    return seminarAttendance >= MIN_SEM_ATT && laboratoryAttendance >= MIN_LAB_ATT;
}