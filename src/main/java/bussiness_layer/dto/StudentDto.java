package bussiness_layer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class StudentDto extends UserDto {

    private Integer registrationNr;

    @Size(max = 6)
    private String fathersInitials;

    private String pathToProfilePhoto;

    private boolean notifiedByEmail;

    private GroupDto group;

}
