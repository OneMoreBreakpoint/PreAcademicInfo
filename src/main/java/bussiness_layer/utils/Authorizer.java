package bussiness_layer.utils;

import bussiness_layer.dto.*;
import data_layer.domain.Lesson;
import data_layer.domain.PartialExam;

public class Authorizer {

    public static boolean teachingCanReadLesson(TeachingDTO teaching, Short groupCode, LessonDTO lesson){
        return teachingCanWriteLesson(teaching, groupCode, lesson) || teachingHasCoordinatorRights(teaching);
    }

    public static boolean teachingCanWriteLesson(TeachingDTO teaching, Short groupCode, LessonDTO lesson){
        return (lesson.getType() == Lesson.LessonType.LABORATORY && teachingHasLabRightsOverGroup(teaching, groupCode))
                || (lesson.getType() == Lesson.LessonType.SEMINAR && teachingHasSemRightsOverGroup(teaching, groupCode));
    }

    public static boolean teachingCanOnlyReadLesson(TeachingDTO teaching, Short groupCode, LessonDTO lesson){
        return teachingCanReadLesson(teaching, groupCode, lesson) && !teachingCanWriteLesson(teaching, groupCode, lesson);
    }

    public static boolean teachingCanReadExam(TeachingDTO teaching, Short groupCode, PartialExamDTO exam){
        return teachingCanWriteExam(teaching, groupCode, exam) || teachingHasCoordinatorRights(teaching);
    }

    public static boolean teachingCanWriteExam(TeachingDTO teaching, Short groupCode, PartialExamDTO exam){
        return (exam.getType() == PartialExam.PartialExamType.LABORATORY && teachingHasLabRightsOverGroup(teaching, groupCode))
                ||(exam.getType() == PartialExam.PartialExamType.SEMINAR && teachingHasSemRightsOverGroup(teaching, groupCode))
                ||(exam.getType() == PartialExam.PartialExamType.COURSE && teachingHasCoordinatorRights(teaching));
    }

    public static boolean teachingCanOnlyReadExam(TeachingDTO teaching, Short groupCode, PartialExamDTO exam){
        return teachingCanReadExam(teaching, groupCode, exam) && !teachingCanWriteExam(teaching, groupCode, exam);
    }

    public static boolean teachingHasSemRightsOverGroup(TeachingDTO teaching, Short groupCode){
        return teaching.getSeminarGroups().contains(new GroupDTO(groupCode));
    }

    public static boolean teachingHasLabRightsOverGroup(TeachingDTO teaching, Short groupCode){
        return teaching.getLaboratoryGroups().contains(new GroupDTO(groupCode));
    }

    public static boolean teachingHasCoordinatorRights(TeachingDTO teachingDTO){
        return teachingDTO.getCourse().getCoordinator().equals(teachingDTO.getProfessor());
    }
    
}
