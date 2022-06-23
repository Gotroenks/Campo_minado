package Campo_minado_interface.view;

import Campo_minado_interface.model.EEventField;
import Campo_minado_interface.model.Field;
import Campo_minado_interface.model.IObserverField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FieldButton extends JButton implements IObserverField, MouseListener {

    private final Color BG_Def = new Color(184, 184, 184);
    private final Color BG_Chek = new Color(88, 179, 247);
    private final Color BG_Explod = new Color(189, 66, 68);
    private final Color BG_Open = new Color(0, 100, 0);
    private Field field;
    public FieldButton(Field field) {
        this.field = field;
        setBackground(BG_Def);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);

        field.register(this);
    }

    @Override
    public void event(Field field, EEventField event) {
        switch (event) {
            case OPEN -> styleAppOpen();
            case CHECKED -> styleAppChecked();
            case EXPLODE -> styleAppExplode();
            default -> styleAppDesafult();
        }

        SwingUtilities.invokeLater(() -> {
            repaint();
            validate();
        }); //garantia
    }

    private void styleAppDesafult() {
        setBackground(BG_Def);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void styleAppExplode() {
        setBackground(BG_Explod);
        setForeground(Color.white);
        setText("x");
    }

    private void styleAppChecked() {
        setBackground(BG_Chek);
        setText("ck");
    }

    private void styleAppOpen() {

        if(field.getMined())
            setBackground(BG_Explod);
        setBackground(BG_Def);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        switch (field.neighborMines()) {
            case 1 -> setForeground(BG_Open);
            case 2 -> setForeground(Color.BLUE);
            case 3 -> setForeground(Color.YELLOW);
            case 4,5,6 -> setForeground(Color.RED);
            default -> setForeground(Color.pink);
        }

        String value = !field.securityNeighbors() ? field.neighborMines() + "": "";
        setText(value);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1)
            field.open();
        else
            field.turned();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
