package data_layer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static utils.Constants.UBB_EMAIL_FORMAT;

@Getter
@Setter
@Entity(name = "Users")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @NotNull
    @Size(min = 4, max = 16)
    private String username;

    @NotNull
    @Size(min = 6)
    private String encryptedPassword;

    @NotNull
    @Size(max = 40)
    private String firstName;

    @NotNull
    @Size(max = 40)
    private String lastName;

    @NotNull
    private String email;

}


