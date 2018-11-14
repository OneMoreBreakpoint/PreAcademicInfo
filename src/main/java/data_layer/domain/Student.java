package data_layer.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User{

    @Column(name = "registration_nr", unique = true, nullable = false)
    private int registrationNr;

    @Column(name = "fathers_capital_letters", length = 6, nullable = false)
    private String fathersCapitalLetters;

    @Column(name = "group_nr")
    private Short groupNr;

    @Column(name = "path_to_profile_photo")
    private String pathToProfilePhoto;

    @OneToMany
    @JoinColumn(name = "student_id", nullable = false)
    private Set<Enrollment> enrollments;


    public int getRegistrationNr() {
        return registrationNr;
    }

    public void setRegistrationNr(int registrationNr) {
        this.registrationNr = registrationNr;
    }

    public String getPathToProfilePhoto() {
        return pathToProfilePhoto;
    }

    public void setPathToProfilePhoto(String pathToProfilePhoto) {
        this.pathToProfilePhoto = pathToProfilePhoto;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Short getGroupNr() {
        return groupNr;
    }

    public void setGroupNr(Short groupNr) {
        this.groupNr = groupNr;
    }

    public String getFathersCapitalLetters() {
        return fathersCapitalLetters;
    }

    public void setFathersCapitalLetters(String fathersCapitalLetters) {
        this.fathersCapitalLetters = fathersCapitalLetters;
    }
}
