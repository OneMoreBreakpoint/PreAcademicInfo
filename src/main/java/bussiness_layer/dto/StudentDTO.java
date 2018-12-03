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

    private GroupDTO group;


    public StudentDTO(Student entity){
        super(entity);
        this.registrationNr = entity.getRegistrationNr();
        this.fathersInitials = entity.getFathersInitials();
        this.pathToProfilePhoto = entity.getPathToProfilePhoto();
        this.notifiedByEmail = entity.isNotifiedByEmail();
        this.group = new GroupDTO(entity.getGroup());
    }

    public Student toEntity(){
        Student entity = new Student();
        entity.setUsername(this.getUsername());
        entity.setFirstName(this.getFirstName());
        entity.setLastName(this.getLastName());
        entity.setEmail(this.getEmail());
        entity.setFathersInitials(this.fathersInitials);
        entity.setPathToProfilePhoto(this.pathToProfilePhoto);
        entity.setNotifiedByEmail(this.notifiedByEmail);
        entity.setGroup(this.group.toEntity());
        return entity;
    }
}
