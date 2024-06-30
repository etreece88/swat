package wgu.etreece.swat.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import wgu.etreece.swat.models.user.AppUser;
import wgu.etreece.swat.models.user.RoleEnum;
import wgu.etreece.swat.repositories.user.AppUserRepository;
import wgu.etreece.swat.services.user.UserService;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private AppUserRepository appUserRepository;

    private AppUser user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new AppUser("testUser", null, "", false);
    }

    @Test
    void testFindByUsername() {
        when(appUserRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Optional<AppUser> foundUser = userService.findByUsername("testUser");

        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void testDeleteUser() {
        when(appUserRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        doNothing().when(appUserRepository).delete(user);

        assertDoesNotThrow(() -> userService.deleteUser("testUser"));
        verify(appUserRepository, times(1)).delete(user);
    }

    @Test
    void testChangeRole() {
        when(appUserRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(appUserRepository.save(any(AppUser.class))).thenReturn(user);

        Set<RoleEnum> roles = Set.of(RoleEnum.ROLE_ADMIN);

        userService.changeRole("testUser", roles);

        verify(appUserRepository, times(1)).save(user);
        assertEquals(roles, user.getRoleEnums());
    }
}
