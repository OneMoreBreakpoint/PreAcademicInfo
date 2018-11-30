package data_layer.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Groups")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(unique = true)
    private Short code;

    @OneToMany(mappedBy = "group")
    private List<Student> students;
}
