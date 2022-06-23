package Campo_minado_interface.view;

import Campo_minado_interface.model.GameBoard;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("Serial")
public class GamePanel extends JPanel {

    public GamePanel(GameBoard board) {

        setLayout(new GridLayout(board.getLines(), board.getColumns()));

        board.to(t -> add(new FieldButton(t)));

        board.obeserverRegister(t -> {

            SwingUtilities.invokeLater(() -> {
                if(board.fieldClick())
                    JOptionPane.showMessageDialog(this, "Ganhou ");
                else
                    JOptionPane.showMessageDialog(this, "Perdeu");
                board.reset();
            });
        });
    }
}