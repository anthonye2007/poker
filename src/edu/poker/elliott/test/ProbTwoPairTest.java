package edu.poker.elliott.test;

import edu.poker.elliott.Elliott_Tools;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProbTwoPairTest {

    @Test
    public void testTwoPairExistsInFullBoard() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 10, 12, 0};

        double probability = Elliott_Tools.probOfTwoPair(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testNoTwoPairExistsInFullBoard() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 10, 11, 3};

        double probability = Elliott_Tools.probOfTwoPair(pocket, board);

        assertEquals(0, probability, 0.001);
    }

    @Test
    public void testTwoPairExistsInBoardOfFour() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 12, 0};

        double probability = Elliott_Tools.probOfTwoPair(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testTwoPairExistsInBoardOfThree() {
        int[] pocket = {0, 12};
        int[] board = {1, 12, 0};

        double probability = Elliott_Tools.probOfTwoPair(pocket, board);

        assertEquals(1, probability, 0.001);
    }
}
