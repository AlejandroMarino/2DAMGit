package org.marino.server.data.models.mappers;

import org.marino.server.data.models.Receipt;
import org.marino.server.data.models.entities.GroupEntity;
import org.marino.server.data.models.entities.ReceiptEntity;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapper {

    public Receipt toReceipt(ReceiptEntity receipt) {
        return new Receipt(receipt.getId(), receipt.getName(), receipt.getDescription(), receipt.getGroup().getId());
    }

    public ReceiptEntity toReceiptEntity(Receipt receipt, GroupEntity group) {
        return new ReceiptEntity(receipt.getId(), receipt.getName(), receipt.getDescription(), group);
    }
}
