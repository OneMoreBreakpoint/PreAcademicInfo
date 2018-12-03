package data_layer.domain;

import bussiness_layer.dto.ProfessorDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
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

    @OneToMany(mappedBy = "professor")
    private List<Teaching> teachingList;


    @Override
    public int hashCode() {
        return super.getUsername().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof ProfessorDTO)){
            return false;
        }
        ProfessorDTO that = (ProfessorDTO)o;
        return this.getUsername().equals(that.getUsername());
    }
}
