package bussiness_layer.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static utils.Constants.UBB_EMAIL_FORMAT;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Size(min = 4, max = 16)
    @NotNull
    private String username;

    @Size(min = 6)
    @NotNull
    private String password;

    @Size(max = 40)
    //TODO(All) this should also be not null? same for last name and email
    private String firstName;

    @Size(max = 40)
    private String lastName;

    @Email(regexp = UBB_EMAIL_FORMAT)
    private String email;

}
