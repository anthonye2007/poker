package edu.poker.elliott;

/*
 * ESTHER represents cards as integers from 0-51. Cards 0-12 are clubs Cards
 * 13-25 are diamonds Cards 26-38 are hearts Cards 39-51 are spades
 *
 * Within each set the cards are ordered from 2 up through Ace
 * */

public class Elliott_AdvancedRules extends Player {
	
	@Override
	public String getScreenName() {
		return "Elliott";
	}

	@Override
	public String getAction(TableData data) {
		int[] pocket = data.getPocket();
		int[] board = data.getBoard();
		int round = data.getBettingRound();
		int score = Elliott_Tools.getPointsOfHand(pocket, board);


		// first round only considers pocket cards
		switch (round) {
		case 1:
			if (Elliott_Tools.sameRank(pocket)
					|| Elliott_Tools.sameSuit(pocket)) {
				return aggressive(data);
			} else if (Elliott_Tools.isPossibleStraight(pocket[0], pocket[1]) || 
					data.getRaisesLeft() >= 2) {
				// a possible straight is a long shot so only stay if no one pushes hard on first round
				return stay(data);
			} else {
				return passive(data);
			}
		case 2: // now have 3 cards in board
			if (score >= Elliott_Tools.THREE_OF_A_KIND) {
				// if near maximum score then aggressive
				return aggressive(data);
			} else if (score >= Elliott_Tools.ONE_PAIR) {
				// if near average score then stay
				return stay(data);
			} else {
				// if below average score then fold
				return passive(data);
			}
		case 3: // now have 4 cards in board
			if (score >= Elliott_Tools.FULL_HOUSE) {
				return aggressive(data);
			} else if (score >= Elliott_Tools.TWO_PAIR) {
				return stay(data);
			} else {
				return passive(data);
			}
		case 4: // now have all 7 cards (board=5 pocket=2)
			int scoreFromBoard = Elliott_Tools.getPointsOfHand(board);

			// ensure my pocket cards are actually worthwhile
			if (score <= scoreFromBoard) {
				return passive(data);
			}

			if (score >= Elliott_Tools.FULL_HOUSE) {
				return aggressive(data);
			} else if (score >= Elliott_Tools.TWO_PAIR) {
				return stay(data);
			} else {
				return passive(data);
			}
		default:
			return passive(data);
		}

	}

	private String stay(TableData data) {
		if (data.getValidActions().contains("call")) {
			return "call";
		} else {
			return "check";
		}
	}

	private String aggressive(TableData data) {
		String pull = data.getValidActions();
		if (pull.contains("bet")) {
			return "bet";
		} else if (pull.contains("raise")) {
			return "raise";
		} else {
			return "call";
		}
	}

	private String passive(TableData data) {
		return "fold";
	}

}
