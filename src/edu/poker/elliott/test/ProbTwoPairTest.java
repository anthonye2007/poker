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

    @Test
    public void testTwoPairCannotExistInBoardOfFour() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 2, 3};

        double probability = Elliott_Tools.probOfTwoPair(pocket, board);

        assertEquals(0, probability, 0.001);
    }

    @Test
    public void testTwoPairOneOpenSlotInBoardOfFourWithPair() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 2, 12};

        double probability = Elliott_Tools.probOfTwoPair(pocket, board);

        // could be a 0, 1, 10, or 2
        int numRemainingOfEachRank = 3;
        int numPossibleRanks = 4;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probForOneRank = numRemainingOfEachRank / (double) remainingCards;
        double expectedProb = probForOneRank * numPossibleRanks;

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testTwoPairTwoOpenSlotsWithPair() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 12};

        double probability = Elliott_Tools.probOfTwoPair(pocket, board);

        // could be a 0, 1, 10, or two of the same unseen rank
        int numRemainingOfEachRank = 3;
        int numPossibleRanks = 3;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probForOneRank = numRemainingOfEachRank / (double) remainingCards;
        double probForMultipleRanks = probForOneRank * numPossibleRanks;

        int numRanksSeen = numPossibleRanks + 1;
        int numRemainingOfUnseenRank = 4;
        double probForFirstCardOfUnseenRank = numRanksSeen * numRemainingOfUnseenRank / (double) (remainingCards - 1);
        double probForSecondCardOfSameRank = (numRemainingOfUnseenRank - 1) / (double) (remainingCards - 2);
        double probOfUnseenPair = probForFirstCardOfUnseenRank * probForSecondCardOfSameRank;

        double expectedProb = probForMultipleRanks + probOfUnseenPair;

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testTwoPairTwoOpenSlotsWithoutPair() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 11};

        double probability = Elliott_Tools.probOfTwoPair(pocket, board);

        // could be a 0, 1, 10, 11, 12, or two of the same unseen rank
        int numRemainingOfEachRank = 3;
        int numPossibleRanks = 5;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probForOneRank = numRemainingOfEachRank / (double) remainingCards;
        double probForMultipleRanks = probForOneRank * numPossibleRanks;

        int numRanksSeen = numPossibleRanks + 1;
        int numRemainingOfUnseenRank = 4;
        double probForFirstCardOfUnseenRank = numRanksSeen * numRemainingOfUnseenRank / (double) (remainingCards - 1);
        double probForSecondCardOfSameRank = (numRemainingOfUnseenRank - 1) / (double) (remainingCards - 2);
        double probOfUnseenPair = probForFirstCardOfUnseenRank * probForSecondCardOfSameRank;

        double expectedProb = probForMultipleRanks + probOfUnseenPair;

        assertEquals(expectedProb, probability, 0.001);
    }
}
