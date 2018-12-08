package data_layer.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;


@Entity(name = "Students")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "registrationNr"))
@Getter
@Setter
@NoArgsConstructor
public class Student extends User {

    private Integer registrationNr;

    @Size(max = 6)
    private String fathersInitials;

    @ManyToOne
    private Group group;

    private String pathToProfilePhoto;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

    private boolean notifiedByEmail;

    @Builder
    public Student(String username, String encryptedPassword, String firstName, String lastName, String email, Integer registrationNr,
                   String fathersInitials, Group group, String pathToProfilePhoto, List<Enrollment> enrollments, boolean notifiedByEmail) {
        super(username, encryptedPassword, firstName, lastName, email);
        this.registrationNr = registrationNr;
        this.fathersInitials = fathersInitials;
        this.group = group;
        this.pathToProfilePhoto = pathToProfilePhoto;
        this.enrollments = enrollments;
        this.notifiedByEmail = notifiedByEmail;
    }

}
