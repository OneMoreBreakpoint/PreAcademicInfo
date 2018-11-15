package data_layer.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Column(name = "username", length = 16, unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String encryptedPassword;

    @Column(name = "firstname", length = 40, columnDefinition = "NVARCHAR(40)", nullable = false)
    private String firstName;

    @Column(name = "lastname", length = 40, columnDefinition = "NVARCHAR(40)", nullable = false)
    private String lastName;

    @Column(name = "email", length = 60, nullable = false)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
