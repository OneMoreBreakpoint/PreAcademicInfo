package bussiness_layer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static utils.Constants.UBB_EMAIL_FORMAT;

@Data
@NoArgsConstructor
public abstract class UserDto {

    @Size(min = 4, max = 16)
    @NotNull
    private String username;

    @Size(max = 40)
    @NotNull
    private String firstName;

    @Size(max = 40)
    @NotNull
    private String lastName;

    @Email(regexp = UBB_EMAIL_FORMAT)
    @NotNull
    private String email;

}
