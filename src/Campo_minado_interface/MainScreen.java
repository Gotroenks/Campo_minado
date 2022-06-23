package Campo_minado_interface;

import Campo_minado_interface.model.GameBoard;
import Campo_minado_interface.view.GamePanel;

import javax.swing.*;

@SuppressWarnings("Serial")
public class MainScreen extends JFrame {

    public MainScreen() {
        GameBoard board = new GameBoard(6, 15, 10);

        add(new GamePanel(board));

        setTitle("Minefield");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {

        new MainScreen();
    }

}
