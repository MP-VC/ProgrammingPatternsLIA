package vanier.com.programmingpatternslia.Error;

public class UserError extends RuntimeException {
    public UserError(String message) {
        super(message);
    }
}
