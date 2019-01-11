package utils;

import bussiness_layer.dto.EmailNotificationDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailMessage {

    private static final String EMAIL_SUBJECT = "Preacademicinfo: news about {}!";
    private static final String EMAIL_CONTENT_NEW_GRADE = "Lesson type:{1} , attended:{2}\n Grade:{3} , Bonus:{4}." +
            "\n Visit the site for more details!";

    private static final String NOT_GRADED = "not graded";

    public static String getSubject(String courseName) {
        return EMAIL_SUBJECT.replace("{}", courseName);
    }

    public static String getContent(EmailNotificationDto notificationDto) {
        String gradeText = notificationDto.getGrade() != null ? notificationDto.getGrade().toString() : NOT_GRADED;
        String bonusText = notificationDto.getBonus() != null ? notificationDto.getBonus().toString() : NOT_GRADED;


        return EMAIL_CONTENT_NEW_GRADE
                .replace("{1}", notificationDto.getLessonType().toString())
                .replace("{2}", String.valueOf(notificationDto.isAttended()))
                .replace("{3}", gradeText)
                .replace("{4}", bonusText);
    }

}
