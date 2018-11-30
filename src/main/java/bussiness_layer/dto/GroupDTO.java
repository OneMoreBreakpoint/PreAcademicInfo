package bussiness_layer.dto;

import data_layer.domain.Group;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GroupDTO implements Comparable<GroupDTO>{
    private Short id;
    private Short code;

    public GroupDTO(Group entity){
        this.id = entity.getId();
        this.code = entity.getCode();
    }

    @Override
    public int compareTo(GroupDTO o) {
        return this.code - o.code;
    }
}
