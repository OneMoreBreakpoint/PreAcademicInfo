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
}
