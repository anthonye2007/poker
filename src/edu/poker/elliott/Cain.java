package edu.poker.elliott;

public class Cain extends Player {
	
	String actions;

	public Cain() {
	}

	@Override
	public String getScreenName() {
		return "Cain IV"; //felt it necessary to add in my Roman numerals :P
	}
	
	public static String passiveBetting(TableData data) {
		if (data.getValidActions().contains("check")) return "check";
		else return "call";
	}
	
	public static String aggressiveBetting(TableData data) {
		if (data.getValidActions().contains("bet")) return "bet";
		else if (data.getValidActions().contains("raise")) return "raise";
		else return "call";
	}
	
	public static String getSuite(int card) {
		int suit = card / 13;
		switch(suit) {
		case (0): return "C";
		case (1): return "D";
		case (2): return "H";
		default: return "S";
		}
	}
	
	public static String preFlopStrategy(TableData data) {
		int playerHand[] = data.getPocket();
		int pocketCard1 = playerHand[0] % 13;
		int pocketCard2 = playerHand[1] % 13;
		String card1Suit = getSuite(playerHand[0]);
		String card2Suit = getSuite(playerHand[1]);
		
		
		if(pocketCard1 > 9 && pocketCard2 > 9) return aggressiveBetting(data);
		else if (pocketCard1 == pocketCard2) return passiveBetting(data);
		else if ((pocketCard1 == 13 | pocketCard2 == 13) && (pocketCard1 > 9 && pocketCard2 > 9)) return aggressiveBetting(data);
		else if ((card1Suit == card2Suit) && (pocketCard1 > 8 && pocketCard2 > 8)) return passiveBetting(data);
		return "fold";
	}

	@Override
	public String getAction(TableData data) {
		int[] playerHand;
		
		actions = data.getValidActions();
		
		playerHand = data.getPocket();
		if (((playerHand[0] % 13) > 9 && (playerHand[1] % 13) > 9) && data.getBettingRound() == 1){
			return aggressiveBetting(data);
		}
		if (data.getBettingRound() == 1) return preFlopStrategy(data);
		
		int bestHandCombo = EstherTools.getBestHand(playerHand, data.getBoard()).getCombo();
		if (bestHandCombo > 3) return aggressiveBetting(data);
		if (bestHandCombo > 1) return passiveBetting(data);
		if (data.getValidActions().contains("check")) return "check";
		return "fold";
	}

}
