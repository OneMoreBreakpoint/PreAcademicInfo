package data_layer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

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


