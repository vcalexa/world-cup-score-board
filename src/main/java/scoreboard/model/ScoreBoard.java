package scoreboard.model;

import java.util.List;

public class ScoreBoard {
    private final List<Integer> activeGames;

    public ScoreBoard(List<Integer> activeGames) {
        this.activeGames = activeGames;
    }
}
