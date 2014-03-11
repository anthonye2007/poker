package edu.poker.elliott;

import java.lang.Math;

public class kakavas extends Player {
	


	private int[] hand, board, suit;
	private int handRarity;

	@Override
	public String getScreenName() {
		return "abacon";
	}

	@Override
	public String getAction(TableData data) {
		hand = data.getPocket();
		board = data.getBoard();
		suit = new int[4];
		int round = data.getBettingRound();
		if	(round == 1) {
			handRarity = checkPocket();
		} else if (round == 2) {
			handRarity =checkFlop();
		} else if (round == 3) {
			handRarity = checkTurn();
		} else {
			handRarity = checkRiver();
		}
		return actionForRarity(data);
	}
	
	private int checkPocket() {
		int cardOne = hand[0];
		int cardTwo = hand[1];
		int cardSuit;
		cardSuit = getSuit(cardOne);
		suit[cardSuit] = suit[cardSuit] + 1; 
		cardSuit = getSuit(cardTwo);
		suit[cardSuit] = suit[cardSuit] + 1; 
		
		if (cardOne % 13 == cardTwo % 13) {
			return 5;
		} else if ((int)cardOne/13 == (int)cardTwo/13 ) {
			return 8;
		} else if(Math.abs(cardOne - cardTwo) < 5) {
			return 4;
		}
		return 3;
	}
	
	private int checkFlop() {
		int cardSuit;
		for (int i = 0; i < board.length; i++) {
			int c = board[i];
			cardSuit = getSuit(c);
			suit[cardSuit] = suit[cardSuit] + 1;
		}
		
		return handRarity();            
	}
	
	private int checkTurn() {
		int cardSuit;
		int c = board[3];
		cardSuit = getSuit(c);
		suit[cardSuit] = suit[cardSuit] + 1;
		
		return handRarity();
	}
	
	private int checkRiver() {
		int cardSuit;
		int c = board[4];
		cardSuit = getSuit(c);
		suit[cardSuit] = suit[cardSuit] + 1;
		
		return handRarity();
	}
	
	private int checkPairs() {
		if (EstherTools.containsFourOfAKind(hand, board)) {
			return 4;
		} else if (EstherTools.containsThreeOfAKind(hand, board)) {
			return 3;
		} else if (EstherTools.containsTwoPair(hand, board)) {
			return 2;
		} else if (EstherTools.containsOnePair(hand,board)) {
			return 1;
		}
		return 0;
	}
	
	private Boolean checkStraight() {
		if (EstherTools.containsStraight(hand, board)) {
			return true;
		}
		return false;
	}
	
	private Boolean checkFullHouse() {
		if (EstherTools.containsFullHouse(hand, board)) {
			return true;
		}
		return false;
	}
	
	private Boolean checkFlush() {
		for (int i = 0; i < 4; i++) {
			if (suit[i] == 5) {
				return true;
			}
		}
		return false;
	}
	
	private int getSuit(int card) {
		return (int)card/13;
	}
	
	private int handRarity() {
		if (checkFullHouse()) {
			return 7;
		}
		
		int pairs = checkPairs();
		
		if (pairs > 3) {
			return 8;
		} else if (checkFlush()) {
			return 6;
		} else if (checkStraight()) {
			return 5;
		} else if (pairs > 2) {
			return 4;
		} else if (pairs > 1) {
			return 3;
		} else if (pairs > 0){
			return 2;
		} else {
			return 1;
		}
	}
	
	private String actionForRarity (TableData data) {
		if (data.getBettingRound() == 1) {
			if (handRarity >= 4) {
				return raise(data);
			} else {
				return checkOrFold(data);
			}
		} else if (data.getBettingRound() == 2) {
			if (handRarity >= 4) {
				return raise(data);
			} else if (handRarity == 3) {
				return betOrCall(data);
			} else if (handRarity == 2) {
				return checkOrCall(data);
			} else {
				return checkOrFold(data);
			}
		} else if (data.getBettingRound() == 3) {
			if (handRarity >= 6) {
				return raise(data);
			} else if (handRarity >= 4) {
				return betOrCall(data);
			} else if (handRarity > 2) {
				return checkOrCall(data);
			}
		} else {
			if (handRarity >= 7) {
				return raise(data);
			} else if (handRarity >= 4) {
				return betOrCall(data);
			} else if (handRarity > 2) {
				return checkOrCall(data);
			}
		}
		return "fold";
	}
	private String raise(TableData data) {

		if (data.getValidActions().contains("bet")) {
			return "bet";
		} else if (data.getValidActions().contains("raise")) {
			return "raise";
		} else if (data.getValidActions().contains("call")) {
			return "call";
		}
		return "fold";
	}
	
	private String betOrCall(TableData data) {
		if (data.getValidActions().contains("bet")) {
			return "bet";
		} else if (data.getValidActions().contains("call")) {
			return "call";
		}
		return "fold";
	}
	
	private String checkOrCall(TableData data) { 
		if (data.getValidActions().contains("check")) {
			return "check";
		} else if (data.getValidActions().contains("call")) {
			return "call";
		}
		return "fold";
	}
	
	private String checkOrFold(TableData data) { 
			if (data.getValidActions().contains("check")) {
				return "check";
			}
		return "fold";
	}
}
