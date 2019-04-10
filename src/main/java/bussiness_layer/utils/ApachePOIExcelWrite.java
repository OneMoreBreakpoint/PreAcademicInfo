package bussiness_layer.utils;

import bussiness_layer.dto.CourseDto;
import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.StudentDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ApachePOIExcelWrite {

    private static final String PARTIAL_EXAM_SEM = "Partial Exam Sem ";
    private static final String SEMINAR = "Seminar ";
    private static final String LABORATORY = "Laboratory ";
    private static final String FILENAME = "src\\main\\resources\\static\\export\\Excel.xlsx";

    public String exportData(List listToExport, Boolean isPublic) {

        List<EnrollmentDto> enrollments = listToExport;

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFSheet sheet = workbook.createSheet("Datatypes in Java");

            int rowNum = 0;

            log.info("Creating excel");

            Row row2 = sheet.createRow(rowNum++);
            int colNum2 = 0;
            for (Object field : headerForExcel(enrollments.get(0), isPublic)) {
                colNum2 = getColNum2(row2, colNum2, field);
            }

            for (EnrollmentDto enrolement : enrollments) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (Object field : getDataArray(enrolement, isPublic)) {
                    colNum = getColNum2(row, colNum, field);
                }
            }
            FileOutputStream outputStream = new FileOutputStream(FILENAME);
            workbook.write(outputStream);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("Done");

        return "export/Excel.xlsx";
    }

    private int getColNum2(Row row2, int colNum2, Object field) {
        Cell cell = row2.createCell(colNum2++);
        if (field instanceof String) {
            cell.setCellValue((String) field);
        } else if (field instanceof Integer) {
            cell.setCellValue((Integer) field);
        } else if (field instanceof Double) {
            cell.setCellValue((Double) field);
        } else if (field instanceof Boolean) {
            cell.setCellValue((Boolean) field);
        } else if (field instanceof Byte) {
            cell.setCellValue((Byte) field);
        }
        return colNum2;
    }

    /**
     * Returns an array of Objects representing the enrolment data to write in an row.
     * Parameters: enrolment EnrollmentDto - the enrolment wich represents the data
     * isPrivate Boolean - flag to determine wheter to display the registration nr. or a name of a student
     */

    private void getStudentData(EnrollmentDto enrollment, boolean isPublic, ArrayList dataToSend){
        StudentDto s = enrollment.getStudent();

        if (isPublic) {
            dataToSend.add(s.getLastName());
            dataToSend.add(s.getFathersInitials());
            dataToSend.add(s.getFirstName());
        } else {
            dataToSend.add(s.getRegistrationNr());
        }
        dataToSend.add(s.getGroup().getCode());
    }

    private ArrayList getDataArray(EnrollmentDto enrolement, boolean isPublic) {
        ArrayList dataToSend = new ArrayList<>();

        CourseDto c = enrolement.getCourse();
        List<LessonDto> l = enrolement.getLessons();

        getStudentData(enrolement,isPublic,dataToSend);
        //Student data

        //Course data
        dataToSend.add(c.getCode());

        int totalAttendenceLab = 0;
        double attendanceLab = 0;
        int totalAttendenceSem = 0;
        double attendanceSem = 0;
        double bonus = 0;

        double finalGrade = 0;

        //Lessons data
        for (LessonDto lesson : l) {
            switch (lesson.getTemplate().getType()) {
                case SEMINAR:
                    dataToSend.add(lesson.isAttended());
                    totalAttendenceSem += 1;
                    if (lesson.isAttended())
                        attendanceSem += 1;
                    bonus = getBonus(dataToSend, bonus, lesson);
                    finalGrade = getFinalGrade(dataToSend, finalGrade, lesson);
                    break;
                case LABORATORY:
                    dataToSend.add(lesson.isAttended());
                    totalAttendenceLab += 1;
                    if (lesson.isAttended())
                        attendanceLab += 1;
                    bonus = getBonus(dataToSend, bonus, lesson);
                    finalGrade = getFinalGrade(dataToSend, finalGrade, lesson);
                    break;
                default: //PARTIAL_EXAMS
                    finalGrade = getFinalGrade2(dataToSend, finalGrade, lesson);
                    break;
            }
        }

        dataToSend.add(bonus);
        if (totalAttendenceSem != 0) {
            dataToSend.add(attendanceSem / totalAttendenceSem);
        } else {
            dataToSend.add(0);
        }
        if (totalAttendenceLab != 0)
            dataToSend.add(attendanceLab / totalAttendenceLab);
        else
            dataToSend.add(0);
        dataToSend.add(finalGrade);

        return dataToSend;
    }

    private double getBonus(ArrayList dataToSend, double bonus, LessonDto lesson) {
        if (lesson.getBonus() != null) {
            dataToSend.add(lesson.getBonus());
            bonus += lesson.getBonus();
        } else
            dataToSend.add(0);
        return bonus;
    }

    private double getFinalGrade2(ArrayList dataToSend, double finalGrade, LessonDto lesson) {
        if (lesson.getGrade() != null) {
            dataToSend.add(lesson.getGrade());
            finalGrade += (lesson.getGrade() * lesson.getTemplate().getWeight() / 100);
        } else {
            dataToSend.add(0);
        }
        return finalGrade;
    }

    private double getFinalGrade(ArrayList dataToSend, double finalGrade, LessonDto lesson) {
        if (lesson.getTemplate().getWeight() != null && lesson.getTemplate().getWeight() != 0) {
            finalGrade = getFinalGrade2(dataToSend, finalGrade, lesson);
        }
        return finalGrade;
    }

    private ArrayList<String> headerForExcel(EnrollmentDto enrolement, Boolean isPublic) {
        ArrayList<String> headerStrings = new ArrayList<>();
        List<LessonDto> l = enrolement.getLessons();

        //Student data
        if (isPublic) {
            headerStrings.add("Name");
            headerStrings.add("Father's initial");
            headerStrings.add("First Name");
        } else {
            headerStrings.add("Student ID");
        }
        headerStrings.add("Group");

        //Course data
        headerStrings.add("Course ID");

        int totalAttendenceLab = 0;
        int totalAttendenceSem = 0;
        int totalAttendencePartSem = 0;
        int totalAttendencePartLab = 0;
        int totalAttendencePartCourse = 0;

        //Lessons data
        for (LessonDto lesson : l) {
            switch (lesson.getTemplate().getType()) {
                case SEMINAR:
                    totalAttendenceSem += 1;
                    headerStrings.add(SEMINAR + totalAttendenceSem + " at.");
                    headerStrings.add(SEMINAR + totalAttendenceSem + " bonus");
                    if (lesson.getTemplate().getWeight() != null && lesson.getTemplate().getWeight() != 0)
                        headerStrings.add(SEMINAR + totalAttendenceSem + " grade");
                    break;
                case LABORATORY:
                    totalAttendenceLab += 1;
                    headerStrings.add(
                            +totalAttendenceLab + " at.");
                    headerStrings.add(LABORATORY + totalAttendenceLab + " bonus");
                    if (lesson.getTemplate().getWeight() != null && lesson.getTemplate().getWeight() != 0)
                        headerStrings.add(LABORATORY + totalAttendenceLab + " grade");
                    break;
                case PARTIAL_EXAM_SEMINAR:
                    totalAttendencePartSem += 1;
                    headerStrings.add(PARTIAL_EXAM_SEM + totalAttendencePartSem);
                    break;
                case PARTIAL_EXAM_LABORATORY:
                    totalAttendencePartLab += 1;
                    headerStrings.add(PARTIAL_EXAM_SEM + totalAttendencePartLab);
                    break;
                case PARTIAL_EXAM_COURSE:
                    totalAttendencePartCourse += 1;
                    headerStrings.add(PARTIAL_EXAM_SEM + totalAttendencePartCourse);
                    break;
                default:
                    break;
            }
        }
        headerStrings.add("Bonus");
        headerStrings.add("Seminar attenence %");
        headerStrings.add("Seminar attenence %");
        headerStrings.add("Laboratory attenence %");
        headerStrings.add("Final Grade");

        return headerStrings;
    }
}

