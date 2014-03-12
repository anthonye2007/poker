package edu.poker.elliott;

public class Elliott_Tools {
	
	public static final int NO_PAIR = 1;
	public static final int ONE_PAIR = 2;
	public static final int TWO_PAIR = 3;
	public static final int THREE_OF_A_KIND = 4;
	public static final int STRAIGHT = 5;
	public static final int FLUSH = 6;
	public static final int FULL_HOUSE = 7;
	public static final int FOUR_OF_A_KIND = 8;
	public static final int STRAIGHT_FLUSH = 9;
	
	public static final int CARDS_PER_HAND = 5;
	
	public static int getPointsOfHand(int[] pocket, int[] board) {
		return getPointsOfHand(concat(pocket, board));
	}
	
	public static int getPointsOfHand(int[] allCards) {
		if (EstherTools.containsStraightFlush(allCards)) {
			return STRAIGHT_FLUSH;
		} else if (EstherTools.containsFourOfAKind(allCards)) {
			return FOUR_OF_A_KIND;
		} else if (EstherTools.containsFullHouse(allCards)) {
			return FULL_HOUSE;
		} else if (EstherTools.containsFlush(allCards)) {
			return FLUSH;
		} else if (EstherTools.containsStraight(allCards)) {
			return STRAIGHT;
		} else if (EstherTools.containsThreeOfAKind(allCards)) {
			return THREE_OF_A_KIND;
		} else if (EstherTools.containsTwoPair(allCards)) {
			return TWO_PAIR;
		} else if (EstherTools.containsOnePair(allCards)) {
			return ONE_PAIR;
		} else {
			return NO_PAIR;
		}
	}
	
	public static int[] concat(int[] A, int[] B) {
		   int aLen = A.length;
		   int bLen = B.length;
		   int[] C = new int[aLen+bLen];
		   System.arraycopy(A, 0, C, 0, aLen);
		   System.arraycopy(B, 0, C, aLen, bLen);
		   return C;
		}
	
	public static boolean sameRank(int[] pocket) {
		return sameRank(pocket[0], pocket[1]);
	}
	
	public static boolean sameRank(int a, int b) {
		return a % 13 == b % 13;
	}
	
	public static boolean sameSuit(int[] pocket) {
		return sameSuit(pocket[0], pocket[1]);
	}
	
	public static boolean sameSuit(int a, int b) {
		return getSuit(a) == getSuit(b);
	}
	
	public static boolean isPossibleStraight(int a, int b) {
		boolean isClose = false;
		
		int diff = Math.abs(getRank(a) - getRank(b));
		
		if (diff <= CARDS_PER_HAND - 1) {
			isClose = true;
		}
		
		return isClose;
	}
	
	public static int getSuit(int card) {
		return card / 13;
	}
	
	public static int getRank(int card) {
		return card % 13;
	}

    public static boolean hasHighCard(int[] cards) {
        boolean retVal = false;

        for (int i = 0; i < cards.length; i++) {
            if (getRank(cards[i]) > 12)
                retVal = true;
        }

        return retVal;
    }
}
