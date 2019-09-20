package biz.oneilindustries.exceptions;

public class UserRegistrationError extends RuntimeException {

    public UserRegistrationError(String message) {
        super(message);
    }
}
