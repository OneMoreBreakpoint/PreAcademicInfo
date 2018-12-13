package bussiness_layer.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

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
}
