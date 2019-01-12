package business_layer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import bd_config.H2TestConfiguration;
import data_layer.domain.Course;
import data_layer.domain.Enrollment;
import data_layer.domain.Group;
import data_layer.domain.Lesson;
import data_layer.domain.LessonTemplate;
import data_layer.domain.Professor;
import data_layer.domain.ProfessorRight;
import data_layer.domain.Student;
import data_layer.domain.User;
import data_layer.repositories.ICourseRepository;
import data_layer.repositories.IEnrollmentRepository;
import data_layer.repositories.IGroupRepository;
import data_layer.repositories.ILessonRepository;
import data_layer.repositories.ILessonTemplateRepository;
import data_layer.repositories.IProfessorRightRepository;
import data_layer.repositories.IUserRepository;
import factory.CourseFactory;
import factory.GroupFactory;
import factory.LessonFactory;
import factory.LessonTemplateFactory;
import factory.ProfessorFactory;
import factory.ProfessorRightFactory;
import factory.StudentFactory;
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

    @Before
    public void emptyDatabase() {
        lessonRepository.deleteAll();
        lessonRepository.flush();
        enrollmentRepository.deleteAll();
        enrollmentRepository.flush();
        lessonTemplateRepository.deleteAll();
        lessonTemplateRepository.flush();
        professorRightRepository.deleteAll();
        professorRightRepository.flush();
        courseRepository.deleteAll();
        courseRepository.flush();
        userRepository.deleteAll();
        userRepository.flush();
        groupRepository.deleteAll();
        groupRepository.flush();
    }

    protected User createUser(User u) {
        return userRepository.save(u);
    }

    protected Enrollment createEnrollment(String profUsername, String courseCode, String groupCode) {
        return createEnrollment(profUsername, courseCode, groupCode, TestConstants.PROF_USERNAME, TestConstants.STUD_USERNAME);
    }

    protected Enrollment createEnrollment(String profUsername, String courseCode, String groupCode
            , String courseCoordinatorUsername, String studentUsername) {
        userRepository.save(ProfessorFactory.generateProfessorBuilder()
                .username(profUsername)
                .build());

        Course course = CourseFactory.generateCourseBuilder()
                .code(courseCode)
                .build();
        Professor professor = null;

        Optional<User> courseCoordinator = userRepository.findByUsername(courseCoordinatorUsername);
        if (courseCoordinator.isPresent()) {
            course.setCoordinator((Professor) courseCoordinator.get());
        } else {
            professor = userRepository.save(ProfessorFactory.generateProfessorBuilder().username(courseCoordinatorUsername).build());
            course.setCoordinator(professor);
        }
        course = courseRepository.save(course);

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

    protected Professor createProfessor(String profUsername) {
        Professor professor = userRepository.save(ProfessorFactory.generateProfessorBuilder()
                .username(profUsername)
                .build());
        return professor;
    }

    protected Student createStudent(String studUsername) {
        Student student = userRepository.save(StudentFactory.generateStudentBuilder()
                .username(studUsername)
                .build());
        return student;
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
        professorRightRepository.saveAll(rights);
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

    protected void addLessonsToEnrollment(Enrollment enrollment, int nrOfLessonsToAdd, LessonType lessonType) {
        LessonTemplate lessonTemplate = enrollment.getCourse().getLessonTemplates().stream()
                .filter(lessonTemplate1 -> lessonTemplate1.getType() == lessonType)
                .findFirst()
                .get();
        while (nrOfLessonsToAdd > 0) {
            Lesson lesson = LessonFactory.generateLessonBuilder()
                    .template(lessonTemplate)
                    .enrollment(enrollment)
                    .build();
            lessonRepository.save(lesson);
            nrOfLessonsToAdd--;
        }
    }

    protected void addLessonTemplatesToCourse(Course course, int nrOfLessonTemplatesToAdd, LessonType lessonType, Byte weight) {
        while (nrOfLessonTemplatesToAdd > 0) {
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
