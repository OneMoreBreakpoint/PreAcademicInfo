package factory;

import bussiness_layer.dto.LessonTemplateDto;
import data_layer.domain.LessonTemplate;
import utils.LessonType;

public class LessonTemplateFactory {
    public static LessonTemplate.LessonTemplateBuilder generateLessonTemplateBuilder(){
        return LessonTemplate.builder()
                .type(LessonType.SEMINAR)
                .nr((byte)1);
    }

    public static LessonTemplate generateLessonTemplate(){
        return generateLessonTemplateBuilder().build();
    }

    public static LessonTemplateDto.LessonTemplateDtoBuilder generateLessonTemplateDtoBuilder(){
        return LessonTemplateDto.builder()
                .type(LessonType.SEMINAR)
                .nr((byte)1);
    }

    public static LessonTemplateDto generateLessonTemplateDto(){
        return generateLessonTemplateDtoBuilder().build();
    }
}
