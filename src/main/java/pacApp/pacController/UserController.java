package pacApp.pacController;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.RegexValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pacApp.pacData.UserRepository;
import pacApp.pacException.RegistrationBadRequestException;
import pacApp.pacLogic.Constants;
import pacApp.pacModel.Currency;
import pacApp.pacModel.User;
import pacApp.pacModel.request.UserInfo;
import pacApp.pacModel.response.GenericResponse;
import pacApp.pacSecurity.JwtAuthenticatedProfile;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController extends BaseRestController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository repository;

    @Autowired
    private DiscoveryClient discoveryClient;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity getAllUsers(){
        /*
        String userEmail = super.getAuthentication().getName();

        Optional<User> optUser = this.repository.findOneByEmail(userEmail);

        if (!optUser.isPresent()) {
            GenericResponse response = new GenericResponse(HttpStatus.BAD_REQUEST.value(),"Invalid user");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = optUser.get();

        //TODO: implement user roles

        long userId = user.getId();

        if (userId != 1L) {
            GenericResponse response = new GenericResponse(HttpStatus.FORBIDDEN.value(),"Request forbidden");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        */

        List<User> users = this.repository.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserInfo() {
        String userEmail = super.getAuthentication().getName();

        Optional<User> optUser = this.repository.findOneByEmail(userEmail);

        if (!optUser.isPresent()){
            GenericResponse response = new GenericResponse(HttpStatus.BAD_REQUEST.value(),"Invalid user");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = optUser.get();

        UserInfo userInfo = this.convertUserToUserInfo(user);

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> registerUser(@RequestBody User user){
        log.info("registerUser: " + user.toString());

        if (user.getEmail() == null || user.getPassword() == null) {
            throw new RegistrationBadRequestException();
        }

        EmailValidator emailValidator = EmailValidator.getInstance();

        if (!emailValidator.isValid(user.getEmail())) {
            throw new RegistrationBadRequestException();
        }

        Optional<User> optUser = this.repository.findOneByEmail(user.getEmail());

        if (optUser.isPresent()){
            GenericResponse response = new GenericResponse(HttpStatus.CONFLICT.value(),"User already registered");
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);
        }

        RegexValidator validator = new RegexValidator("((?=.*[a-z])(?=.*\\d)(?=.*[@#$%])(?=.*[A-Z]).{6,16})");

        if (!validator.isValid(user.getPassword())) {
            throw new RegistrationBadRequestException();
        }

        if (user.getDefaultCurrency() == null) {
            user.setDefaultCurrency(Constants.SERVICE_CURRENCY);
        }

        user.setId(0L);

        this.repository.saveUser(user);

        GenericResponse response = new GenericResponse(HttpStatus.OK.value(), "User registration successful");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@RequestBody UserInfo userInfo) {
        if (userInfo == null) {
            GenericResponse response = new GenericResponse(400, "Missing request body");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String userEmail = super.getAuthentication().getName();

        Optional<User> optUser = this.repository.findOneByEmail(userEmail);

        if (!optUser.isPresent()){
            GenericResponse response = new GenericResponse(HttpStatus.BAD_REQUEST.value(),"Invalid user");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = optUser.get();
        User userCopy = null;

        try {
            userCopy = (User) user.clone();
        } catch (CloneNotSupportedException ex) {
            log.error(ex.getMessage());
        }

        //change user settings

        if (userInfo.getDefaultCurrency() != null) {
            String currency = userInfo.getDefaultCurrency();
            int id = Currency.getCurrencyId(currency);

            if (id == -1) {
                GenericResponse response = new GenericResponse(HttpStatus.BAD_REQUEST.value(),currency + " is invalid");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Currency newDefaultCurrency = Currency.valueOf(currency);
            user.setDefaultCurrency(newDefaultCurrency);
        }

        //save user settings

        user = this.repository.saveAndFlush(user);

        //validate changes

        if (userCopy == null) {
            GenericResponse response = new GenericResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"User update failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (userCopy.equals(user)) {
            GenericResponse response = new GenericResponse(HttpStatus.OK.value(),"User settings not changed");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        GenericResponse response = new GenericResponse(HttpStatus.OK.value(),"User settings updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected UserInfo convertUserToUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        Currency currency = user.getDefaultCurrency();
        userInfo.setDefaultCurrency(currency.name());

        return userInfo;
    }


}
