package domain.model.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    private String username;
    private String password;
    private String role;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
