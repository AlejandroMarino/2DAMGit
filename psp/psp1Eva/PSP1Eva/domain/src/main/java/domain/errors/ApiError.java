package domain.errors;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private String message;
    private String fecha;
}
