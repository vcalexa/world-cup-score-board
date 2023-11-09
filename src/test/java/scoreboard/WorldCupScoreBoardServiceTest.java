package scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scoreboard.model.Game;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldCupScoreBoardServiceTest {
    ScoreBoardService service;

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
        service.startGame(500L);
        List<Game> gameList = service.getActiveGames();
        assertEquals(1, gameList.size());
    }

    @Test
    public void testStartGamesAndFinishGame() {
        Game game1 = service.startGame(1500L);
        Game game2 = service.startGame(2500L);
        Game game3 = service.startGame(3500L);

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
        service.startGame(1500L);
        Thread.sleep(100);
        service.startGame(2500L);
        Thread.sleep(100);
        service.startGame(3500L);

        List<Game> gameList = service.getActiveGames();

        assertEquals(3500L, gameList.get(0).getNumber());
        assertEquals(2500L, gameList.get(1).getNumber());
        assertEquals(1500L, gameList.get(2).getNumber());
    }

}