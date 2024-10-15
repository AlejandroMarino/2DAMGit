package org.marino.server.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class ParticipationEntityId implements Serializable {

    private Integer member;
    private Integer receipt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationEntityId that = (ParticipationEntityId) o;
        return Objects.equals(member, that.member) && Objects.equals(receipt, that.receipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, receipt);
    }
}
