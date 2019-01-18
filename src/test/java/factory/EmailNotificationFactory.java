package factory;

import java.util.ArrayList;
import java.util.List;

import bussiness_layer.dto.EmailNotificationDto;
import utils.LessonType;

public class EmailNotificationFactory {

    private static EmailNotificationDto generateEmailNotificationDto() {

        return EmailNotificationDto.builder()
                .lessonNumber((byte) 1)
                .lessonType(LessonType.LABORATORY)
                .courseName("LFTC")
                .emailAddress("cpir2084@scs.ubbcluj.ro")
                .bonus((byte) 2)
                .grade((byte) 10)
                .attended(true)
                .build();
    }

    public static List<EmailNotificationDto> generateEmailNotificationDtoList(int number) {

        List<EmailNotificationDto> emailNotificationDtos = new ArrayList<>();
        for (int i = 0; i < number; ++i) {
            emailNotificationDtos.add(generateEmailNotificationDto());
        }
        return emailNotificationDtos;
    }

}
