package Campo_minado;

import Campo_minado.model.GameBoard;
import Campo_minado.view.Console;

public class Main {

    public static void main(String[] args) {

        GameBoard board = new GameBoard(6, 6, 10);

        new Console(board);
    }
}
