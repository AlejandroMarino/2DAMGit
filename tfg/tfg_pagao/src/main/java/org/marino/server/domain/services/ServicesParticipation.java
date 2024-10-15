package org.marino.server.domain.services;

import lombok.extern.log4j.Log4j2;
import org.marino.server.data.models.Member;
import org.marino.server.data.models.Participation;
import org.marino.server.data.models.Receipt;
import org.marino.server.data.models.entities.ParticipationEntityId;
import org.marino.server.data.models.mappers.GroupMapper;
import org.marino.server.data.models.mappers.MemberMapper;
import org.marino.server.data.models.mappers.ParticipationMapper;
import org.marino.server.data.models.mappers.ReceiptMapper;
import org.marino.server.data.models.repositories.ParticipationEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ServicesParticipation {

    private final ParticipationEntityRepository participationR;

    private final ParticipationMapper participationMapper;

    private final MemberMapper memberMapper;

    private final ServicesMember sMember;

    private final GroupMapper groupMapper;

    private final ServicesGroup sGroup;

    private final ReceiptMapper receiptMapper;

    private final ServicesReceipt sReceipt;

    public ServicesParticipation(ParticipationEntityRepository participationR, ParticipationMapper participationMapper, MemberMapper memberMapper, ServicesMember sMember, GroupMapper groupMapper, ServicesGroup sGroup, ReceiptMapper receiptMapper, ServicesReceipt sReceipt) {
        this.participationR = participationR;
        this.participationMapper = participationMapper;
        this.memberMapper = memberMapper;
        this.sMember = sMember;
        this.groupMapper = groupMapper;
        this.sGroup = sGroup;
        this.receiptMapper = receiptMapper;
        this.sReceipt = sReceipt;
    }

    public List<Participation> getAllOfReceipt(int receiptId) {
        return participationR.getAllOfReceipt(receiptId)
                .stream()
                .map(participationMapper::toParticipation)
                .toList();
    }

    public Participation get(int memberId, int receiptId) {
        return participationR.findById(new ParticipationEntityId(memberId, receiptId))
                .map(participationMapper::toParticipation).orElse(null);
    }

    public Participation add(Participation participation) {
        Member member = sMember.get(participation.getMemberId());
        Receipt receipt = sReceipt.get(participation.getReceiptId());
        if (member.getGroupId() == receipt.getGroupId()) {
            return participationMapper.toParticipation(participationR.save(participationMapper
                    .toParticipationEntity(
                            participation,
                            memberMapper.toMemberEntity(member, groupMapper.toGroupEntity(sGroup.get(member.getGroupId()))),
                            receiptMapper.toReceiptEntity(receipt, groupMapper.toGroupEntity(sGroup.get(receipt.getGroupId())))
                    )
            ));
        } else {
            return null;
        }
    }
}
