package data_layer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Courses")
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 7)
    private String code;

    @NotNull
    private String name;

    @NotNull
    @Max(14)
    @Min(0)
    private Byte nrOfSeminars;

    @NotNull
    @Max(14)
    @Min(7)
    private Byte nrOfLaboratories;

    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    private Professor coordinator;

}
