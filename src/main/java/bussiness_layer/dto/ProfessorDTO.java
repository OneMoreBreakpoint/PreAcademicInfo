package bussiness_layer.dto;

import data_layer.domain.Professor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Data
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
