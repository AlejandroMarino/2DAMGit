package org.marino.server.domain.services;

import lombok.extern.log4j.Log4j2;
import org.marino.server.data.models.Receipt;
import org.marino.server.data.models.mappers.GroupMapper;
import org.marino.server.data.models.mappers.ReceiptMapper;
import org.marino.server.data.models.repositories.ReceiptEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ServicesReceipt {

    private final ReceiptEntityRepository receiptR;

    private final ReceiptMapper receiptMapper;

    private final GroupMapper groupMapper;

    private final ServicesGroup sGroup;

    public ServicesReceipt(ReceiptEntityRepository receiptR, ReceiptMapper receiptMapper, GroupMapper groupMapper, ServicesGroup sGroup) {
        this.receiptR = receiptR;
        this.receiptMapper = receiptMapper;
        this.groupMapper = groupMapper;
        this.sGroup = sGroup;
    }

    public List<Receipt> getAllOfGroup(int groupId) {
        return receiptR.getAllOfGroup(groupId)
                .stream()
                .map(receiptMapper::toReceipt)
                .toList();
    }

    public Receipt get(int id) {
        return receiptR.findById(id).map(receiptMapper::toReceipt).orElse(null);
    }

    public Receipt add(Receipt receipt) {
        return receiptMapper.toReceipt(receiptR.save(receiptMapper
                .toReceiptEntity(receipt, groupMapper.toGroupEntity(sGroup.get(receipt.getGroupId())))));
    }
}
