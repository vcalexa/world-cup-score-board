package scoreboard;

import scoreboard.exception.GameAlreadyExistsException;
import scoreboard.exception.GameNotFoundException;
import scoreboard.exception.InactiveGameException;
import scoreboard.model.Game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.synchronizedList;

public class WorldCupScoreBoardService implements ScoreBoardService {
    public static final String GAME_WITH_SAME_NUMBER_ALREADY_EXISTS = "Game with same number already exists:";
    public static final String GAME_CANNOT_BE_FOUND = "Game cannot be found:";
    public static final String GAME_IS_ALREADY_INACTIVE = "Game is already inactive:";
    private final List<Game> games = synchronizedList(new ArrayList<>());

    /**
     * Starts a new game with the specified game number, home team, and away team.
     *
     * @param gameNumber The number of the new game.
     * @param homeTeam   The name of the home team.
     * @param awayTeam   The name of the away team.
     * @return The newly started game.
     */
    @Override
    public Game startGame(Long gameNumber, String homeTeam, String awayTeam) {
        synchronized (games) {
            for (Game game : games) {
                if (gameNumber.equals(game.getNumber()))
                    throw new GameAlreadyExistsException(GAME_WITH_SAME_NUMBER_ALREADY_EXISTS + game.getNumber());
            }
        }
        Game game = new Game(gameNumber, 0, 0, homeTeam, awayTeam, true, LocalDateTime.now());
        games.add(game);
        return game;
    }

    /**
     * Finishes the specified game.
     *
     * @param game The game to be finished.
     * @throws GameNotFoundException If the specified game is not found in the list.
     * @throws InactiveGameException If the specified game is already inactive.
     */
    @Override
    public void finishGame(Game game) {
        synchronized (games) {
            if (!games.contains(game)) throw new GameNotFoundException(GAME_CANNOT_BE_FOUND + game.getNumber());
            if (!game.getIsActive()) throw new InactiveGameException(GAME_IS_ALREADY_INACTIVE + game.getNumber());
            game.setIsActive(false);
        }
    }

    /**
     * Updates the scores for a specified game.
     *
     * @param gameNumber The number of the game to be updated.
     * @param homeScore  The new home team score.
     * @param awayScore  The new away team score.
     */
    @Override
    public void updateGame(Long gameNumber, Integer homeScore, Integer awayScore) {
        synchronized (games) {
            games.stream()
                    .filter(game -> game.getNumber().equals(gameNumber))
                    .findFirst()
                    .ifPresent(game -> {
                        game.setHomeScore(homeScore);
                        game.setAwayScore(awayScore);
                    });
        }
    }

    /**
     * Retrieves a list of active games, sorted by the total score and most recent start time.
     *
     * @return A list of active games, sorted by the total score and most recent start time.
     */
    @Override
    public List<Game> getActiveGames() {
        synchronized (games) {
            return games.stream()
                    .filter(Game::getIsActive)
                    .sorted(
                            Comparator.comparing((Game game) -> game.getHomeScore() + game.getAwayScore()
                                    ).reversed()
                                    .thenComparing(Comparator.comparing(Game::getStartedAt).reversed()))
                    .toList();
        }
    }
}
