package org.marino.server.domain.services;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.marino.server.data.models.Group;
import org.marino.server.data.models.Member;
import org.marino.server.data.models.entities.GroupEntity;
import org.marino.server.data.models.entities.UserEntity;
import org.marino.server.data.models.mappers.GroupMapper;
import org.marino.server.data.models.mappers.MemberMapper;
import org.marino.server.data.models.repositories.GroupEntityRepository;
import org.marino.server.data.models.repositories.MemberEntityRepository;
import org.marino.server.data.models.repositories.UserEntityRepository;
import org.marino.server.domain.exceptions.BadRequestException;
import org.marino.server.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ServicesGroup {

    private final GroupEntityRepository groupR;
    private final MemberEntityRepository memberR;
    private final UserEntityRepository userR;

    private final GroupMapper groupMapper;
    private final MemberMapper memberMapper;

    public ServicesGroup(GroupEntityRepository groupR, MemberEntityRepository memberR, UserEntityRepository userR, GroupMapper groupMapper, MemberMapper memberMapper) {
        this.groupR = groupR;
        this.memberR = memberR;
        this.userR = userR;
        this.groupMapper = groupMapper;
        this.memberMapper = memberMapper;
    }

    public List<Group> getAll() {
        return groupR.findAll()
                .stream()
                .map(groupMapper::toGroup)
                .toList();
    }

    public List<Group> getAllOfUser(String email) {
        try {
            UserEntity user = userR.findByEmail(email);
            return groupR.getAllOfUser(user.getId())
                    .stream()
                    .map(groupMapper::toGroup)
                    .toList();
        } catch (Exception e) {
            throw new NotFoundException("User with email " + email + " not found");
        }
    }

    public Group get(int id) {
        return groupR.findById(id).map(groupMapper::toGroup)
                .orElseThrow(() -> new NotFoundException("Group with id " + id + " not found"));
    }

    @Transactional
    public Group add(Group group) {
        List<Member> members = group.getMembers();
        if (members.isEmpty()) {
            throw new BadRequestException("Impossible to create a group without members");
        } else {
            GroupEntity savedGroup = groupR.save(groupMapper.toGroupEntity(group));
            for (Member member : members) {
                UserEntity user;
                int userId = member.getUserId();
                if (userId < 1) {
                    user = null;
                } else {
                    user = userR.findById(userId).orElseThrow();
                }
                memberR.save(memberMapper.toMemberEntity(member, savedGroup, user));
            }
            return groupMapper.toGroup(savedGroup);
        }
    }
}
