package scoreboard.exception;

public class InvalidTeamNameException extends RuntimeException {
    public InvalidTeamNameException(String message) {
        super(message);
    }
}
