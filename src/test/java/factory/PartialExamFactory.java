package factory;

import bussiness_layer.dto.PartialExamDto;
import data_layer.domain.PartialExam;

public class PartialExamFactory {
    public static PartialExam.PartialExamBuilder generatePartialExamBuilder() {
        return PartialExam.builder()
                .type(PartialExam.PartialExamType.COURSE)
                .nr((byte) 1);
    }

    public static PartialExam generatePartialExam() {
        return generatePartialExamBuilder().build();
    }

    public static PartialExamDto.PartialExamDtoBuilder generatePartialExamDtoBuilder() {
        return PartialExamDto.builder()
                .type(PartialExam.PartialExamType.COURSE)
                .nr((byte) 1);
    }

    public static PartialExamDto generatePartialDtoExam() {
        return generatePartialExamDtoBuilder().build();
    }
}
