package data_layer.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Professors")
public class Professor extends User {

    @Column(name = "web_page", length = 50)
    private String webPage;

    @Column(name = "path_to_profile_photo")
    private String pathToProfilePhoto;

    @OneToMany
    @JoinColumn(name = "professor_id")
    private List<Teaching> teachingList;

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getPathToProfilePhoto() {
        return pathToProfilePhoto;
    }

    public void setPathToProfilePhoto(String pathToProfilePhoto) {
        this.pathToProfilePhoto = pathToProfilePhoto;
    }

    public List<Teaching> getTeachingList() {
        return teachingList;
    }

    public void setTeachingList(List<Teaching> teachingList) {
        this.teachingList = teachingList;
    }
}
