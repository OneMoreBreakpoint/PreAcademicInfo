package bussiness_layer.utils;

import bussiness_layer.dto.LessonTemplateDto;
import lombok.experimental.UtilityClass;
import utils.LessonType;
import utils.exceptions.UnprocessableEntityException;

import java.util.List;

@UtilityClass
public class LessonTemplateDtoValidator {
    public static void validate(List<LessonTemplateDto> lessonTemplates) {
        validateAfterType(lessonTemplates);
        validateAfterNrOfType(lessonTemplates);
        validateAfterParticularWeight(lessonTemplates);
        validateLessonTemplatesAfterTotalWeight(lessonTemplates);
    }

    private static void validateAfterType(List<LessonTemplateDto> lessonTemplateDtos) {
        boolean hasSeminars = lessonTemplateDtos.stream()
                .anyMatch(lessonTemplateDto -> lessonTemplateDto.getType() == LessonType.SEMINAR);
        boolean hasSeminarPartials = lessonTemplateDtos.stream()
                .anyMatch(lessonTemplateDto -> lessonTemplateDto.getType() == LessonType.PARTIAL_EXAM_SEMINAR);
        if (!hasSeminars && hasSeminarPartials) {
            throw new UnprocessableEntityException();
        }
    }

    private static void validateAfterNrOfType(List<LessonTemplateDto> lessonTemplates) {
        validateAfterNrOfSeminars(lessonTemplates);
        validateAfterNrOfLaboratories(lessonTemplates);
        validateAfterNrOfPartials(lessonTemplates);
    }

    private static void validateAfterNrOfSeminars(List<LessonTemplateDto> lessonTemplates) {
        long nrOfSeminars = getNrOfLessonsOfType(lessonTemplates, LessonType.SEMINAR);
        if (nrOfSeminars > 14 || nrOfSeminars % 7 != 0 || nrOfSeminars < 0) {
            throw new UnprocessableEntityException();
        }
    }

    private static void validateAfterNrOfLaboratories(List<LessonTemplateDto> lessonTemplates) {
        long nrOfLaboratories = getNrOfLessonsOfType(lessonTemplates, LessonType.LABORATORY);
        if (nrOfLaboratories != 14 && nrOfLaboratories != 7) {
            throw new UnprocessableEntityException();
        }
    }

    private static void validateAfterNrOfPartials(List<LessonTemplateDto> lessonTemplates) {
        long nrOfSeminarPartials = getNrOfLessonsOfType(lessonTemplates, LessonType.PARTIAL_EXAM_SEMINAR);
        long nrOfLaboratoryPartials = getNrOfLessonsOfType(lessonTemplates, LessonType.PARTIAL_EXAM_LABORATORY);
        long nrOfCoursePartials = getNrOfLessonsOfType(lessonTemplates, LessonType.PARTIAL_EXAM_COURSE);

        if (nrOfSeminarPartials > 5 || nrOfSeminarPartials < 0
                || nrOfLaboratoryPartials > 5 || nrOfLaboratoryPartials < 0
                || nrOfCoursePartials > 5 || nrOfCoursePartials < 0) {
            throw new UnprocessableEntityException();
        }
    }

    private static void validateAfterParticularWeight(List<LessonTemplateDto> lessonTemplates) {
        validateParticularWeightIsNullForSeminars(lessonTemplates);
        validateParticularWeightIsNotNullForPartialsAndLabs(lessonTemplates);
        validateParticularWeightIsNot0ForPartials(lessonTemplates);
    }

    private static void validateParticularWeightIsNullForSeminars(List<LessonTemplateDto> lessonTemplates) {
        lessonTemplates.stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.SEMINAR)
                .forEach(lessonTemplate -> {
                    if (lessonTemplate.getWeight() != null) {
                        throw new UnprocessableEntityException();
                    }
                });
    }

    private static void validateParticularWeightIsNotNullForPartialsAndLabs(List<LessonTemplateDto> lessonTemplates) {
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
    }

    private static void validateParticularWeightIsNot0ForPartials(List<LessonTemplateDto> lessonTemplates) {
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
