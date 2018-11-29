package data_layer.domain;

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
public class Student extends User implements Comparable<Student> {

    private Integer registrationNr;

    @Size(max = 6)
    private String fathersInitials;

    @ManyToOne
    private Group group;

    private String pathToProfilePhoto;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

    private boolean notifiedByEmail;

    @Override
    public int compareTo(Student o) {
        String thisfullName = this.getLastName() + " " + this.getFirstName();
        String thatFullName = o.getLastName() + " " + o.getFirstName();
        return thisfullName.compareTo(thatFullName);
    }

}
