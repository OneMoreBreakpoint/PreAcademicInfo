package data_layer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "Professors")
@Getter
@Setter
public class Professor extends User {

    @Size(max = 50)
    private String webPage;

    private String pathToProfilePhoto;

    @OneToMany
    @JoinColumn(name = "professor_id")
    private List<Teaching> teachingList;

}
