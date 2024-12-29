package uglyluigi.zut.client;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class ZutClient {

    public static void main(String[] args) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        try (Terminal terminal = defaultTerminalFactory.createTerminal()) {
            terminal.putString("zut alpha v0.1");
            terminal.putCharacter('\n');
            terminal.flush();

            terminal.putString("Select your game!");
            terminal.putCharacter('\n');
            terminal.flush();
            terminal.putString("1) Tic-Tac-Toe");
            terminal.putCharacter('\n');
            terminal.flush();

            while (true)
            {
                KeyStroke stroke = terminal.readInput();

                if (stroke.getCharacter() != null) {
                    if (stroke.getCharacter() == '1') {
                        ticTacToeTime(terminal);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ticTacToeTime(Terminal terminal) throws IOException
    {
        terminal.clearScreen();
        terminal.putString("Tic-Tac-Toe over IP (TTToIP)");
        newln(terminal);
        terminal.flush();
        terminal.putString("     |     |     ");
        newln(terminal);
        terminal.putString("  %s  |  %s  |  %s  ");
        newln(terminal);
        terminal.putString("_____|_____|_____");
        newln(terminal);
        terminal.putString("     |     |     ");
        newln(terminal);
        terminal.putString("  %s  |  %s  |  %s  ");
        newln(terminal);
        terminal.putString("_____|_____|_____");
        newln(terminal);
        terminal.putString("     |     |     ");
        newln(terminal);
        terminal.putString("  %s  |  %s  |  %s  ");
        newln(terminal);
        terminal.putString("     |     |     ");
        newln(terminal);
        terminal.flush();
    }

    private static void newln(Terminal terminal) throws IOException {
        terminal.putCharacter('\n');
        terminal.flush();
    }
}
