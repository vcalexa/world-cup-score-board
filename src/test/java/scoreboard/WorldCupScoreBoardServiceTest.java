package scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scoreboard.exception.GameNotFoundException;
import scoreboard.exception.InactiveGameException;
import scoreboard.exception.InvalidTeamNameException;
import scoreboard.model.Game;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WorldCupScoreBoardServiceTest {
    ScoreBoardService service;
    private static final String HOME_TEAM = "Romania";
    private static final String AWAY_TEAM = "Austria";

    @BeforeEach
    void setup() {
        service = new WorldCupScoreBoardService();
    }

    @Test
    public void testEmptyScoreBoard() {
        List<Game> gameList = service.getActiveGames();
        assertEquals(List.of(), gameList);
    }

    @Test
    public void testAddOneGame() {
        service.startGame(500L, HOME_TEAM, AWAY_TEAM);
        List<Game> gameList = service.getActiveGames();
        assertEquals(1, gameList.size());
    }

    @Test
    public void testAddGameTwice() {
        service.startGame(500L, HOME_TEAM, AWAY_TEAM);

        assertThrows(RuntimeException.class, () -> {
            service.startGame(500L, HOME_TEAM, AWAY_TEAM);
        });
    }

    @Test
    public void testStartGamesAndFinishGame() {
        Game game1 = service.startGame(1500L, HOME_TEAM, AWAY_TEAM);
        Game game2 = service.startGame(2500L, HOME_TEAM, AWAY_TEAM);
        Game game3 = service.startGame(3500L, HOME_TEAM, AWAY_TEAM);

        service.finishGame(game3);
        List<Game> gameList = service.getActiveGames();
        assertEquals(2, gameList.size());

        service.finishGame(game2);
        gameList = service.getActiveGames();
        assertEquals(1, gameList.size());

        service.finishGame(game1);
        gameList = service.getActiveGames();
        assertEquals(0, gameList.size());
    }


    @Test
    public void testMostRecentSorting() throws InterruptedException {
        service.startGame(1500L, HOME_TEAM, AWAY_TEAM);
        Thread.sleep(100);
        service.startGame(2500L, HOME_TEAM, AWAY_TEAM);
        Thread.sleep(100);
        service.startGame(3500L, HOME_TEAM, AWAY_TEAM);

        List<Game> gameList = service.getActiveGames();

        assertEquals(3500L, gameList.get(0).getNumber());
        assertEquals(2500L, gameList.get(1).getNumber());
        assertEquals(1500L, gameList.get(2).getNumber());
    }

    @Test
    public void testUpdateGames() {
        service.startGame(1500L, "Mexico", "Canada");
        service.updateGameScore(1500L, 0, 5);

        List<Game> gameList = service.getActiveGames();

        assertEquals(5, gameList.get(0).getAwayScore());

        service.updateGameScore(1500L, 6, 12);
        assertEquals(12, gameList.get(0).getAwayScore());

    }

    @Test
    public void testFinishNonExistingGame() {
        assertThrows(GameNotFoundException.class, () -> {
            Game game = new Game(1L, 0, 0, "TeamA", "TeamB", true, LocalDateTime.now());
            service.finishGame(game);
        });
    }

    @Test
    public void testFinishExistingGameTwice() {
        Game game = service.startGame(1L, "TeamA", "TeamB");
        service.finishGame(game);
        assertThrows(InactiveGameException.class, () -> service.finishGame(game));
    }

    @Test
    public void testStartGameWithNullTeams() {
        assertThrows(InvalidTeamNameException.class, () -> {
            service.startGame(1L, null, null);
        });
    }

    @Test
    public void testUpdateScoresWithNegativeValues() {
        Game game = service.startGame(1L, "TeamA", "TeamB");

        assertThrows(NegativeScoreException.class, () -> {
            service.updateGameScore(game.getNumber(), -1, 2);
        });

        assertThrows(NegativeScoreException.class, () -> {
            service.updateGameScore(game.getNumber(), 1, -2);
        });

        assertThrows(NegativeScoreException.class, () -> {
            service.updateGameScore(game.getNumber(), -1, -2);
        });

        // Check that the scores are not updated
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
    }

    @Test
    void testUpdateFinishedGame() {
        long gameNumber = 123L;
        Game game = service.startGame(gameNumber, "TeamA", "TeamB");
        service.finishGame(game);

        assertThrows(InactiveGameException.class, () -> {
            service.updateGameScore(gameNumber, 1, 2);
        });
    }

    @Test
    public void testGivenExample() throws InterruptedException {
        service.startGame(1500L, "Mexico", "Canada");
        Thread.sleep(50);
        service.updateGameScore(1500L, 0, 5);

        service.startGame(2500L, "Spain", "Brazil");
        Thread.sleep(50);
        service.updateGameScore(2500L, 10, 2);

        service.startGame(3500L, "Germany", "France");
        Thread.sleep(50);
        service.updateGameScore(3500L, 2, 2);

        service.startGame(4500L, "Uruguay", "Italy");
        Thread.sleep(50);
        service.updateGameScore(4500L, 6, 6);

        service.startGame(5500L, "Argentina", "Australia");
        Thread.sleep(50);
        service.updateGameScore(5500L, 3, 1);

        List<Game> gameList = service.getActiveGames();

        assertEquals(4500L, gameList.get(0).getNumber());
        assertEquals(2500L, gameList.get(1).getNumber());
        assertEquals(1500L, gameList.get(2).getNumber());
        assertEquals(5500L, gameList.get(3).getNumber());
        assertEquals(3500L, gameList.get(4).getNumber());
    }
}