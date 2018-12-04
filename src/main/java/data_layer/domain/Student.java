package data_layer.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "Students")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "registrationNr"))
@Getter
@Setter
@NoArgsConstructor
public class Student extends User implements Comparable<Student> {

    private Integer registrationNr;

    @Size(max = 6)
    private String fathersInitials;

    private Short groupNr;

    private String pathToProfilePhoto;

    @OneToMany
    @JoinColumn(name = "student_id")
    private List<Enrollment> enrollments;

    private boolean notifiedByEmail;

    @Override
    public int compareTo(Student o) {
        String thisfullName = this.getLastName() + " " + this.getFirstName();
        String thatFullName = o.getLastName() + " " + o.getFirstName();
        return thisfullName.compareTo(thatFullName);
    }

}
