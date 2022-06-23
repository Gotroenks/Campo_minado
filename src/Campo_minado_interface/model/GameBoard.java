package Campo_minado_interface.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GameBoard implements IObserverField{

    private final int lines;
    private final int columns;
    private final int mines;
    private final List<Field> boards = new ArrayList<>();
    private final List<Consumer<Boolean>> observers = new ArrayList<>();

    public GameBoard(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;

        boardGenerated();
        getNeighbor();
        sortMines();
    }

    public void to(Consumer<Field> func) {
        boards.forEach(func);
    }

    public void  obeserverRegister(Consumer<Boolean> ob) {
        observers.add(ob);
    }

    public void notify(Boolean res) {
        observers.forEach(t -> t.accept(res));
    }

    private void boardGenerated() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {

                Field field = new Field(i, j);
                field.register(this);
                boards.add(field);
            }
        }
    }

    private void getNeighbor() {
        for (var f1: boards) {
            for (var f2: boards) {
                f1.addNeighbors(f2);
            }
        }
    }

    private void sortMines() {
        int quantityMines = 0;

        do {
            int random = (int) (Math.random() * boards.size());
            boards.get(random).mined();
            quantityMines = (int) boards.stream().filter(Field::getMined).count();
        }while(quantityMines < mines);
    }

    public boolean fieldClick()  {
        return boards.stream()
                .allMatch(Field::fieldClick);
    }

    public void reset() {
        boards.forEach(Field::reset);
        sortMines();
    }

    public void open(int line, int column) {
        boards.parallelStream()
                .filter(t -> t.getLine() == line && t.getColumn() == column)
                .findFirst()
                .ifPresent(Field::open);
    }

    public void checked(int line, int column) {
        boards.parallelStream()
                .filter(t -> t.getLine() == line && t.getColumn() == column)
                .findFirst()
                .ifPresent(Field::turned);
    }

    @Override
    public void event(Field field, EEventField event) {
        if(event == EEventField.EXPLODE) {
            viewMines();
            notify(false);
        }
        else if(fieldClick()) {
            notify(true);
        }
    }

    private void viewMines() {
        boards.stream()
                .filter(Field::getMined)
                .filter(t -> !t.isChecked())
                .forEach(t -> t.setOpen(true));
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }
}
