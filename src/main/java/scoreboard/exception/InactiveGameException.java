package scoreboard.exception;

public class InactiveGameException extends RuntimeException {
    public InactiveGameException(String message) {
        super(message);
    }
}
