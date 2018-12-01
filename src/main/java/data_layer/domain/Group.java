package data_layer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Groups")
@Getter
@Setter
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(unique = true)
    private Short code;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    public Group(Short code){
        this.code = code;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Group)){
            return false;
        }
        Group that = (Group)obj;
        return this.code.equals(that.code);
    }
}
