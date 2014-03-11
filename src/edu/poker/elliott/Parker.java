package edu.poker.elliott;

public class Parker extends Player {

	/**
     * Returns a string of no more than length eight which is the player name
     * displayed on the graphical representation of the game if used
     *
     * @return String
     */
	@Override
    public String getScreenName() {
        return "Parker";
    }

 
    /**
     * The main AI of your agent.
     *
     * It takes in an instance of the TableData class as a parameter which
     * indicates the current state of the hand/game.
     *
     * It then expects your next move as a String from the set check, bet,
     * raise, call, fold
     *
     * Not all options are legal at all times. Illegal actions will
     * automatically be treated as a "fold"
     *
     * @param data a TableData instance passed to you by the ESTHER server
     * @return String your action
     */
    @Override
    public String getAction(TableData data) {
    	char num = EstherTools.intCardToStringCard(data.getPocket()[0]).charAt(0);
    	
	    	if (num == EstherTools.intCardToStringCard(data.getPocket()[1]).charAt(0)){
	    		
	    		if (EstherTools.containsFullHouse(data.getPocket(), data.getBoard())){
	    			if(data.getValidActions().contains("raise")){
		            	return "raise";
		    		}
	    		}
	        	if (EstherTools.containsThreeOfAKind(data.getPocket(), data.getBoard())){
		    		if(data.getValidActions().contains("raise")){
		            	return "raise";
		    		}
	        	}
	        	
	        	if (EstherTools.containsTwoPair(data.getPocket(), data.getBoard())){
		    		if(data.getValidActions().contains("bet")){
		            	return "bet";
		    		}
	        	}
	        	
	            if (data.getValidActions().contains("bet")){
	        		return "bet";
	        	}
	            else if (data.getValidActions().contains("call")){
	        		return "call";
	            }
	            else{ System.out.println("check"); return "check";}

	    	}
    
    	return "fold";
    	
    }	
}
