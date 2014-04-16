package edu.poker.elliott.test;

import edu.poker.elliott.Elliott_Tools;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProbFlushTest {

    @Test
    public void testExistsInFullBoard() {
        int[] pocket = {0, 3};
        int[] board = {1, 2, 0, 12, 0};

        double probability = Elliott_Tools.probOfFlush(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testOneOpenSlot() {
        int[] pocket = {0, 3};
        int[] board = {21, 22, 0, 12};

        double probability = Elliott_Tools.probOfFlush(pocket, board);

        int remainingInSuit = 13 - 4;
        int remainingInDeck = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double expectedProb = remainingInSuit / (double) remainingInDeck;

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testTwoOpenSlotsAndNeedTwo() {
        int[] pocket = {0, 3};
        int[] board = {21, 22, 0};

        double probability = Elliott_Tools.probOfFlush(pocket, board);

        int remainingInSuit = 13 - 3;
        int remainingInDeck = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probOfFirstCard = remainingInSuit / (double) remainingInDeck;
        double probOfSecondCard = (remainingInSuit - 1) / (double) (remainingInDeck - 1);

        double expectedProb = probOfFirstCard * probOfSecondCard;

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testTwoOpenSlotsAndNeedOne() {
        int[] pocket = {0, 3};
        int[] board = {21, 2, 0};

        double probability = Elliott_Tools.probOfFlush(pocket, board);

        int remainingInSuit = 13 - 4;
        int remainingInDeck = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probOfFirstCard = remainingInSuit / (double) remainingInDeck;
        double probOfSecondCard = (remainingInSuit - 1) / (double) (remainingInDeck - 1);

        double expectedProb = probOfFirstCard + probOfSecondCard;

        assertEquals(expectedProb, probability, 0.001);
    }
}
