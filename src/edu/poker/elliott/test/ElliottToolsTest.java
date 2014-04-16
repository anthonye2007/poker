package edu.poker.elliott.test;

import edu.poker.elliott.Elliott_Tools;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElliottToolsTest {

	@Test
	public void testSameRank() {
		assertTrue(Elliott_Tools.sameRank(0, 0));
		assertTrue(Elliott_Tools.sameRank(0, 13));
		assertFalse(Elliott_Tools.sameRank(0,1));
		
		assertTrue(Elliott_Tools.sameRank(new int[] {0,0}));
	}
		
	@Test
	public void testSameSuit() {
		assertTrue(Elliott_Tools.sameSuit(0, 0));
		assertTrue(Elliott_Tools.sameSuit(0, 12));
		assertFalse(Elliott_Tools.sameSuit(0,13));
		
		assertTrue(Elliott_Tools.sameSuit(new int[] {0,0}));
	}

    @Test
    public void testShouldContainPair() {
        int[] pocket = {0, 0};
        int[] board = {1, 2, 3};

        assertEquals(true, Elliott_Tools.containsPair(pocket, board));
    }

    @Test
    public void testShouldContainPairWhenBoardEmpty() {
        int[] pocket = {0, 13};
        int[] board = {};

        assertEquals(true, Elliott_Tools.containsPair(pocket, board));
    }

    @Test
    public void testShouldNotContainPairWhenBoardEmpty() {
        int[] pocket = {0, 1};
        int[] board = {};

        assertEquals(false, Elliott_Tools.containsPair(pocket, board));
    }

    @Test
    public void testShouldContainThree() {
        int[] pocket = {0, 13};
        int[] board = {13*2, 2, 3};

        assertEquals(true, Elliott_Tools.containsThreeOfAKind(pocket, board));
    }

    @Test
    public void testShouldNotContainThree() {
        int[] pocket = {0, 0};
        int[] board = {1, 2, 3};

        assertEquals(false, Elliott_Tools.containsThreeOfAKind(pocket, board));
    }

}
