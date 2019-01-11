package bussiness_layer.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import utils.LessonType;

@Getter
@Setter
@Builder
public class EmailNotificationDto {

    @NotNull
    private boolean attended;

    @Max(10)
    @Min(1)
    private Byte grade;

    @Min(-10)
    @Max(10)
    private Byte bonus;

    @NotNull
    private byte lessonNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LessonType lessonType;

    @NotNull
    @Email
    private String emailAddress;

    @NotNull
    private String courseName;

}
