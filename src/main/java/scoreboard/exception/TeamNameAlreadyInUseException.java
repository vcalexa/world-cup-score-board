package scoreboard.exception;

public class TeamNameAlreadyInUseException extends RuntimeException {
    public TeamNameAlreadyInUseException(String message) {
        super(message);
    }
}
