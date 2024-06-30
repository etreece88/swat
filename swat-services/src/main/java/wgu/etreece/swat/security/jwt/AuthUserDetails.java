package wgu.etreece.swat.security.jwt;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import wgu.etreece.swat.models.user.AppUser;

@Data
public class AuthUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private boolean isDarkModeEnabled;

    private Collection<? extends GrantedAuthority> authorities;

    public AuthUserDetails(String id, String username, String email, String password, boolean isDarkModeEnabled,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isDarkModeEnabled = isDarkModeEnabled;
        this.authorities = authorities;
    }

    public static AuthUserDetails build(AppUser user) {
        List<GrantedAuthority> authorities = user.getRoleEnums().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new AuthUserDetails(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.isDarkModeEnabled(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AuthUserDetails user = (AuthUserDetails) o;
        return Objects.equals(id, user.id);
    }
}