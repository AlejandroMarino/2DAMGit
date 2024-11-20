package org.marino.server.data.models.mappers;

import org.marino.server.data.models.Member;
import org.marino.server.data.models.entities.GroupEntity;
import org.marino.server.data.models.entities.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toMember(MemberEntity member) {
        return new Member(member.getId(), member.getName(), member.getGroup().getId());
    }

    public MemberEntity toMemberEntity(Member member, GroupEntity group) {
        return new MemberEntity(member.getId(), member.getName(), group);
    }
}
