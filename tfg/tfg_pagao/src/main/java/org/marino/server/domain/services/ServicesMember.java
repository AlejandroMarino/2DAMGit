package org.marino.server.domain.services;

import lombok.extern.log4j.Log4j2;
import org.marino.server.data.models.Member;
import org.marino.server.data.models.mappers.GroupMapper;
import org.marino.server.data.models.mappers.MemberMapper;
import org.marino.server.data.models.repositories.MemberEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ServicesMember {

    private final MemberEntityRepository memberR;

    private final MemberMapper memberMapper;

    private final GroupMapper groupMapper;

    private final ServicesGroup sGroup;

    public ServicesMember(MemberEntityRepository memberR, MemberMapper memberMapper, GroupMapper groupMapper, ServicesGroup sGroup) {
        this.memberR = memberR;
        this.memberMapper = memberMapper;
        this.groupMapper = groupMapper;
        this.sGroup = sGroup;
    }

    public List<Member> getAllOfGroup(int groupId) {
        return memberR.getAllOfGroup(groupId)
                .stream()
                .map(memberMapper::toMember)
                .toList();
    }

    public Member get(int id) {
        return memberR.findById(id).map(memberMapper::toMember).orElse(null);
    }

    public Member add(Member member) {
        return memberMapper.toMember(memberR.save(memberMapper
                .toMemberEntity(member, groupMapper.toGroupEntity(sGroup.get(member.getGroupId())))));
    }
}
