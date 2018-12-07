package bussiness_layer.utils;

import bussiness_layer.dto.GroupDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.PartialExamDto;
import bussiness_layer.dto.TeachingDto;
import data_layer.domain.Lesson;
import data_layer.domain.PartialExam;

public class Authorizer {

    public static boolean teachingCanReadLesson(TeachingDto teaching, String groupCode, LessonDto lesson) {
        return teachingCanWriteLesson(teaching, groupCode, lesson) || teachingHasCoordinatorRights(teaching);
    }

    public static boolean teachingCanWriteLesson(TeachingDto teaching, String groupCode, LessonDto lesson) {
        return (lesson.getType() == Lesson.LessonType.LABORATORY && teachingHasLabRightsOverGroup(teaching, groupCode))
                || (lesson.getType() == Lesson.LessonType.SEMINAR && teachingHasSemRightsOverGroup(teaching, groupCode));
    }

    public static boolean teachingCanOnlyReadLesson(TeachingDto teaching, String groupCode, LessonDto lesson) {
        return teachingCanReadLesson(teaching, groupCode, lesson) && !teachingCanWriteLesson(teaching, groupCode, lesson);
    }

    public static boolean teachingCanReadExam(TeachingDto teaching, String groupCode, PartialExamDto exam) {
        return teachingCanWriteExam(teaching, groupCode, exam) || teachingHasCoordinatorRights(teaching);
    }

    public static boolean teachingCanWriteExam(TeachingDto teaching, String groupCode, PartialExamDto exam) {
        return (exam.getType() == PartialExam.PartialExamType.LABORATORY && teachingHasLabRightsOverGroup(teaching, groupCode))
                || (exam.getType() == PartialExam.PartialExamType.SEMINAR && teachingHasSemRightsOverGroup(teaching, groupCode))
                || (exam.getType() == PartialExam.PartialExamType.COURSE && teachingHasCoordinatorRights(teaching));
    }

    public static boolean teachingCanOnlyReadExam(TeachingDto teaching, String groupCode, PartialExamDto exam) {
        return teachingCanReadExam(teaching, groupCode, exam) && !teachingCanWriteExam(teaching, groupCode, exam);
    }

    public static boolean teachingHasSemRightsOverGroup(TeachingDto teaching, String groupCode) {
        return teaching.getSeminarGroups().contains(new GroupDto(groupCode));
    }

    public static boolean teachingHasLabRightsOverGroup(TeachingDto teaching, String groupCode) {
        return teaching.getLaboratoryGroups().contains(new GroupDto(groupCode));
    }

    public static boolean teachingHasCoordinatorRights(TeachingDto teachingDTO) {
        return teachingDTO.getCourse().getCoordinator().equals(teachingDTO.getProfessor());
    }

}
