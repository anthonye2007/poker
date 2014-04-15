package edu.poker.elliott.test;

import edu.poker.elliott.Elliott_Tools;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProbStraightTest {

    @Test
    public void testOneDifferentSuit() {
        int[] pocket = {0, 14};
        int[] board = {15, 16, 18, 19};
        double probability = Elliott_Tools.probOfStraight(pocket, board);

        int possibleWaysToGetAFour = 4;
        int remainingCardsInDeck = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double expectedProb = possibleWaysToGetAFour / (double) remainingCardsInDeck;
        //System.out.println("Prob of one unseen rank with four cards on board: " + expectedProb);
        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testManyDifferentSuits() {
        int[] pocket = {0, 14};
        int[] board = {28, 16, 31, 45};
        double probability = Elliott_Tools.probOfStraight(pocket, board);

        int possibleWaysToGetAFour = 4;
        int remainingCardsInDeck = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double expectedProb = possibleWaysToGetAFour / (double) remainingCardsInDeck;
        //System.out.println("Prob of one unseen rank with four cards on board: " + expectedProb);
        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testHaveFourLowCards() {
        int[] pocket = {0, 1};
        int[] board = {2, 3, 5, 6};
        double probability = Elliott_Tools.probOfStraight(pocket, board);

        int possibleWaysToGetAFour = 4;
        int remainingCardsInDeck = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double expectedProb = possibleWaysToGetAFour / (double) remainingCardsInDeck;
        //System.out.println("Prob of one unseen rank with four cards on board: " + expectedProb);
        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testTwoPossibleCards() {
        int[] pocket = {1, 2};
        int[] board = {3, 4, 10, 11};
        double probability = Elliott_Tools.probOfStraight(pocket, board);

        int possibleWaysAtHighEnd = 4;
        int possibleWaysAtLowEnd = 4;
        int totalPossibleWays = possibleWaysAtHighEnd + possibleWaysAtLowEnd;

        int remainingCardsInDeck = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double expectedProb = totalPossibleWays / (double) remainingCardsInDeck;
        //System.out.println("Prob of one of two unseen ranks with four cards on board: " + expectedProb);
        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void testOneCardRepeated() {
        int[] pocket = {0, 1};
        int[] board = {2, 3, 5, 2};
        double probability = Elliott_Tools.probOfStraight(pocket, board);

        int possibleWaysToGetAFour = 4;
        int remainingCardsInDeck = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double expectedProb = possibleWaysToGetAFour / (double) remainingCardsInDeck;
        assertEquals(expectedProb, probability, 0.001);
    }

    @Test
    public void calculateProbAtFlopWithOnePossibility() {
        // just need a four
        int[] pocket = {0, 1};
        int[] board = {2, 3, 5};
        double probability = Elliott_Tools.probOfStraight(pocket, board);

        int possibleWaysToGetAFour = 4;
        int remainingCardsInDeck = Elliott_Tools.CARDS_IN_DECK - pocket.length - board.length;
        double probForFour = possibleWaysToGetAFour / (double) remainingCardsInDeck;

        assertEquals(probForFour, probability, 0.001);
    }

    @Test
    public void NoPossibleStraightWithFourCards() {
        int[] pocket = {0, 12};
        int[] board = {1, 2, 10, 11};

        double probability = Elliott_Tools.probOfStraight(pocket, board);

        assertEquals(0, probability, 0.001);
    }

    @Test
    public void NoPossibleStraightWithThreeCards() {
        int[] pocket = {0, 12};
        int[] board = {1, 5, 11};

        double probability = Elliott_Tools.probOfStraight(pocket, board);

        assertEquals(0, probability, 0.001);
    }

    @Test
    public void doesHaveStraight() {
        int[] pocket = {0, 1};
        int[] board = {2, 3, 4, 5, 6};
        double probability = Elliott_Tools.probOfStraight(pocket, board);

        assertEquals(1.0, probability, 0.001);
    }

    @Test
    public void doesNotHaveStraight() {
        int[] pocket = {1, 2};
        int[] board = {3, 4, 10, 11, 12};

        double probability = Elliott_Tools.probOfStraight(pocket, board);
        assertEquals(0.0, probability, 0.001);
    }

    @Test
    public void notAllAreClose() {
        List<Integer> cards = new ArrayList<>();
        cards.add(1);
        cards.add(2);
        cards.add(3);
        cards.add(5);
        cards.add(9);

        assertEquals(false, Elliott_Tools.areAllClose(cards));
    }

    @Test
    public void allAreClose() {
        List<Integer> cards = new ArrayList<>();
        cards.add(1);
        cards.add(2);
        cards.add(3);
        cards.add(4);
        cards.add(5);

        assertEquals(true, Elliott_Tools.areAllClose(cards));
    }
}
