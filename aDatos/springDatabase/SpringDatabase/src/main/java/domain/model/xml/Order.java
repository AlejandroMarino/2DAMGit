package domain.model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    @XmlElement()
    private int id;
    @XmlElement()
    private Table table;
    @XmlElement()
    private Customer customer;
    @XmlElement(name = "order_date")
    private LocalDate orderDate;
    @XmlElement(name = "order_item")
    private List<OrderItem> orderItems;
}
