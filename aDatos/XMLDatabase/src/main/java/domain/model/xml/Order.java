package domain.model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    @XmlElement(name = "order_id")
    private int id;
    @XmlElement(name = "order_date")
    private LocalDate date;
    @XmlElement
    private double total;
    @XmlElement
    private Items items;
}
