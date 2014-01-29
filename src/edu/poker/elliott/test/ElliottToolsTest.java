package edu.poker.elliott.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.poker.elliott.Elliott_Tools;

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

}
