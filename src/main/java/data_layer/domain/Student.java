package data_layer.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "Students")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "registrationNr"))
@Getter
@Setter
@NoArgsConstructor
public class Student extends User {

    private Integer registrationNr;

    @Size(max = 6)
    private String fathersInitials;

    private Short groupNr;

    private String pathToProfilePhoto;

    @OneToMany
    @JoinColumn(name = "student_id")
    private List<Enrollment> enrollments;

    private boolean notifiedByEmail;

    @Builder
    public Student(String username, String encryptedPassword, String firstName, String lastName, String email, Integer registrationNr,
                   String fathersInitials, Short groupNr, String pathToProfilePhoto, List<Enrollment> enrollments, boolean notifiedByEmail) {
        super(username, encryptedPassword, firstName, lastName, email);
        this.registrationNr = registrationNr;
        this.fathersInitials = fathersInitials;
        this.groupNr = groupNr;
        this.pathToProfilePhoto = pathToProfilePhoto;
        this.enrollments = enrollments;
        this.notifiedByEmail = notifiedByEmail;
    }

}
