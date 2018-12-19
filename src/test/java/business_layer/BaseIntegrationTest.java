package business_layer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import data_layer.domain.*;
import data_layer.repositories.*;
import factory.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import bd_config.H2TestConfiguration;
import utils.LessonType;
import utils.RightType;
import utils.TestConstants;

/**
 * Utilitary class for tests.
 * Here you could add methods for inserting data into db, for having presets
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = H2TestConfiguration.class)
public abstract class BaseIntegrationTest {

    @Autowired
    protected IUserRepository userRepository;

    @Autowired
    protected IGroupRepository groupRepository;

    @Autowired
    protected ICourseRepository courseRepository;

    @Autowired
    protected IProfessorRightRepository professorRightRepository;

    @Autowired
    protected IEnrollmentRepository enrollmentRepository;

    @Autowired
    protected ILessonRepository lessonRepository;

    @Autowired
    protected ILessonTemplateRepository lessonTemplateRepository;


    protected User createUser(User u) {
        return userRepository.save(u);
    }

    protected Enrollment createEnrollment(String profUsername, String courseCode, String groupCode) {
        return createEnrollment(profUsername, courseCode, groupCode, TestConstants.USERNAME, TestConstants.USERNAME);
    }

    protected Enrollment createEnrollment(String profUsername, String courseCode, String groupCode
            , String courseCoordinatorUsername, String studentUsername) {
        Professor professor = userRepository.save(ProfessorFactory.generateProfessorBuilder()
                .username(profUsername)
                .build());
        Course course = courseRepository.save(CourseFactory.generateCourseBuilder()
                .coordinator(userRepository.save(ProfessorFactory.generateProfessorBuilder().username(courseCoordinatorUsername).build()))
                .code(courseCode)
                .build());
        Group group = groupRepository.save(GroupFactory.generateGroupBuilder()
                .code(groupCode)
                .build());
        Student student = userRepository.save(StudentFactory.generateStudentBuilder()
                .group(group)
                .username(studentUsername)
                .build());
        Enrollment enrollment = enrollmentRepository.save(Enrollment.builder()
                .course(course)
                .student(student)
                .build());
        LessonTemplate lessonTemplate = lessonTemplateRepository.save(LessonTemplateFactory.generateLessonTemplateBuilder()
                .course(course)
                .build());
        Lesson lesson = lessonRepository.save(LessonFactory.generateLessonBuilder()
                .enrollment(enrollment)
                .template(lessonTemplate)
                .build());
        enrollment.setLessons(new ArrayList<>(Arrays.asList(lesson)));
        course.setLessonTemplates(new ArrayList<>(Arrays.asList(lessonTemplate)));
        return enrollment;
    }

    protected List<ProfessorRight> createProfessorRights(String profUsername, String courseCode, String groupCode) {
        Professor professor = (Professor) userRepository.findByUsername(profUsername).get();
        Course course = courseRepository.findByCode(courseCode).get();
        Group group = groupRepository.findByCode(groupCode).get();

        List<ProfessorRight> rights = new ArrayList<>();
        rights.add(createProfessorRight(professor, course, group, LessonType.LABORATORY, RightType.READ));
        rights.add(createProfessorRight(professor, course, group, LessonType.LABORATORY, RightType.WRITE));
        rights.add(createProfessorRight(professor, course, group, LessonType.PARTIAL_EXAM_LABORATORY, RightType.READ));
        rights.add(createProfessorRight(professor, course, group, LessonType.PARTIAL_EXAM_LABORATORY, RightType.WRITE));
        return rights;
    }

    protected ProfessorRight createProfessorRight(Professor professor, Course course, Group group
            , LessonType lessonType, RightType rightType) {
        return professorRightRepository.save(ProfessorRightFactory.generateProfessorRightBuilder()
                .professor(professor)
                .course(course)
                .group(group)
                .lessonType(lessonType)
                .rightType(rightType)
                .build());
    }

    protected void addLessonsToEnrollment(Enrollment enrollment, int nrOfLessonsToAdd, LessonType lessonType){
        LessonTemplate lessonTemplate = enrollment.getCourse().getLessonTemplates().stream()
                .filter(lessonTemplate1 -> lessonTemplate1.getType() == lessonType)
                .findFirst()
                .get();
        while(nrOfLessonsToAdd > 0){
            Lesson lesson = LessonFactory.generateLessonBuilder()
                    .template(lessonTemplate)
                    .enrollment(enrollment)
                    .build();
            lessonRepository.save(lesson);
            nrOfLessonsToAdd--;
        }
    }

    protected void addLessonTemplatesToCourse(Course course, int nrOfLessonTemplatesToAdd, LessonType lessonType, Double weight){
        while(nrOfLessonTemplatesToAdd > 0){
            LessonTemplate lessonTemplate = LessonTemplateFactory.generateLessonTemplateBuilder()
                    .type(lessonType)
                    .course(course)
                    .weight(weight)
                    .build();
            course.getLessonTemplates().add(lessonTemplateRepository.save(lessonTemplate));
            lessonTemplateRepository.flush();
            courseRepository.flush();
            nrOfLessonTemplatesToAdd--;
        }
    }

}
