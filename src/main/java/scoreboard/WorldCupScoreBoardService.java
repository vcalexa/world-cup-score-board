package scoreboard;

import scoreboard.model.Game;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WorldCupScoreBoardService implements ScoreBoardService {
    private Clock clock = Clock.systemDefaultZone();

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    private final List<Game> games = new ArrayList<>();

    @Override
    public Game startGame(Long gameNumber, String homeTeam, String awayTeam) {
        for (Game game : games) {
            if (gameNumber.equals(game.getNumber()))
                throw new RuntimeException("Game with same number already exists.");
        }
        Game game = new Game(gameNumber, 0, 0, homeTeam, awayTeam, true, LocalDateTime.now());
        games.add(game);
        return game;
    }

    @Override
    public void finishGame(Game game) {
        game.setIsActive(false);
    }

    @Override
    public void updateGame(Long gameNumber, Integer homeScore, Integer AwayScore) {
        games.stream()
                .filter(game -> game.getNumber().equals(gameNumber))
                .findFirst()
                .ifPresent(game -> {
                    game.setHomeScore(homeScore);
                    game.setAwayScore(AwayScore);
                });
    }

    @Override
    public List<Game> getActiveGames() {
        return games.stream()
                .filter(Game::getIsActive)
                .sorted(
                        Comparator.comparing((Game game) -> game.getHomeScore() + game.getAwayScore()
                                ).reversed()
                                .thenComparing(Comparator.comparing(Game::getStartedAt).reversed()))
                .toList();

    }
}
