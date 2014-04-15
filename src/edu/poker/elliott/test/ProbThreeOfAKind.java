package edu.poker.elliott.test;

import edu.poker.elliott.Elliott_Tools;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProbThreeOfAKind {

    @Test
    public void testThreeOfAKindExistsInFullBoard() {
        int[] pocket = {0, 3};
        int[] board = {1, 2, 0, 12, 0};

        double probability = Elliott_Tools.probOfThreeOfAKind(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testNotExistsInFullBoard() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 10, 11, 3};

        double probability = Elliott_Tools.probOfThreeOfAKind(pocket, board);

        assertEquals(0, probability, 0.001);
    }
}
