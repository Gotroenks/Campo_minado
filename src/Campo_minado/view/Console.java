package Campo_minado.view;

import Campo_minado.controller.ErrorExceptions;
import Campo_minado.model.GameBoard;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Console {

    private final GameBoard board;
    Scanner scanner = new Scanner(System.in);

    public Console(GameBoard board) {
        this.board = board;

        execute();
    }

    private void execute() {
        try {
            while(true) {
                loop();
                System.out.println("Next Game (Y/N)");
                String answer = scanner.next();
                if (answer.equalsIgnoreCase("n"))
                    break;
                board.reset();
            }
        }catch (ErrorExceptions e) {
            System.out.println("End Game");
        }
        finally {
            scanner.close();
        }
    }

    private void loop() {
        try {
            while (!board.fieldClick()) {
                System.out.println(board);

                String insert = usersValues("Insert line and column: ");

                Iterator<Integer> it =  Arrays.stream(insert.split(",")).map(t -> Integer.parseInt(t.trim())).iterator();
                insert = usersValues("1 - open    2 - (un)check\n");

                if (insert.equalsIgnoreCase("1")) {
                    board.open(it.next(), it.next());
                } else {
                    board.checked(it.next(), it.next());
                }
            }

            System.out.println("you win");
        }catch(RuntimeException e) {
            System.out.println("you lose");
        }
    }

    private String usersValues(String value) {
        System.out.print(value);
        String digit = scanner.nextLine();
        if(digit.equalsIgnoreCase("exit"))
            throw new ErrorExceptions();
        return digit;
    }
}
