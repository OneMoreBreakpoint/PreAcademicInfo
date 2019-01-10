package bussiness_layer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class StudentDto extends UserDto implements Serializable {

    private static final long serialVersionUID = 2022L;

    private Integer registrationNr;

    @Size(max = 6)
    private String fathersInitials;

    private String profilePhoto;

    private boolean notifiedByEmail;

    private GroupDto group;

    @Builder
    public StudentDto(String username, String password, String firstName, String lastName, String email,
                      Integer registrationNr, String fathersInitials, String profilePhoto, boolean notifiedByEmail, GroupDto group, String role) {
        super(username, password, firstName, lastName, email, role);
        this.registrationNr = registrationNr;
        this.fathersInitials = fathersInitials;
        this.profilePhoto = profilePhoto;
        this.notifiedByEmail = notifiedByEmail;
        this.group = group;
    }
}
