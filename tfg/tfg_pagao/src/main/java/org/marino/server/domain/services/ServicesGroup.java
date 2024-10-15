package org.marino.server.domain.services;

import lombok.extern.log4j.Log4j2;
import org.marino.server.data.models.Group;
import org.marino.server.data.models.mappers.GroupMapper;
import org.marino.server.data.models.repositories.GroupEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ServicesGroup {

    private final GroupEntityRepository groupR;

    private final GroupMapper groupMapper;

    public ServicesGroup(GroupEntityRepository groupR, GroupMapper groupMapper) {
        this.groupR = groupR;
        this.groupMapper = groupMapper;
    }

    public List<Group> getAll() {
        return groupR.findAll()
                .stream()
                .map(groupMapper::toGroup)
                .toList();
    }

    public Group get(int id) {
        return groupR.findById(id).map(groupMapper::toGroup).orElse(null);
    }

    public Group add(Group group) {
        return groupMapper.toGroup(groupR.save(groupMapper.toGroupEntity(group)));
    }
}
