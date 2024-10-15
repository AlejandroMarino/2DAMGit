package org.marino.server.data.models.mappers;

import org.marino.server.data.models.Participation;
import org.marino.server.data.models.entities.MemberEntity;
import org.marino.server.data.models.entities.ParticipationEntity;
import org.marino.server.data.models.entities.ReceiptEntity;
import org.springframework.stereotype.Component;

@Component
public class ParticipationMapper {

    public Participation toParticipation(ParticipationEntity participation) {
        return new Participation(participation.getMember().getId(), participation.getReceipt().getId(),
                participation.getDescription(), participation.getPays(), participation.getDebts());
    }

    public ParticipationEntity toParticipationEntity(Participation participation, MemberEntity member, ReceiptEntity receipt) {
        return new ParticipationEntity(member, receipt, participation.getDescription(), participation.getPays(),
                participation.getDebts());
    }
}
