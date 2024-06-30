package wgu.etreece.swat.services.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wgu.etreece.swat.models.user.AppUser;
import wgu.etreece.swat.models.user.Role;
import wgu.etreece.swat.models.user.RoleEnum;
import wgu.etreece.swat.repositories.user.AppUserRepository;
import wgu.etreece.swat.repositories.user.RoleRepository;

import java.util.*;

@Slf4j
@Service
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public void deleteUser(String username) throws UsernameNotFoundException {
        AppUser user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        appUserRepository.delete(user);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public void changeRole(String username, Set<RoleEnum> roles) {
        AppUser user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        user.setRoleEnums(roles);
        appUserRepository.save(user);
    }
}
