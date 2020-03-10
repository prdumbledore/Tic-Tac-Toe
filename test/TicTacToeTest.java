import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.spbstu.icc.TicTacToe;

class TicTacToeTest {

    @Test
    void setSymbol() {
        TicTacToe test = new TicTacToe();
        test.setCross(0, 0);
        test.setZero(2, 2);
        assertFalse(test.setCross(0, 0));
        assertFalse(test.setZero(2, 2));
        assertTrue(test.setCross(0, 1));
        assertTrue(test.setZero(1, 0));
        assertThrows(IllegalArgumentException.class, () -> test.setCross(4, 4));
    }

    @Test
    void clearSymbol() {
        TicTacToe test = new TicTacToe(4);
        test.setCross(2, 2);
        test.setCross(3, 3);
        test.setZero(2, 2);
        test.setZero(0, 3);
        assertTrue(test.clearSymbol(2, 2));
        assertTrue(test.clearSymbol(3, 3));
        assertFalse(test.clearSymbol(0, 0));
        assertFalse(test.clearSymbol(0, 1));
        assertThrows(IllegalArgumentException.class, () -> test.clearSymbol(4, 4));
    }

    @Test
    void getMaxSequence() {
        TicTacToe test1 = new TicTacToe(5);
        test1.setCross(0, 0);
        test1.setCross(4, 4);
        test1.setCross(1, 1);
        test1.setCross(2, 2);
        test1.setCross(3, 3);
        test1.setZero(1, 2);
        test1.setZero(0, 3);
        test1.clearSymbol(2, 2);

        assertEquals(2, test1.getMaxCrossSequence());
        assertEquals(2, test1.getMaxZeroSequence());

        TicTacToe test2 = new TicTacToe(7);

        test2.setCross(1, 1);
        test2.setCross(2, 1);
        test2.setCross(3, 1);
        test2.setCross(4, 1);
        test2.setCross(5, 1);
        test2.setCross(6, 1);
        test2.setCross(6, 0);
        test2.setCross(4, 2);
        test2.setCross(3, 3);
        test2.setCross(2, 4);
        test2.setCross(1, 5);
        test2.setCross(0, 6);

        test2.setZero(0, 1);
        test2.setZero(0, 4);
        test2.setZero(1, 2);
        test2.setZero(1, 4);
        test2.setZero(2, 3);
        test2.setZero(2, 6);
        test2.setZero(3, 4);
        test2.setZero(3, 5);
        test2.setZero(4, 4);
        test2.setZero(4, 5);
        test2.setZero(5, 3);
        test2.setZero(5, 4);
        test2.setZero(5, 6);
        test2.setZero(6, 2);
        test2.setZero(6, 4);

        assertEquals(7, test2.getMaxCrossSequence());

        test2.clearSymbol(4, 2);
        assertEquals(6, test2.getMaxCrossSequence());

        test2.clearSymbol(4, 4);
        test2.clearSymbol(3, 4);
        assertEquals(3, test2.getMaxZeroSequence());

        test2.clearSymbol(4, 1);
        assertEquals(4, test2.getMaxCrossSequence());

    }
}