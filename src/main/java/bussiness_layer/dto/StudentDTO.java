package bussiness_layer.dto;

import data_layer.domain.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class StudentDTO extends UserDTO {

    private Integer registrationNr;

    @Size(max = 6)
    private String fathersInitials;

    private String pathToProfilePhoto;

    private boolean notifiedByEmail;


    public StudentDTO(Student entity){
        super(entity);
        this.registrationNr = entity.getRegistrationNr();
        this.fathersInitials = entity.getFathersInitials();
        this.pathToProfilePhoto = entity.getPathToProfilePhoto();
        this.notifiedByEmail = entity.isNotifiedByEmail();
    }
}
