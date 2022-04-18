package Test;

import Campo_minado.model.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class fieldTest {

    Field f = new Field(3,3);

    @Test
    void distanceTest() {
        Field neighbor = new Field(4,3);

       assertTrue(f.addNeighbors(neighbor));
    }

    @Test
    void distanceTest2() {
        Field neighbor = new Field(2,2);

       assertTrue(f.addNeighbors(neighbor));
    }

    @Test
    void distanceTest2NotDiagonal() {
        Field neighbor = new Field(5,3);

       assertFalse(f.addNeighbors(neighbor));
    }

    @Test
    void distanceTest2Diagonal() {
        Field neighbor = new Field(5,5);

       assertFalse(f.addNeighbors(neighbor));
    }

    @Test
    void defaultCheckedTest() {
        assertFalse(f.isChecked());
    }

    @Test
    void checkedTest() {
        f.turned();
        assertTrue(f.isChecked());
    }

    @Test
    void checked2Test() {
        f.turned();
        f.turned();
        assertFalse(f.isChecked());
    }

    @Test
    void notMinedNotCheckedTest() {
        assertTrue(f.open());
    }

    @Test
    void notMinedCheckedTest() {
        f.turned();
        assertFalse(f.open());
    }

    @Test
    void MinedNotCheckedTest() {
        f.mined();
        assertThrows(RuntimeException.class, () -> f.open());
    }

    @Test
    void opengWithNeighbor() {
        Field f2 = new Field(1, 1);
        Field f3 = new Field(2, 2);

        f2.addNeighbors(f3);
        f.addNeighbors(f2);
        f.open();

        assertTrue(f2.isOpen() && f3.isOpen());
    }

    @Test
    void opengWithNeighbor2() {
        Field f2 = new Field(1, 1);
        Field f3 = new Field(2, 2);

        f2.addNeighbors(f3);
        f.addNeighbors(f2);
        f.open();

        assertFalse(f2.isOpen() && f3.isClosed());
    }
}