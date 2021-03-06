package bussiness_layer.dto;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto implements Comparable<GroupDto>, Serializable {

    public static final long serialVersionUID = 2026L;

    private Short id;
    private String code;

    public GroupDto(String code) {
        this.code = code;
    }

    @Override
    public int compareTo(GroupDto o) {
        return this.code.compareTo(o.code);
    }


    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GroupDto)) {
            return false;
        }
        GroupDto that = (GroupDto) obj;
        //TODO(all) maybe use groupname instead of code
        return this.code.equals(that.code);
    }

}
