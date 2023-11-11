package scoreboard;

import scoreboard.exception.GameAlreadyExistsException;
import scoreboard.exception.GameNotFoundException;
import scoreboard.exception.InactiveGameException;
import scoreboard.exception.InvalidTeamNameException;
import scoreboard.model.Game;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static scoreboard.validation.GameValidator.*;

public class WorldCupScoreBoardService implements ScoreBoardService {
    public static final String GAME_CANNOT_BE_FOUND = "Game cannot be found:";
    public static final String GAME_IS_ALREADY_INACTIVE = "Game is already inactive:";

    private final Map<Long, Game> games = new ConcurrentHashMap<>();

    /**
     * Starts a new game with the specified game number, home team, and away team.
     *
     * @param gameNumber The number of the new game.
     * @param homeTeam   The name of the home team.
     * @param awayTeam   The name of the away team.
     * @return The newly started game.
     * @throws InvalidTeamNameException   If either team name is empty or null.
     * @throws GameAlreadyExistsException If the there already exists an active game with same number.
     */
    @Override
    public Game startGame(long gameNumber, String homeTeam, String awayTeam) {
        validateGameNumber(gameNumber);
        validateTeamNames(homeTeam, awayTeam);
        validateGameAlreadyExists(gameNumber, games);

        Game game = new Game(gameNumber, 0, 0, homeTeam, awayTeam, true, LocalDateTime.now());
        games.put(gameNumber, game);

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
        updateGame(game.getNumber(), game.getHomeScore(), game.getAwayScore(), false);
    }

    /**
     * Updates the scores for a specified game.
     *
     * @param gameNumber The number of the game to be updated.
     * @param homeScore  The new home team score.
     * @param awayScore  The new away team score.
     * @throws NegativeScoreException If either score is negative.
     * @throws GameNotFoundException  If the specified game is not found in the list.
     * @throws InactiveGameException  If the specified game is already inactive.
     */
    @Override
    public void updateGameScore(long gameNumber, int homeScore, int awayScore) {
        validateScores(homeScore, awayScore);
        updateGame(gameNumber, homeScore, awayScore, true);
    }


    /**
     * Retrieves a list of active games, sorted by the total score and most recent start time.
     *
     * @return A list of active games, sorted by the total score and most recent start time.
     */
    @Override
    public List<Game> getActiveGames() {
        return games.values().stream()
                .filter(Game::isActive)
                .sorted(Comparator.comparing((Game game) -> game.getHomeScore() + game.getAwayScore())
                        .reversed().thenComparing(Comparator.comparing(Game::getStartedAt).reversed()))
                .toList();
    }

    private void updateGame(long gameNumber, int homeScore, int awayScore, boolean active) {
        games.compute(gameNumber, (key, existingGame) -> {
            if (existingGame != null) {
                if (existingGame.isActive()) {
                    existingGame.setHomeScore(homeScore);
                    existingGame.setAwayScore(awayScore);
                    if (!active) {
                        existingGame.setActive(false);
                    }
                } else {
                    throw new InactiveGameException(GAME_IS_ALREADY_INACTIVE + existingGame.getNumber());
                }
                return existingGame;
            } else {
                throw new GameNotFoundException(GAME_CANNOT_BE_FOUND + gameNumber);
            }
        });
    }
}

