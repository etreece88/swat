package wgu.etreece.swat.models.user;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "users")
public class AppUser {

    @Id
    private String id;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    private String password;

    private boolean isDarkModeEnabled;

    private Set<RoleEnum> roleEnums = new HashSet<>();

    public AppUser(String username, String email, String password, boolean isDarkModeEnabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isDarkModeEnabled = isDarkModeEnabled;
    }
}
