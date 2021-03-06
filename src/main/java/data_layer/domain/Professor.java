package data_layer.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "professors")
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
