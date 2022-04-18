package Campo_minado.model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private int lines = 0;
    private int columns = 0;
    private int mines = 0;

    private final List<Field> boards = new ArrayList<>();

    public GameBoard(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;

        boardGenerated();
        getNeighbor();
        sortMines();
    }


    private void boardGenerated() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                boards.add(new Field(i, j));
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
        while(quantityMines < mines) {
            int random = (int) (Math.random() * boards.size());
            boards.get(random).mined();
            quantityMines = (int) boards.stream().filter(Field::getMined).count();
        }
    }

    public boolean fieldClick() {
        return boards.stream().allMatch(Field::fieldClick);
    }

    public void reset() {
        boards.forEach(Field::reset);
        sortMines();
    }

    public void open(int line, int column) {
        try {
            boards.parallelStream()
                    .filter(t -> t.getLine() == line && t.getColumn() == column)
                    .findFirst()
                    .ifPresent(Field::open);
        } catch (RuntimeException e) {
            boards.forEach(t -> t.setOpen(true));
            throw e;
        }
    }

    public void checked(int line, int column) {
        boards.parallelStream()
                .filter(t -> t.getLine() == line && t.getColumn() == column)
                .findFirst()
                .ifPresent(Field::turned);
    }

    public String toString() {
        StringBuilder build = new StringBuilder();

        for (int i = 0; i < columns; i++) {
            build.append("   ").append(i).append("");
        }
        build.append("\n");
        int incr = 0;
        for (int i = 0; i < lines; i++) {
            build.append(i);
            build.append(" ");

            for (int j = 0; j < columns; j++) {
                build.append("[");
                build.append(boards.get(incr));
                build.append("] ");
                incr++;
            }
            build.append("\n");
        }
        return build.toString();
    }
}
