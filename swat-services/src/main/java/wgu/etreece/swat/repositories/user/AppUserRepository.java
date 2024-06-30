package wgu.etreece.swat.repositories.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import wgu.etreece.swat.models.user.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
