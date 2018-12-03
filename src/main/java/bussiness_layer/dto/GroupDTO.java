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

    public GroupDTO(Short code){
        this.code = code;
    }

    public Group toEntity(){
        Group entity = new Group();
        entity.setId(this.id);
        entity.setCode(this.code);
        return entity;
    }

    @Override
    public int compareTo(GroupDTO o) {
        return this.code - o.code;
    }


    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GroupDTO)){
            return false;
        }
        GroupDTO that = (GroupDTO)obj;
        return this.code.equals(that.code);
    }

}
