package data_layer.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "Professors")
@Data
@NoArgsConstructor
public class Professor extends User {

    @Size(max = 50)
    private String webPage;

    private String pathToProfilePhoto;

    @OneToMany(mappedBy = "professor")
    private List<Teaching> teachingList;

    @Builder
    public Professor(String username, String encryptedPassword, String firstName, String lastName, String email,
                     String webPage, String pathToProfilePhoto, List<Teaching> teachingList) {
        super(username, encryptedPassword, firstName, lastName, email);
        this.webPage = webPage;
        this.pathToProfilePhoto = pathToProfilePhoto;
        this.teachingList = teachingList;
    }
}
