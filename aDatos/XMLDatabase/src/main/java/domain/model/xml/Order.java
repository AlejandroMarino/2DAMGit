package domain.model.xml;

import config.adapter.LocalDateXMLAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    @XmlElement(name = "order_id")
    private int id;
    @XmlElement(name = "order_date")
    @XmlJavaTypeAdapter(value = LocalDateXMLAdapter.class)
    private LocalDate date;
    @XmlElement
    private double total;
    @XmlElement
    private Items items;
}
