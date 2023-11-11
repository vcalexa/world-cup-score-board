package scoreboard.validation;

import scoreboard.exception.GameAlreadyExistsException;
import scoreboard.exception.InvalidTeamNameException;
import scoreboard.exception.NegativeScoreException;
import scoreboard.exception.TeamNameAlreadyInUseException;
import scoreboard.model.Game;

import java.util.Map;

public class GameValidator {
    public static final String TEAM_ARE_REQUIRED_AS_NON_EMPTY_STRINGS =
            "homeTeam and awayTeam are required as non-empty strings";
    public static final String SCORES_HAVE_TO_BE_EQUAL_OR_LARGER_THAN_0 =
            "Both away and home scores have to be equal or larger than 0";
    public static final String INVALID_GAME_NUMBER = "Invalid game number";

    public static void validateGameNumber(long gameNumber) {
        if (gameNumber <= 0) {
            throw new IllegalArgumentException(INVALID_GAME_NUMBER);
        }
    }

    public static void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.trim().isEmpty() || awayTeam == null || awayTeam.trim().isEmpty()) {
            throw new InvalidTeamNameException(TEAM_ARE_REQUIRED_AS_NON_EMPTY_STRINGS);
        }
    }

    public static void validateScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) throw new NegativeScoreException(SCORES_HAVE_TO_BE_EQUAL_OR_LARGER_THAN_0);
    }

    public static void validateGameAlreadyExists(long gameNumber, Map<Long, Game> games) {
        if (games.containsKey(gameNumber)) {
            throw new GameAlreadyExistsException("Game with the same number already exists: " + gameNumber);
        }
    }

    public static void validateTeamNamesNotInUse(String homeTeam, String awayTeam, Map<Long, Game> games) {
        if (isTeamNameInUse(homeTeam, games) || isTeamNameInUse(awayTeam, games)) {
            throw new TeamNameAlreadyInUseException("A game cannot start with a team name that is already part of an active game.");
        }
    }

    private static boolean isTeamNameInUse(String teamName, Map<Long, Game> games) {
        return games.values().stream()
                .anyMatch(game -> game.isActive() && (teamName.equals(game.getHomeTeamName()) || teamName.equals(game.getAwayTeamName())));
    }
}
