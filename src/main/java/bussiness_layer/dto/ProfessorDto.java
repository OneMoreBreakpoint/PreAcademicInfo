package bussiness_layer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ProfessorDto extends UserDto {
    @Size(max = 50)
    private String webPage;
    private String pathToProfilePhoto;

    public ProfessorDto(String username, String password, String firstName, String lastName, String email,
                        String userRole, String webPage, String pathToProfilePhoto) {
        super(username, password, firstName, lastName, email, userRole);
        this.webPage = webPage;
        this.pathToProfilePhoto = pathToProfilePhoto;
    }
}
