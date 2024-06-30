package wgu.etreece.swat.repositories.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import wgu.etreece.swat.models.user.Role;
import wgu.etreece.swat.models.user.RoleEnum;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(RoleEnum name);
}
