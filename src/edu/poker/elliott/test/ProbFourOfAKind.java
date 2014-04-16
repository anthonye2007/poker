package edu.poker.elliott.test;

import edu.poker.elliott.Elliott_Tools;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProbFourOfAKind {

    @Test
    public void testExistsInFullBoard() {
        int[] pocket = {0, 0};
        int[] board = {1, 2, 0, 12, 0};

        double probability = Elliott_Tools.probOfFourOfAKind(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testNotExistsInFullBoard() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 10, 11, 3};

        double probability = Elliott_Tools.probOfThreeOfAKind(pocket, board);

        assertEquals(0, probability, 0.001);
    }

    @Test
    public void testOneEmptySlotWithOneRank() {
        int[] pocket = {0, 0};
        int[] board = {1, 2, 0, 12};

        double probability = Elliott_Tools.probOfFourOfAKind(pocket, board);

        int suitsRemaining = 1;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double expectedProb = suitsRemaining / (double) remainingCards;

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testOneEmptySlotWithTwoRanks() {
        int[] pocket = {0, 0};
        int[] board = {1, 1, 0, 1};

        double probability = Elliott_Tools.probOfFourOfAKind(pocket, board);

        int suitsRemaining = 2;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double expectedProb = suitsRemaining / (double) remainingCards;

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testTwoEmptySlotsWhenNeedOne() {
        int[] pocket = {0, 0};
        int[] board = {1, 2, 0};

        double probability = Elliott_Tools.probOfFourOfAKind(pocket, board);

        int suitsRemaining = 1;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probOfFirstCard = suitsRemaining / (double) remainingCards;
        double probOfSecondCard = suitsRemaining / (double) (remainingCards - 1);
        double expectedProb = probOfFirstCard + probOfSecondCard;

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testTwoEmptySlotsWhenNeedTwo() {
        int[] pocket = {0, 0};
        int[] board = {1, 2, 3};

        double probability = Elliott_Tools.probOfFourOfAKind(pocket, board);

        int suitsRemaining = 1;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probOfFirstCard = suitsRemaining / (double) remainingCards;
        double probOfSecondCard = suitsRemaining / (double) (remainingCards - 1);
        double expectedProb = probOfFirstCard * probOfSecondCard;

        assertEquals(expectedProb, probability, 0.001);
    }
}
