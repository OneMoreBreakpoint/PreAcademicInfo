package data_layer.domain;

import bussiness_layer.dto.ProfessorDto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "Professors")
@Data
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
        if (!(o instanceof ProfessorDto)) {
            return false;
        }
        ProfessorDto that = (ProfessorDto) o;
        return this.getUsername().equals(that.getUsername());
    }
}
