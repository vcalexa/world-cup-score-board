package scoreboard;

import scoreboard.model.Game;

import java.util.List;

public class Example {
    public static void main(String[] args) {
        // Create an instance of the WorldCupScoreBoardService
        WorldCupScoreBoardService scoreboardService = new WorldCupScoreBoardService();

        // Start new matches
        Game game1 = scoreboardService.startGame(1L, "HomeTeamA", "AwayTeamA");
        Game game2 = scoreboardService.startGame(2L, "HomeTeamB", "AwayTeamB");

        // Update the score
        scoreboardService.updateGame(game1.getNumber(), 2, 1);
        scoreboardService.updateGame(game1.getNumber(), 5, 0);

        // Finish the match
        scoreboardService.finishGame(game1);

        // Get a summary of ongoing matches
        System.out.println("Ongoing Matches Summary:");
        List<Game> activeGames = scoreboardService.getActiveGames();
        activeGames.forEach(match -> System.out.println(match.getHomeTeamName() + " " + match.getHomeScore() + " - " +
                match.getAwayTeamName() + " " + match.getAwayScore()));
    }
}
