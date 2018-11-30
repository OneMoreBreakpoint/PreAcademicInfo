package bussiness_layer.dto;

import data_layer.domain.Professor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
