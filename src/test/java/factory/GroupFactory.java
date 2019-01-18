package factory;

import bussiness_layer.dto.GroupDto;
import data_layer.domain.Group;
import utils.TestConstants;

public class GroupFactory {
    public static Group.GroupBuilder generateGroupBuilder() {
        return Group.builder()
                .code(TestConstants.GROUP_CODE);
//                .students(Arrays.asList(StudentFactory.generateStudent()));
        //TODO(All) add rest of the fields when necessary.
    }

    public static Group generateGroup() {
        return generateGroupBuilder().build();
    }

    public static GroupDto.GroupDtoBuilder generateGroupDtoBuilder() {
        return GroupDto.builder()
                .id((short) 1)
                .code(TestConstants.GROUP_CODE);
    }

    public static GroupDto generateGroupDto() {
        return generateGroupDtoBuilder().build();
    }

}
