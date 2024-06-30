package wgu.etreece.swat.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private boolean isDarkModeEnabled;
    private List<String> roles;

    public JwtResponse(String accessToken, String id, String username, String email, boolean isDarkModeEnabled, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.isDarkModeEnabled = isDarkModeEnabled;
    }

}
