package scoreboard.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Game {
    long number;
    int homeScore;
    int awayScore;
    String homeTeamName;
    String awayTeamName;
    boolean isActive;
    LocalDateTime startedAt;
}
