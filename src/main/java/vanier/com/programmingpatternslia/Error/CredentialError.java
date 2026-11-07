package vanier.com.programmingpatternslia.Error;

public class CredentialError extends RuntimeException {
    public CredentialError(String message) {
        super(message);
    }
}
