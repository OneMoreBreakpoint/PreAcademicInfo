package data_layer.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "Groups")
@Data
public class Group {
    @Id
    private Short code;

    @OneToMany(mappedBy = "group")
    private List<Student> students;
}
