package uglyluigi.zut.gamebusiness;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static uglyluigi.zut.gamebusiness.TicTacToe.TicTacToeSquareStatus.EMPTY;

@NoArgsConstructor
public class TicTacToe {
    public enum TicTacToeSquareStatus {
        EMPTY,
        X,
        O,
    }

    public enum VictoryCondition {
        WIN_X,
        WIN_O,
        CAT
    }
    public static final TicTacToeSquareStatus[] EMPTY_BOARD = {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY};
    public TicTacToeSquareStatus[] board = EMPTY_BOARD;
    private static final int[] MagicSquare = {2, 7, 6, 9, 5, 1, 4, 3, 8};

    public TicTacToe(TicTacToeSquareStatus[] board)
    {
        this.board = board;
    }

    public int idx(int x, int y) {
        return 3 * x + y;
    }

    private boolean boundsAreOK(int x, int y) {
        return idx(x, y) < board.length;
    }

    public boolean put(TicTacToeSquareStatus status, int x, int y) {
        int idx = idx(x, y);
        if (!boundsAreOK(x, y) || board[idx] != EMPTY) {
           return false;
        }

        board[idx] = status;
        return true;
    }

    public Optional<TicTacToeSquareStatus> at(int x, int y) {
        if (!boundsAreOK(x, y)) {
            return Optional.empty();
        }

        return Optional.of(board[idx(x, y)]);
    }

    public Optional<VictoryCondition> computeVictory()
    {
        int xSum = 0;
        int oSum = 0;
        boolean atLeastOneEmptySquare = false;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                switch (board[idx(x, y)]) {
                    case X -> {
                        xSum += MagicSquare[idx(x, y)];
                    }
                    case O -> {
                        oSum += MagicSquare[idx(x, y)];
                    }
                    case EMPTY -> {
                        atLeastOneEmptySquare = true;
                    }
                }
            }
        }

        if (xSum == 15) {
            return Optional.of(VictoryCondition.WIN_X);
        }

        if (oSum == 15) {
            return Optional.of(VictoryCondition.WIN_O);
        }

        if (!atLeastOneEmptySquare) {
            return Optional.of(VictoryCondition.CAT);
        }

        return Optional.empty();
    }
}
