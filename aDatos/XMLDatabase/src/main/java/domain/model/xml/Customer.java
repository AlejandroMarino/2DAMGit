package domain.model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
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
    @XmlElement
    private List<Order> orders;
}
