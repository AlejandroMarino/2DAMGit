package org.marino.server.data.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "participation")
@IdClass(ParticipationEntityId.class)
public class ParticipationEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberEntity member;

    @Id
    @ManyToOne
    @JoinColumn(name = "receipt_id", referencedColumnName = "id")
    private ReceiptEntity receipt;

    @Column(name = "description")
    private String description;

    @Column(name = "pays")
    private Double pays;

    @Column(name = "debts")
    private Double debts;
}
