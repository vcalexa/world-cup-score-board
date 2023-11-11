package scoreboard;

import scoreboard.model.Game;

import java.util.List;

public interface ScoreBoardService {
    void updateGameScore(long gameNumber, int homeScore, int AwayScore);

    List<Game> getActiveGames();

    Game startGame(long gameNumber, String homeTeam, String awayTeam);

    void finishGame(Game game);
}
