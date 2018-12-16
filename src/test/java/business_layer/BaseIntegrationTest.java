package business_layer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import bd_config.H2TestConfiguration;
import data_layer.domain.Course;
import data_layer.domain.Enrollment;
import data_layer.domain.Group;
import data_layer.domain.Lesson;
import data_layer.domain.Professor;
import data_layer.domain.ProfessorRight;
import data_layer.domain.Student;
import data_layer.domain.User;
import data_layer.repositories.ICourseRepository;
import data_layer.repositories.IEnrollmentRepository;
import data_layer.repositories.IGroupRepository;
import data_layer.repositories.ILessonRepository;
import data_layer.repositories.IProfessorRightRepository;
import data_layer.repositories.IUserRepository;
import factory.CourseFactory;
import factory.GroupFactory;
import factory.LessonFactory;
import factory.ProfessorFactory;
import factory.ProfessorRightFactory;
import factory.StudentFactory;
import utils.LessonType;
import utils.RightType;

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


    protected User createUser(User u) {
        return userRepository.save(u);
    }

    protected Enrollment createEnrollment(String profUsername, String courseCode, String groupCode) {
        Professor professor = userRepository.save(ProfessorFactory.generateProfessorBuilder()
                .username(profUsername)
                .build());
        Course course = courseRepository.save(CourseFactory.generateCourseBuilder()
                .coordinator(userRepository.save(ProfessorFactory.generateProfessor()))
                .code(courseCode)
                .build());
        Group group = groupRepository.save(GroupFactory.generateGroupBuilder()
                .code(groupCode)
                .build());
        Student student = userRepository.save(StudentFactory.generateStudentBuilder()
                .group(group)
                .build());
        Enrollment enrollment = enrollmentRepository.save(Enrollment.builder()
                .course(course)
                .student(student)
                .build());
        Lesson lesson = lessonRepository.save(LessonFactory.generateLessonBuilder()
                .enrollment(enrollment)
                .build());
        enrollment.setLessons(Arrays.asList(lesson));
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

}
