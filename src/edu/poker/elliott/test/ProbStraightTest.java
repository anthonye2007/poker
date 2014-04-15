package edu.poker.elliott.test;

import edu.poker.elliott.Elliott_Tools;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProbStraightTest {

    @Test
    public void doesHaveStraight() {
        int[] pocket = {0, 1};
        int[] board = {2, 3, 4, 5, 6};
        double probability = Elliott_Tools.probOfStraight(pocket, board);

        assertEquals(1.0, probability, 0.001);
    }

    @Test
    public void doNotHaveStraight() {
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
