package business_layer;

import bd_config.H2TestConfiguration;
import data_layer.domain.*;
import data_layer.repositories.*;
import factory.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

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
    protected ITeachingRepository teachingRepository;

    @Autowired
    protected IEnrollmentRepository enrollmentRepository;

    @Autowired
    protected ILessonRepository lessonRepository;

    @Autowired
    private IPartialExamRepository partialExamRepository;


    protected User createUser(User u) {
        return userRepository.save(u);
    }

    protected Enrollment createTeachingAndEnrollment(String profUsername, String courseCode, String groupCode) {
        Professor professor = userRepository.save(ProfessorFactory.generateProfessorBuilder().username(profUsername).build());
        Course course = courseRepository.save(CourseFactory.generateCourseBuilder().coordinator(professor).code(courseCode).build());
        Group group = groupRepository.save(GroupFactory.generateGroupBuilder().code(groupCode).build());
        Student student = userRepository.save(StudentFactory.generateStudentBuilder().group(group).build());
        Teaching teaching = teachingRepository.save(TeachingFactory.generateTeachingBuilder()
                .professor(professor)
                .laboratoryGroups(new HashSet<>(Arrays.asList(group)))
                .course(course)
                .build());
        Enrollment enrollment = enrollmentRepository.save(Enrollment.builder()
                .course(course)
                .student(student)
                .lessons(Arrays.asList(lessonRepository.save(LessonFactory.generateLesson())))
                .partialExams(Arrays.asList(partialExamRepository.save(PartialExamFactory.generatePartialExam())))
                .build());
        return enrollment;
    }

}
