package data_layer.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Professors")
@Data
@NoArgsConstructor
public class Professor extends User {

    @Size(max = 50)
    private String webPage;

    @Lob
    private String profilePhoto;

    @OneToMany(mappedBy = "professor")
    private List<ProfessorRight> rights;

    @Builder
    public Professor(String username, String encryptedPassword, String firstName, String lastName, String email,
                     String webPage, String profilePhoto, List<ProfessorRight> rights) {
        super(username, encryptedPassword, firstName, lastName, email);
        this.webPage = webPage;
        this.profilePhoto = profilePhoto;
        this.rights = rights;
    }
}
