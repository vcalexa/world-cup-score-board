package scoreboard;

public class NegativeScoreException extends RuntimeException {
    public NegativeScoreException(String message) {
        super(message);
    }
}
