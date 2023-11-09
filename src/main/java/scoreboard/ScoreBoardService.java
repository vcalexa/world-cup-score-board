package scoreboard;

import scoreboard.model.Game;

import java.util.List;

public interface ScoreBoardService {
    void updateGame(Integer gameNumber, int homeScore, int AwayScore);

    List<Game> getActiveGames();
}
