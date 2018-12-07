package bussiness_layer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ProfessorDto extends UserDto {
    @Size(max = 50)
    private String webPage;
    private String pathToProfilePhoto;
}
