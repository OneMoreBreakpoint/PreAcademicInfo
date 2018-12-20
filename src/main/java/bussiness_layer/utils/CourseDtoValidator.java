package bussiness_layer.utils;

import bussiness_layer.dto.CourseDto;
import bussiness_layer.dto.LessonTemplateDto;
import lombok.experimental.UtilityClass;
import utils.LessonType;
import utils.exceptions.UnprocessableEntityException;

import java.util.List;

@UtilityClass
public class CourseDtoValidator {
    public static void validate(CourseDto courseDto) {
        List<LessonTemplateDto> lessonTemplates = courseDto.getLessonTemplates();
        validateLessonTemplatesAfterType(lessonTemplates);
        validateLessonTemplatesAfterNrOfType(lessonTemplates);
        validateLessonTemplatesAfterParticularWeight(lessonTemplates);
        validateLessonTemplatesAfterTotalWeight(lessonTemplates);
    }

    private static void validateLessonTemplatesAfterType(List<LessonTemplateDto> lessonTemplateDtos) {
        boolean hasSeminars = lessonTemplateDtos.stream()
                .anyMatch(lessonTemplateDto -> lessonTemplateDto.getType() == LessonType.SEMINAR);
        boolean hasSeminarPartials = lessonTemplateDtos.stream()
                .anyMatch(lessonTemplateDto -> lessonTemplateDto.getType() == LessonType.PARTIAL_EXAM_SEMINAR);
        if (!hasSeminars && hasSeminarPartials) {
            throw new UnprocessableEntityException();
        }
    }

    private static void validateLessonTemplatesAfterNrOfType(List<LessonTemplateDto> lessonTemplates) {
        long nrOfSeminars = getNrOfLessonsOfType(lessonTemplates, LessonType.SEMINAR);
        long nrOfLaboratories = getNrOfLessonsOfType(lessonTemplates, LessonType.LABORATORY);
        long nrOfSeminarPartials = getNrOfLessonsOfType(lessonTemplates, LessonType.PARTIAL_EXAM_SEMINAR);
        long nrOfLaboratoryPartials = getNrOfLessonsOfType(lessonTemplates, LessonType.PARTIAL_EXAM_LABORATORY);
        long nrOfCoursePartials = getNrOfLessonsOfType(lessonTemplates, LessonType.PARTIAL_EXAM_COURSE);

        if (nrOfSeminars > 14 || nrOfSeminars % 7 != 0 || nrOfSeminars < 0) {
            throw new UnprocessableEntityException();
        }
        if (nrOfLaboratories != 14 && nrOfLaboratories != 7) {
            throw new UnprocessableEntityException();
        }
        if (nrOfSeminarPartials > 5 || nrOfSeminarPartials < 0
                || nrOfLaboratoryPartials > 5 || nrOfLaboratoryPartials < 0
                || nrOfCoursePartials > 5 || nrOfCoursePartials < 0) {
            throw new UnprocessableEntityException();
        }
    }

    private static void validateLessonTemplatesAfterParticularWeight(List<LessonTemplateDto> lessonTemplates) {
        lessonTemplates.stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.SEMINAR)
                .forEach(lessonTemplate -> {
                    if (lessonTemplate.getWeight() != null) {
                        throw new UnprocessableEntityException();
                    }
                });
        lessonTemplates.stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.PARTIAL_EXAM_SEMINAR
                        || lessonTemplate.getType() == LessonType.PARTIAL_EXAM_LABORATORY
                        || lessonTemplate.getType() == LessonType.PARTIAL_EXAM_COURSE
                        || lessonTemplate.getType() == LessonType.LABORATORY)
                .forEach(lessonTemplate -> {
                    if (lessonTemplate.getWeight() == null) {
                        throw new UnprocessableEntityException();
                    }
                });
        lessonTemplates.stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.PARTIAL_EXAM_SEMINAR
                        || lessonTemplate.getType() == LessonType.PARTIAL_EXAM_LABORATORY
                        || lessonTemplate.getType() == LessonType.PARTIAL_EXAM_COURSE)
                .forEach(lessonTemplate -> {
                    if (lessonTemplate.getWeight() == 0) {
                        throw new UnprocessableEntityException();
                    }
                });
    }

    private static void validateLessonTemplatesAfterTotalWeight(List<LessonTemplateDto> lessonTemplates) {
        byte totalWeight = lessonTemplates.stream()
                .filter(lessonTemplate -> lessonTemplate.getType() != LessonType.SEMINAR)
                .map(LessonTemplateDto::getWeight)
                .reduce((x, y) -> (byte) (x + y))
                .orElseThrow(UnprocessableEntityException::new);
        if (totalWeight != 100) {
            throw new UnprocessableEntityException();
        }
    }

    private static int getNrOfLessonsOfType(List<LessonTemplateDto> lessonTemplates, LessonType lessonType) {
        return (int) lessonTemplates.stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == lessonType)
                .count();
    }
}
