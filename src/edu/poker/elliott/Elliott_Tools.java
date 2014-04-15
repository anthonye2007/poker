package edu.poker.elliott;

import java.util.*;

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
    public static final int CARDS_IN_POCKET = 2;
    public static int CARDS_IN_DECK = 52;

    public static int HIGH_THRESHOLD = 10;

    public static int getPointsOfHand(int[] pocket, int[] board) {
		return getPointsOfHand(combine(pocket, board));
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
	
	public static int[] combine(int[] A, int[] B) {
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
	
	public static boolean areClose(int a, int b) {
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

        for (int card : cards) {
            if (getRank(card) > 12)
                retVal = true;
        }

        return retVal;
    }

    public static boolean isHighCard(int card) {
        return getRank(card) >= HIGH_THRESHOLD;
    }

    public static boolean isHighPair(int[] pocket) {
        boolean isPair = sameRank(pocket);
        boolean isHigh = isHighCard(pocket[0]);

        return isPair && isHigh;
    }

    public static double probOfStraight(int[] pocket, int[] board) {
        assert(pocket.length == 2);

        int first = pocket[0];
        int second = pocket[1];

        List<Integer> closeToFirst = new ArrayList<>();
        List<Integer> closeToSecond = new ArrayList<>();

        closeToFirst.add(first);
        closeToSecond.add(second);

        addPocketCardsIfClose(first, second, closeToFirst);

        addCloseCards(board, first, closeToFirst);
        addCloseCards(board, second, closeToSecond);

        int numCardsSeen = pocket.length + board.length;
        double probFirst = findProbOfStraight(closeToFirst, numCardsSeen);
        double probSecond = findProbOfStraight(closeToSecond, numCardsSeen);

        return maxProb(probFirst, probSecond);
    }

    private static void addPocketCardsIfClose(int first, int second, List<Integer> list) {
        if (areClose(first, second)) {
            list.add(second);
        }
    }

    private static void addCloseCards(int[] board, int first, List<Integer> closeToFirst) {
        for (int card : board) {
            if (areClose(first, card)) {
                closeToFirst.add(card);
            }
        }
    }

    private static double maxProb(double probFirst, double probSecond) {
        return probFirst >= probSecond ? probFirst : probSecond;
    }

    private static double findProbOfStraight(List<Integer> cards, int numCardsSeen) {
        if (numCardsSeen >= CARDS_PER_HAND + CARDS_IN_POCKET) {
            return doFindProbOfStraight(cards);
        } else if (cards.size() == CARDS_PER_HAND - 1) {
            List<Integer> possibleSuccesses = determinePossibleSuccesses(cards);
            List<Integer> actualSuccesses = determineActualSuccesses(cards, possibleSuccesses);
            Map<Integer, Double> probForCard = calculateProbForEachSuccess(cards, actualSuccesses, numCardsSeen);

            return calculateTotalProbForStraight(probForCard);
        } else {
            // generate all possible combinations
            return 0.0;
        }
    }

    private static double calculateTotalProbForStraight(Map<Integer, Double> probForCard) {
        double totalProb = 0.0;
        for (Double prob : probForCard.values()) {
            totalProb += prob;
        }
        return totalProb;
    }

    private static Map<Integer, Double> calculateProbForEachSuccess(List<Integer> cards, List<Integer> actualSuccesses, int numCardsSeen) {
        Map<Integer, Double> probForCard = new TreeMap<>();
        for (Integer success : actualSuccesses) {
            int numAlreadySeenOfThisRank = 0;
            for (Integer card : cards) {
                if (card.equals(success)) {
                    numAlreadySeenOfThisRank++;
                }
            }

            int numTotalCardsOfThisRank = 4;
            int numCardsOfThisRankNotSeen = numTotalCardsOfThisRank - numAlreadySeenOfThisRank;
            int numRemainingCards = CARDS_IN_DECK - numCardsSeen;

            double probability = numCardsOfThisRankNotSeen / (double) numRemainingCards;
            assert(probability >= 0.0 && probability <= 1.0);

            probForCard.put(success, probability);
        }
        return probForCard;
    }

    private static List<Integer> determineActualSuccesses(List<Integer> cards, List<Integer> possibleSuccesses) {
        List<Integer> actualSuccesses = new LinkedList<>();
        for (Integer possible : possibleSuccesses) {
            List<Integer> possibleCards = new LinkedList<>(cards);
            possibleCards.add(possible);

            if (containsStraight(possibleCards)) {
                actualSuccesses.add(possible);
            }
        }
        return actualSuccesses;
    }

    private static List<Integer> determinePossibleSuccesses(List<Integer> cards) {
        List<Integer> possibleSuccesses = new LinkedList<>();
        for (Integer card : cards) {
            if (getRank(card) <= 12) {
                int higher = card + 1;
                onlyAddIfNotSeen(cards, possibleSuccesses, higher);
            }
            if (getRank(card) >= 1) {
                int lower = card - 1;
                onlyAddIfNotSeen(cards, possibleSuccesses, lower);
            }
        }
        return possibleSuccesses;
    }

    private static void onlyAddIfNotSeen(List<Integer> cards, List<Integer> possibleSuccesses, int cardToAdd) {
        if (!cards.contains(cardToAdd) && !possibleSuccesses.contains(cardToAdd)) {
            possibleSuccesses.add(cardToAdd);
        }
    }

    private static double doFindProbOfStraight(List<Integer> cards) {
        if (containsStraight(cards)) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    public static boolean areAllClose(List<Integer> cards) {
        for (int i = 0; i < cards.size(); i++) {
            for (int j = 0; j < cards.size(); j++) {
                if (i == j)
                    continue;

                if (!areClose(cards.get(i), cards.get(j))) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean containsStraight(List<Integer> cards) {
        int[] array = cardListToArray(cards);
        return EstherTools.containsStraight(array);
    }

    public static int[] cardListToArray(List<Integer> cards) {
        int[] array = new int[cards.size()];

        int i = 0;
        for (Integer card : cards) {
            array[i] = card;
            i++;
        }

        return array;
    }

}
