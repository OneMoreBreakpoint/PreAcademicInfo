package data_layer.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Students")
public class Student extends User implements Comparable<Student>{

    @Column(name = "registration_nr", unique = true, nullable = false)
    private Integer registrationNr;

    @Column(name = "fathers_initials", length = 6, columnDefinition = "NVARCHAR(6)", nullable = false)
    private String fathersInitials;

    @Column(name = "group_nr")
    private Short groupNr;

    @Column(name = "path_to_profile_photo")
    private String pathToProfilePhoto;

    @OneToMany
    @JoinColumn(name = "student_id")
    private List<Enrollment> enrollments;

    @Column(name = "notified_by_email", nullable = false)
    private boolean notifiedByEmail;


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

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Short getGroupNr() {
        return groupNr;
    }

    public void setGroupNr(Short groupNr) {
        this.groupNr = groupNr;
    }

    public String getFathersInitials() {
        return fathersInitials;
    }

    public void setFathersInitials(String fathersInitials) {
        this.fathersInitials = fathersInitials;
    }

    public boolean isNotifiedByEmail() {
        return notifiedByEmail;
    }

    public void setNotifiedByEmail(boolean notifiedByEmail) {
        this.notifiedByEmail = notifiedByEmail;
    }

    @Override
    public int compareTo(Student o) {
        String thisfullName = this.getLastName() + " " + this.getFirstName();
        String thatFullName = o.getLastName() + " " + o.getFirstName();
        return thisfullName.compareTo(thatFullName);
    }
}
