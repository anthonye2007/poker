package edu.poker.elliott.test;

import edu.poker.elliott.Elliott_Tools;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProbFullHouse {

    @Test
    public void testExistsInFullBoard() {
        int[] pocket = {0, 3};
        int[] board = {2, 2, 0, 12, 0};

        double probability = Elliott_Tools.probFullHouse(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testNotExistsInFullBoard() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 10, 11, 3};

        double probability = Elliott_Tools.probFullHouse(pocket, board);

        assertEquals(0, probability, 0.001);
    }

    @Test
    public void testOneEmptySlotWithTwoAndTwo() {
        int[] pocket = {0, 3};
        int[] board = {2, 2, 0, 12};

        double probability = Elliott_Tools.probFullHouse(pocket, board);

        int possibleSuccesses = 2 * 2;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;

        double expectedProb = possibleSuccesses / (double) remainingCards;

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testOneEmptySlotWithThreeAndOne() {
        int[] pocket = {0, 0};
        int[] board = {2, 1, 0, 12};

        double probability = Elliott_Tools.probFullHouse(pocket, board);

        int possibleSuccesses = 3 * 3;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;

        double expectedProb = possibleSuccesses / (double) remainingCards;

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testTwoEmptySlotsWithTwoAndOne() {
        int[] pocket = {0, 3};
        int[] board = {2, 1, 0};

        double probability = Elliott_Tools.probFullHouse(pocket, board);

        int possibleSuccesses = 3 * 3;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;

        double probFirstCard = possibleSuccesses / (double) remainingCards;
        double probOfSecondCard = 2 / (double) (remainingCards - 1);

        double expectedProb = probFirstCard * probOfSecondCard;

        assertEquals(expectedProb, probability, 0.001);
    }
}
