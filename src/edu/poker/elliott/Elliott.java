package edu.poker.elliott;

/*
* ESTHER represents cards as integers from 0-51. Cards 0-12 are clubs Cards
* 13-25 are diamonds Cards 26-38 are hearts Cards 39-51 are spades
*
* Within each set the cards are ordered from 2 up through Ace
* */

public class Elliott extends Player {

	@Override
	public String getScreenName() {
		return "Elliott";
	}

	@Override
	public String getAction(TableData data) {
		int[] pocket = data.getPocket();
		
		if (Elliott_Tools.sameRank(pocket) || 
				Elliott_Tools.sameSuit(pocket) || 
				Elliott_Tools.isPossibleStraight(pocket[0], pocket[1])) {
			 return aggressive(data);
		} else {
			return passive(data);
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
