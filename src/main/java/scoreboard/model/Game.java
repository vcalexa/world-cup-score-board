package scoreboard.model;

public record Game(Long number, Integer homeScore, Integer awayScore, Boolean isActive) {
}
