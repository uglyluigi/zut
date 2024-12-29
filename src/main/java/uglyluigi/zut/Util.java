package uglyluigi.zut;

import uglyluigi.zut.gamebusiness.TicTacToe;

public class Util {
    public static int[] fromTicTacToeSquareStatus(TicTacToe.TicTacToeSquareStatus[] statuses)
    {
        int[] i = new int[statuses.length];
        for (int j = 0; j < i.length; j++)
        {
            i[j] = statuses[j].ordinal();
        }
        return i;
    }

    public static TicTacToe.TicTacToeSquareStatus[] fromIntArray(int[] board)
    {
        TicTacToe.TicTacToeSquareStatus[] i = new TicTacToe.TicTacToeSquareStatus[board.length];
        for (int j = 0; j < i.length; j++)
        {
            i[j] = TicTacToe.TicTacToeSquareStatus.values()[board[j]];
        }
        return i;
    }
}
