package domain.modelXml;

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
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioXml {
    @XmlElement
    private int id;
    @XmlElement
    private String nombre;
    @XmlElement(name = "fecha_de_nacimiento")
    @XmlJavaTypeAdapter(value = LocalDateXMLAdapter.class)
    private LocalDate fechaNacimiento;
    @XmlElement(name = "objeto")
    private List<ObjetoXml> objetos;
}
