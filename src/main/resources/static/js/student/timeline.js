function hasMinimumAttendance(seminarAttendance, laboratoryAttendance) {
    if(seminarAttendance === undefined){
        seminarAttendance = 100;
    }
    if(laboratoryAttendance === undefined){
        laboratoryAttendance = 100;
    }
    const MIN_SEM_ATT = 75, MIN_LAB_ATT = 90;
    return seminarAttendance >= MIN_SEM_ATT && laboratoryAttendance >= MIN_LAB_ATT;
}