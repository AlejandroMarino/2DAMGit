package org.marino.server.domain.services;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.marino.server.data.models.Member;
import org.marino.server.data.models.User;
import org.marino.server.data.models.mappers.GroupMapper;
import org.marino.server.data.models.mappers.MemberMapper;
import org.marino.server.data.models.mappers.UserMapper;
import org.marino.server.data.models.repositories.GroupEntityRepository;
import org.marino.server.data.models.repositories.MemberEntityRepository;
import org.marino.server.domain.exceptions.BadRequestException;
import org.marino.server.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ServicesMember {

    private final MemberEntityRepository memberR;

    private final MemberMapper memberMapper;

    private final GroupMapper groupMapper;
    private final UserMapper userMapper;

    private final ServicesGroup sGroup;
    private final ServicesUser sUser;
    private final GroupEntityRepository groupR;

    public ServicesMember(MemberEntityRepository memberR, MemberMapper memberMapper, GroupMapper groupMapper, UserMapper userMapper, ServicesGroup sGroup, ServicesUser sUser, GroupEntityRepository groupR) {
        this.memberR = memberR;
        this.memberMapper = memberMapper;
        this.groupMapper = groupMapper;
        this.userMapper = userMapper;
        this.sGroup = sGroup;
        this.sUser = sUser;
        this.groupR = groupR;
    }

    public List<Member> getAllOfGroup(int groupId) {
        if (!groupR.existsById(groupId)) {
            throw new NotFoundException("Group with id " + groupId + " not found");
        }
        return memberR.getAllOfGroup(groupId)
                .stream()
                .map(memberMapper::toMember)
                .toList();
    }

    public List<Member> getAllAvailableOfGroup(int groupId) {
        if (!groupR.existsById(groupId)) {
            throw new NotFoundException("Group with id " + groupId + " not found");
        }
        return memberR.getAllAvailableOfGroup(groupId)
                .stream()
                .map(memberMapper::toMember)
                .toList();
    }

    public Member get(int id) {
        return memberR.findById(id).map(memberMapper::toMember)
                .orElseThrow(() -> new NotFoundException("Member with id " + id + " not found"));
    }

    public double getBalanceOfMember(int id) {
        if (!memberR.existsById(id)) {
            throw new NotFoundException("Member with id " + id + " not found");
        }
        return memberR.getBalanceOfMember(id);
    }

    public Member add(Member member) {
        return memberMapper.toMember(memberR.save(memberMapper
                .toMemberEntity(
                        member,
                        groupMapper.toGroupEntity(sGroup.get(member.getGroupId())),
                        userMapper.toUserEntity(sUser.get(member.getUserId()))
                )
        ));
    }

    @Transactional
    public void setUserToMember(int memberId, String userEmail) {
        Member member = get(memberId);
        if (memberR.userAlreadyInGroup(userEmail, member.getGroupId())) {
            throw new BadRequestException("User can only be assigned to one member per group");
        } else {
            User user = sUser.findByEmail(userEmail);
            int updatedRows = memberR.setUser(memberId, userMapper.toUserEntity(user));
            if (updatedRows <= 0) {
                throw new BadRequestException("User couldn't be set");
            }
        }
    }

}
