package scoreboard.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Game {
    Long number;
    Integer homeScore;
    Integer awayScore;
    Boolean isActive;
    LocalDateTime startedAt;
}
