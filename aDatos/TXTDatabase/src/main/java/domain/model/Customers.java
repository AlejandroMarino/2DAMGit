package domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Customers {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
