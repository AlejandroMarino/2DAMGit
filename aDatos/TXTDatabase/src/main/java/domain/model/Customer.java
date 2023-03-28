package domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Customer(String line){
        String[] parts = line.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.firstName = parts[1];
        this.lastName = parts[2];
        this.email = parts[3];
        this.phone = parts[4];
    }

    public String toString(){
        return id + ";" + firstName + ";" + lastName + ";" + email + ";" + phone;
    }
}
