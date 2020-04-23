package pacApp.pacData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pacApp.pacModel.User;

@Component
public class UserRepositoryImpl implements UserRepositoryExtension {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
    @Autowired
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    /*
    public UserRepositoryImpl(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    } */

    public UserRepositoryImpl(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user){
        String password = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        this.repository.saveAndFlush(user);
    }
}
