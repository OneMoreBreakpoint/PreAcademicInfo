package bussiness_layer.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentDto extends UserDto implements Serializable {

    private static final long serialVersionUID = 2022L;

    private Integer registrationNr;

    @Size(max = 6)
    private String fathersInitials;

    private String pathToProfilePhoto;

    private boolean notifiedByEmail;

    private GroupDto group;

    @Builder
    public StudentDto(String username, String password, String firstName, String lastName, String email,
                      Integer registrationNr, String fathersInitials, String pathToProfilePhoto, boolean notifiedByEmail, GroupDto group) {
        super(username, password, firstName, lastName, email);
        this.registrationNr = registrationNr;
        this.fathersInitials = fathersInitials;
        this.pathToProfilePhoto = pathToProfilePhoto;
        this.notifiedByEmail = notifiedByEmail;
        this.group = group;
    }
}
