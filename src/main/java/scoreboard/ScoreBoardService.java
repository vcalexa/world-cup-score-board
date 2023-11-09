package scoreboard;

import scoreboard.model.Game;

import java.util.List;

public interface ScoreBoardService {
    void updateGame(Long gameNumber, Integer homeScore, Integer AwayScore);

    List<Game> getActiveGames();

    Game startGame(Long gameNumber);

    void finishGame(Game game);
}
