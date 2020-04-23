package pacApp.pacException;

import pacApp.pacModel.User;

public class AuthenticationForbiddenException extends RuntimeException {

    public AuthenticationForbiddenException(){
        super("Incorrect username or password");
    }

    public AuthenticationForbiddenException (String message) {
        super(message);
    }

    public AuthenticationForbiddenException (User user){
        super("User " + user.getEmail() + " forbidden");
    }
}
