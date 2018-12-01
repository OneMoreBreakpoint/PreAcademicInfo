package bussiness_layer.dto;

import data_layer.domain.Professor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ProfessorDTO extends UserDTO{
    @Size(max = 50)
    private String webPage;
    private String pathToProfilePhoto;

    public ProfessorDTO(Professor entity){
        super(entity);
        this.webPage = entity.getWebPage();
        this.pathToProfilePhoto = entity.getPathToProfilePhoto();
    }

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

    public Professor toEntity(){
        Professor entity = new Professor();
        entity.setUsername(this.getUsername());
        entity.setFirstName(this.getFirstName());
        entity.setLastName(this.getLastName());
        entity.setEmail(this.getEmail());
        entity.setWebPage(this.webPage);
        entity.setPathToProfilePhoto(this.pathToProfilePhoto);
        return entity;
    }
}
