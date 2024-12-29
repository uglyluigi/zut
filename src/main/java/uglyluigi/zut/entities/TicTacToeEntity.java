package uglyluigi.zut.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import uglyluigi.zut.gamebusiness.TicTacToe;

import java.util.Date;

@Entity
@Table(schema = "zut", name = "state")
public class TicTacToeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    public int id;

    public String x_name;

    public String o_name;

    public Date game_start_date;

    public Date most_recent_update_date;

    public int[] board_state;

    @Enumerated(EnumType.STRING)
    public TicTacToe.TicTacToeSquareStatus turn;

    @Enumerated(EnumType.STRING)
    public TicTacToe.TicTacToeSquareStatus abandoned;

    @Enumerated(EnumType.STRING)
    public TicTacToe.VictoryCondition won;
}
