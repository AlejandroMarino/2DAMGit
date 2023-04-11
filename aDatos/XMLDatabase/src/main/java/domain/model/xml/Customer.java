package domain.model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
    @XmlElement
    private String firstName;
    @XmlElement
    private String lastName;
    @XmlElement
    private String email;
    @XmlElement
    private String phone;
    @XmlElement(name = "order")
    private List<Order> orders;
}
