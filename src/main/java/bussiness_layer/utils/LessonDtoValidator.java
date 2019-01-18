package bussiness_layer.utils;

import bussiness_layer.dto.LessonDto;
import lombok.experimental.UtilityClass;
import utils.exceptions.UnprocessableEntityException;

@UtilityClass
public class LessonDtoValidator {
    public static void validate(LessonDto lesson) {
        if (!lesson.isAttended() && lesson.getBonus() != null && lesson.getBonus() != 0) {
            throw new UnprocessableEntityException();
        }
    }
}
