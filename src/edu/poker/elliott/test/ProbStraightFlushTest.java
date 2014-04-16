package edu.poker.elliott.test;

import edu.poker.elliott.Elliott_Tools;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProbStraightFlushTest {

    @Test
    public void testExistsInFullBoard() {
        int[] pocket = {0, 3};
        int[] board = {1, 2, 4, 5, 6};

        double probability = Elliott_Tools.probOfStraightFlush(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testNotExistsInFullBoard() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 8+13, 11+13, 3+13*2};

        double probability = Elliott_Tools.probOfStraightFlush(pocket, board);

        assertEquals(0, probability, 0.001);
    }
}
