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
public class ProfessorDto extends UserDto implements Serializable {

    private static final long serialVersionUID = 2021L;

    @Size(max = 50)
    private String webPage;
    private String pathToProfilePhoto;

    @Builder
    public ProfessorDto(String username, String password, String firstName, String lastName, String email,
                        String userRole, String webPage, String pathToProfilePhoto) {
        super(username, password, firstName, lastName, email, userRole);
        this.webPage = webPage;
        this.pathToProfilePhoto = pathToProfilePhoto;
    }
}
