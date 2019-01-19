package bussiness_layer.utils;

import bussiness_layer.dto.CourseDto;
import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.StudentDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApachePOIExcelWrite {

    private static String fileName;

    public ApachePOIExcelWrite() { }

    public String exportData(List listToExport,Boolean isPublic) {

        this.fileName ="src\\main\\resources\\static\\export\\Excel.xlsx";
        List<EnrollmentDto> enrollments=listToExport;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");

        int rowNum = 0;
        System.out.println("Creating excel");

        Row row2 = sheet.createRow(rowNum++);
        int colNum2=0;
        for (Object field : headerForExcel(enrollments.get(0),isPublic)) {
            Cell cell = row2.createCell(colNum2++);
            if (field instanceof String) {
                cell.setCellValue((String) field);
            } else if (field instanceof Integer) {
                cell.setCellValue((Integer) field);
            }else if (field instanceof Double) {
                cell.setCellValue((Double) field);
            }else if (field instanceof Boolean) {
                cell.setCellValue((Boolean) field);
            }else if (field instanceof Byte) {
                cell.setCellValue((Byte) field);
            }
        }

        Row row3 = sheet.createRow(rowNum++);

        for (EnrollmentDto enrolement : enrollments) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : getDataArray(enrolement,isPublic)) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                }else if (field instanceof Boolean) {
                    cell.setCellValue((Boolean) field);
                }else if (field instanceof Byte) {
                    cell.setCellValue((Byte) field);
                }
            }
        }

        try {

            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");

        return "export/Excel.xlsx";
    }

    /**
     *   Returns an array of Objects representing the enrolment data to write in an row.
     *   Parameters: enrolment EnrollmentDto - the enrolment wich represents the data
     *               isPrivate Boolean - flag to determine wheter to display the registration nr. or a name of a student
     */
    private ArrayList getDataArray(EnrollmentDto enrolement,Boolean isPublic) {
        ArrayList dataToSend = new ArrayList<>();

        StudentDto s = enrolement.getStudent();
        CourseDto c = enrolement.getCourse();
        List<LessonDto> l = enrolement.getLessons();

        //Student data
        if(isPublic) {
            dataToSend.add(s.getLastName());
            dataToSend.add(s.getFathersInitials());
            dataToSend.add(s.getFirstName());
        }else{
            dataToSend.add(s.getRegistrationNr());
        }
        dataToSend.add(s.getGroup().getCode());

        //Course data
        dataToSend.add(c.getCode());

        int totalAttendenceLab=0;
        double attendanceLab=0;
        int totalAttendenceSem=0;
        double attendanceSem=0;
        double bonus=0;

        double finalGrade=0;

        //Lessons data
        for(LessonDto lesson : l){
            switch (lesson.getTemplate().getType()) {
                case SEMINAR:
                    dataToSend.add(lesson.isAttended());
                    totalAttendenceSem += 1;
                    if (lesson.isAttended())
                        attendanceSem += 1;
                    if (lesson.getBonus() != null) {
                        dataToSend.add(lesson.getBonus());
                        bonus += lesson.getBonus();
                    } else
                        dataToSend.add(0);
                    if (lesson.getTemplate().getWeight()!=null && lesson.getTemplate().getWeight()!= 0 ) {
                        if (lesson.getGrade() != null) {
                            dataToSend.add(lesson.getGrade());
                            finalGrade += (lesson.getGrade() * lesson.getTemplate().getWeight() / 100);
                        }
                        else{
                            dataToSend.add(0);
                            finalGrade += (0 * lesson.getTemplate().getWeight() / 100);
                        }
                    }
                    break;
                case LABORATORY:
                    dataToSend.add(lesson.isAttended());
                    totalAttendenceLab += 1;
                    if (lesson.isAttended())
                        attendanceLab += 1;
                    if (lesson.getBonus() != null) {
                        dataToSend.add(lesson.getBonus());
                        bonus += lesson.getBonus();
                    } else
                        dataToSend.add(0);
                    if (lesson.getTemplate().getWeight()!=null && lesson.getTemplate().getWeight()!= 0 ) {
                        if (lesson.getGrade() != null) {
                            dataToSend.add(lesson.getGrade());
                            finalGrade += (lesson.getGrade() * lesson.getTemplate().getWeight() / 100);
                        }
                        else{
                            dataToSend.add(0);
                            finalGrade += (0 * lesson.getTemplate().getWeight() / 100);
                        }
                    }
                    break;
                case PARTIAL_EXAM_SEMINAR:
                    if(lesson.getGrade()!=null) {
                        dataToSend.add(lesson.getGrade());
                        finalGrade += (lesson.getGrade() * lesson.getTemplate().getWeight() / 100);
                    }else{
                        dataToSend.add(0);
                        finalGrade += (0 * lesson.getTemplate().getWeight() / 100);
                    }
                    break;
                case PARTIAL_EXAM_LABORATORY:
                    if(lesson.getGrade()!=null) {
                        dataToSend.add(lesson.getGrade());
                        finalGrade += (lesson.getGrade() * lesson.getTemplate().getWeight() / 100);
                    }else{
                        dataToSend.add(0);
                        finalGrade += (0 * lesson.getTemplate().getWeight() / 100);
                    }
                    break;
                case PARTIAL_EXAM_COURSE:
                    if(lesson.getGrade()!=null) {
                        dataToSend.add(lesson.getGrade());
                        finalGrade += (lesson.getGrade() * lesson.getTemplate().getWeight() / 100);
                    }else{
                        dataToSend.add(0);
                        finalGrade += (0 * lesson.getTemplate().getWeight() / 100);
                    }
                    break;
                default:
                    break;
            }
        }

        dataToSend.add(bonus);
        if(totalAttendenceSem!=0)
            dataToSend.add(attendanceSem/totalAttendenceSem);
        else
            dataToSend.add(0);
        if(totalAttendenceLab!=0)
            dataToSend.add(attendanceLab/totalAttendenceLab);
        else
            dataToSend.add(0);
        dataToSend.add(finalGrade);

        return dataToSend;
    }

    private ArrayList<String> headerForExcel(EnrollmentDto enrolement,Boolean isPublic) {
        ArrayList<String> headerStrings=new ArrayList<String>();
        List<LessonDto> l = enrolement.getLessons();

        //Student data
        if(isPublic) {
            headerStrings.add("Name");
            headerStrings.add("Father's initial");
            headerStrings.add("First Name");
        }else{
            headerStrings.add("Student ID");
        }
        headerStrings.add("Group");

        //Course data
        headerStrings.add("Course ID");

        int totalAttendenceLab=0;
        int totalAttendenceSem=0;
        int totalAttendencePartSem=0;
        int totalAttendencePartLab=0;
        int totalAttendencePartCourse=0;

        //Lessons data
        for(LessonDto lesson : l){
            switch (lesson.getTemplate().getType()) {
                case SEMINAR:
                    totalAttendenceSem += 1;
                    headerStrings.add("Seminar "+totalAttendenceSem+" at.");
                    headerStrings.add("Seminar "+totalAttendenceSem+" bonus");
                    if (lesson.getTemplate().getWeight()!=null && lesson.getTemplate().getWeight()!= 0)
                        headerStrings.add("Seminar "+totalAttendenceSem+" grade");
                    break;
                case LABORATORY:
                    totalAttendenceLab += 1;
                    headerStrings.add("Laboratory "+totalAttendenceLab+" at.");
                    headerStrings.add("Laboratory "+totalAttendenceLab+" bonus");
                    if (lesson.getTemplate().getWeight()!=null && lesson.getTemplate().getWeight()!= 0 )
                        headerStrings.add("Laboratory "+totalAttendenceLab+" grade");
                    break;
                case PARTIAL_EXAM_SEMINAR:
                    totalAttendencePartSem+=1;
                    headerStrings.add("Partial Exam Sem "+totalAttendencePartSem);
                    break;
                case PARTIAL_EXAM_LABORATORY:
                    totalAttendencePartLab+=1;
                    headerStrings.add("Partial Exam Sem "+totalAttendencePartLab);
                    break;
                case PARTIAL_EXAM_COURSE:
                    totalAttendencePartCourse+=1;
                    headerStrings.add("Partial Exam Sem "+totalAttendencePartCourse);
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

