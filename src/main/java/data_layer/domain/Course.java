package data_layer.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "Courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", length = 7, nullable = false)
    private String code;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String name;

    @Max(14) @Min(0)
    @Column(name = "nr_of_seminars", nullable = false)
    private Byte nrOfSeminars;

    @Max(14) @Min(7)
    @Column(name = "nr_of_laboratories", nullable = false)
    private Byte nrOfLaboratories;

    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    private Professor coordinator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getNrOfSeminars() {
        return nrOfSeminars;
    }

    public void setNrOfSeminars(Byte nrOfSeminars) {
        this.nrOfSeminars = nrOfSeminars;
    }

    public Byte getNrOfLaboratories() {
        return nrOfLaboratories;
    }

    public void setNrOfLaboratories(Byte nrOfLaboratories) {
        this.nrOfLaboratories = nrOfLaboratories;
    }

    public Professor getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Professor coordinator) {
        this.coordinator = coordinator;
    }
}
