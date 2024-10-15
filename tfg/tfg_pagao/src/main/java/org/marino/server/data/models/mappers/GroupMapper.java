package org.marino.server.data.models.mappers;

import org.marino.server.data.models.Group;
import org.marino.server.data.models.entities.GroupEntity;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

    public Group toGroup(GroupEntity group) {
        return new Group(group.getId(), group.getName(), group.getDescription());
    }

    public GroupEntity toGroupEntity(Group group) {
        return new GroupEntity(group.getId(), group.getName(), group.getDescription());
    }
}
