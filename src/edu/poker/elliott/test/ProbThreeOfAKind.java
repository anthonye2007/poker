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

    @Test
    public void testExistsInBoardOfThree() {
        int[] pocket = {0, 12};
        int[] board = {1, 0, 0};

        double probability = Elliott_Tools.probOfThreeOfAKind(pocket, board);

        assertEquals(1, probability, 0.001);
    }

    @Test
    public void testCannotExistInBoardOfFour() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 2, 3};

        double probability = Elliott_Tools.probOfThreeOfAKind(pocket, board);

        assertEquals(0, probability, 0.001);
    }

    @Test
    public void testOneOpenSlotWithPair() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 0, 2};

        double probability = Elliott_Tools.probOfThreeOfAKind(pocket, board);

        // must be a 0
        int numRemainingOfEachRank = 2;
        int numPossibleRanks = 1;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probForOneRank = numRemainingOfEachRank / (double) remainingCards;
        double expectedProb = probForOneRank * numPossibleRanks;

        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testTwoOpenSlotsWithNoPair() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 11};

        double probability = Elliott_Tools.probOfThreeOfAKind(pocket, board);

        // could be a pair of any number already seen
        int numRemainingOfEachRank = 3;
        int numPossibleRanks = 5;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probForFirstCard = (numRemainingOfEachRank * numPossibleRanks) / (double) remainingCards;
        double probForSecondCard = (numRemainingOfEachRank - 1) / (double) (remainingCards - 1);

        double expectedProb = probForFirstCard * probForSecondCard;
        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testTwoOpenSlotsWithPair() {
        int[] pocket = {0, 12};
        int[] board = {1, 10, 12};

        double probability = Elliott_Tools.probOfThreeOfAKind(pocket, board);

        // could be a pair of any number already seen except the existing pair
        // or either of the two open slots could be fulfill the current pair
        int numRemainingOfEachRank = 3;
        int numPossibleRanks = 4;
        int remainingCards = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probForFirstCard = (numRemainingOfEachRank * numPossibleRanks) / (double) remainingCards;
        double probForSecondCard = (numRemainingOfEachRank - 1) / (double) (remainingCards - 1);
        double probForPair = probForFirstCard * probForSecondCard;

        int numRemainingOfRankInExistingPair = 2;
        double probForNextCardOfExistingPair = numRemainingOfRankInExistingPair / (double) remainingCards;
        int numOpenSlots = 2;
        double probForRightCardToAppearInTwoSlots = probForNextCardOfExistingPair * numOpenSlots;

        double expectedProb = probForPair + probForRightCardToAppearInTwoSlots;
        assertEquals(expectedProb, probability, 0.001);
    }
}
