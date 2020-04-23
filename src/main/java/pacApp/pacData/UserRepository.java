package pacApp.pacData;

import pacApp.pacModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryExtension {
    User findById(long id);
    Optional<User> findOneByEmail(String email);
}
