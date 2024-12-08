package org.marino.server.domain.services;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.marino.server.data.models.Member;
import org.marino.server.data.models.Participation;
import org.marino.server.data.models.Receipt;
import org.marino.server.data.models.entities.ReceiptEntity;
import org.marino.server.data.models.entities.UserEntity;
import org.marino.server.data.models.mappers.MemberMapper;
import org.marino.server.data.models.mappers.ParticipationMapper;
import org.marino.server.data.models.mappers.ReceiptMapper;
import org.marino.server.data.models.repositories.*;
import org.marino.server.domain.exceptions.BadRequestException;
import org.marino.server.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ServicesReceipt {

    private final ReceiptEntityRepository receiptR;

    private final ReceiptMapper receiptMapper;

    private final MemberMapper memberMapper;

    private final ParticipationMapper participationMapper;

    private final GroupEntityRepository groupR;

    private final ParticipationEntityRepository participationR;

    private final MemberEntityRepository memberR;

    private final UserEntityRepository userR;

    public ServicesReceipt(ReceiptEntityRepository receiptR, ReceiptMapper receiptMapper, MemberMapper memberMapper, ParticipationMapper participationMapper, GroupEntityRepository groupR, ParticipationEntityRepository participationR, MemberEntityRepository memberR, UserEntityRepository userR) {
        this.receiptR = receiptR;
        this.receiptMapper = receiptMapper;
        this.memberMapper = memberMapper;
        this.participationMapper = participationMapper;
        this.groupR = groupR;
        this.participationR = participationR;
        this.memberR = memberR;
        this.userR = userR;
    }

    public List<Receipt> getAllOfGroup(int groupId) {
        if (!groupR.existsById(groupId)) {
            throw new NotFoundException("Group with id " + groupId + " not found");
        }
        return receiptR.getAllOfGroup(groupId)
                .stream()
                .map(receiptMapper::toReceipt)
                .toList();
    }

    public Receipt get(int id) {
        return receiptR.findById(id).map(receiptMapper::toReceipt)
                .orElseThrow(() -> new NotFoundException("Receipt with id " + id + " not found"));
    }

    @Transactional
    public Receipt add(Receipt receipt) {
        int groupId = receipt.getGroupId();
        if (groupR.existsById(groupId)) {
            List<Participation> participations = receipt.getParticipations();
            if (participations.isEmpty()) {
                throw new BadRequestException("Impossible to add a receipt without participations");
            } else {
                Set<Integer> groupMembersIds = memberR.getAllOfGroup(receipt.getGroupId())
                        .stream()
                        .map(memberMapper::toMember)
                        .toList().stream().map(Member::getId).collect(Collectors.toSet());
                for (Participation participation : participations) {
                    if (!groupMembersIds.contains(participation.getMemberId())) {
                        throw new BadRequestException("The receipt and the members of the participations must belong to the same group");
                    }
                }
                double totalPays = participations.stream().mapToDouble(Participation::getPays).sum();
                double totalDebts = participations.stream().mapToDouble(Participation::getDebts).sum();
                if (totalPays - totalDebts != 0) {
                    throw new BadRequestException("Receipt must have the same amount in pays than in debts");
                } else {
                    ReceiptEntity savedReceipt = receiptR.save(receiptMapper.toReceiptEntity(receipt, groupR.getReferenceById(groupId)));
                    for (Participation participation : participations) {
                        Member member = memberMapper.toMember(memberR.findById(participation.getMemberId()).orElseThrow());
                        participationR.save(participationMapper
                                .toParticipationEntity(
                                        participation,
                                        memberMapper.toMemberEntity(
                                                member,
                                                groupR.findById(member.getGroupId()).orElseThrow(),
                                                getUser(member.getUserId())
                                        ),
                                        savedReceipt
                                )
                        );
                    }
                    return receiptMapper.toReceipt(savedReceipt);
                }
            }
        } else {
            throw new BadRequestException("Impossible to add a receipt of a non existing group");
        }
    }

    private UserEntity getUser(int id) {
        if (id < 1) {
            return null;
        } else {
            return userR.findById(id).orElseThrow(() -> new NotFoundException("Invalid user"));
        }
    }

    public double getTotalPaidOfReceipt(int id) {
        if (!receiptR.existsById(id)) {
            throw new NotFoundException("Receipt with id " + id + " not found");
        }
        return receiptR.getTotalPaidOfReceipt(id);
    }
}
