package edu.poker.elliott.test;

import edu.poker.elliott.Elliott_Tools;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProbPairTest {

    @Test
    public void testPairExistsInEmptyBoard() {
        int[] pocket = {0, 0+13};
        int[] board = {};

        double probability = Elliott_Tools.probOfOnePair(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testPairNotExistsInEmptyBoard() {
        int[] pocket = {0, 1};
        int[] board = {};

        double probability = Elliott_Tools.probOfOnePair(pocket, board);

        assertEquals(true, probability < 1);
    }

    @Test
    public void testPairExistsInFullBoard() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 10, 11, 0};

        double probability = Elliott_Tools.probOfOnePair(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testNoPairExistsInFullBoard() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 10, 11, 3};

        double probability = Elliott_Tools.probOfOnePair(pocket, board);

        assertEquals(0, probability, 0.001);
    }

    @Test
    public void testPairExistsInBoardOfFour() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 10, 0};

        double probability = Elliott_Tools.probOfOnePair(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testPairExistsInBoardOfThree() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 0};

        double probability = Elliott_Tools.probOfOnePair(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testPairNotCurrentlyExistsInBoardOfThree() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 2};

        double probability = Elliott_Tools.probOfOnePair(pocket, board);
        double expectedProb = calculateExpectedProb(pocket, board);

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testPairNotCurrentlyExistsInBoardOfFour() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 2, 3};

        double probability = Elliott_Tools.probOfOnePair(pocket, board);
        double expectedProb = calculateExpectedProb(pocket, board);

        assertEquals(expectedProb, probability, 0.001);
    }

    private double calculateExpectedProb(int[] pocket, int[] board) {
        int possibleRanks = pocket.length;
        int totalRanks = 13;
        int numOpenSlots = Elliott_Tools.CARDS_IN_FULL_BOARD - board.length;

        double expectedProbPerSlot = possibleRanks / (double) totalRanks;
        return expectedProbPerSlot * numOpenSlots;
    }
}
