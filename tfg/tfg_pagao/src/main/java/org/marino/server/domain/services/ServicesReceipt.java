package org.marino.server.domain.services;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.marino.server.data.models.Participation;
import org.marino.server.data.models.Receipt;
import org.marino.server.data.models.entities.ReceiptEntity;
import org.marino.server.data.models.mappers.ParticipationMapper;
import org.marino.server.data.models.mappers.ReceiptMapper;
import org.marino.server.data.models.repositories.GroupEntityRepository;
import org.marino.server.data.models.repositories.MemberEntityRepository;
import org.marino.server.data.models.repositories.ParticipationEntityRepository;
import org.marino.server.data.models.repositories.ReceiptEntityRepository;
import org.marino.server.domain.exceptions.BadRequestException;
import org.marino.server.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ServicesReceipt {

    private final ReceiptEntityRepository receiptR;

    private final ReceiptMapper receiptMapper;

    private final ParticipationMapper participationMapper;

    private final GroupEntityRepository groupR;

    private final ParticipationEntityRepository participationR;

    private final MemberEntityRepository memberR;

    public ServicesReceipt(ReceiptEntityRepository receiptR, ReceiptMapper receiptMapper, ParticipationMapper participationMapper, GroupEntityRepository groupR, ParticipationEntityRepository participationR, MemberEntityRepository memberR) {
        this.receiptR = receiptR;
        this.receiptMapper = receiptMapper;
        this.participationMapper = participationMapper;
        this.groupR = groupR;
        this.participationR = participationR;
        this.memberR = memberR;
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
                ReceiptEntity savedReceipt = receiptR.save(receiptMapper.toReceiptEntity(receipt, groupR.getReferenceById(groupId)));
                for (Participation participation : participations) {
                    participationR.save(participationMapper
                            .toParticipationEntity(participation,
                                    memberR.getReferenceById(participation.getMemberId()),
                                    savedReceipt
                            )
                    );
                }
                return receiptMapper.toReceipt(savedReceipt);
            }
        } else {
            throw new BadRequestException("Impossible to add a receipt of a non existing group");
        }
    }
}
