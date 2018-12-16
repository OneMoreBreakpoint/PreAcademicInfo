package bussiness_layer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static utils.Constants.UBB_EMAIL_FORMAT;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

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

    private String userRole;

//    private String newPassword;


    public UserDto(String username, String password, String firstName, String lastName, String email, String userRole) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRole = userRole;
    }
}