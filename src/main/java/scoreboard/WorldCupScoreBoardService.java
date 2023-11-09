package scoreboard;

import scoreboard.model.Game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WorldCupScoreBoardService implements ScoreBoardService {
    private final List<Game> games = new ArrayList<>();

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
                .sorted(Comparator.comparing(Game::getStartedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Game startGame(Long gameNumber) {
        Game game = new Game(gameNumber, 0, 0, true, LocalDateTime.now());
        games.add(game);
        return game;
    }

    @Override
    public void finishGame(Game game) {
        game.setIsActive(false);
    }
}
