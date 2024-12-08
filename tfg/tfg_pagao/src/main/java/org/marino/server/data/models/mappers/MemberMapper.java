package org.marino.server.data.models.mappers;

import org.marino.server.data.models.Member;
import org.marino.server.data.models.entities.GroupEntity;
import org.marino.server.data.models.entities.MemberEntity;
import org.marino.server.data.models.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toMember(MemberEntity member) {
        Integer userId = null;
        if (member.getUser() != null) {
            userId = member.getUser().getId();
        }
        return new Member(member.getId(), member.getName(), member.getGroup().getId(), userId);
    }

    public MemberEntity toMemberEntity(Member member, GroupEntity group, UserEntity user) {
        return new MemberEntity(member.getId(), member.getName(), group, user);
    }
}
