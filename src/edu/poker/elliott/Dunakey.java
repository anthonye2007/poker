package edu.poker.elliott;

import java.util.*;
import java.util.Map.Entry;

public class Dunakey extends Player {
	
	public Dunakey(){
	}

	//Returns userName string of Dunakey.
	@Override
	public String getScreenName(){
		String userName = "Dunakey";
		return userName;
	}


	//Advanced rule set for second Poker Agent.
	@Override
	public String getAction(TableData data) {
		//Variables
		int[] board = data.getBoard();
		int[] pocket  = data.getPocket();
		int[] allCards = EstherTools.merge(pocket, board);
		int card1 = pocket[0];
		int card2 = pocket[1];
		String value = CF(data);
		
		//handDebugger(data);
		
		//Betting Round 1; Deal
		if(data.getBettingRound() == 1){
			//Pocket cards or flush chase
			if(((card1 % 13) ==  (card2 % 13)) || flushChase(data) >= 2){
				value = CC(data);
			}
			else{
				return CF(data);
			}
		}
	
		//Betting Round 2; Flop
		else if(data.getBettingRound() == 2){
			//Hand >= two pair or 4 or more cards with same suit
			if((rateHand(data) >= 2) || (flushChase(data) >= 4)){
				value  = BRC(data);
			}
			//3 cards with same suit
			else if(flushChase(data)  == 3){
				value = CC(data);
			}
			else{
				value = CF(data);
			}
		}
		
		//Betting Round 3; The Turn
		else if(data.getBettingRound() == 3){
			//Hand >= three of a kind or 5 or more cards with same suit
			if(rateHand(data) >= 3 || flushChase(data) >= 5){
				value = BRC(data);
			}
			//Hand >= two pair or 4 or more cards with same suit
			else if(rateHand(data) >= 2 || flushChase(data) >= 4){
				value = CC(data);
			}
			else{
				value = CF(data);
			}	
		}
		
		//Betting Round 4; The River
		else if(data.getBettingRound() == 4){
			//Hand >= three of a kind or 5 or more cards with same suit
			if(rateHand(data) >= 3 || flushChase(data) >= 5){
				value = BRC(data);
			}
			//Hand >= two pair
			else if(rateHand(data) >= 2){
				value = CC(data);
			}
			else{
				value = CF(data);
			}
		}
		return value;
	}
	
	//Bet-Raise-Call actions
	public String BRC(TableData data){
		//Variables
		String pull = data.getValidActions();
		
		if(pull.contains("bet")){
			return "bet";
		}
		else if(pull.contains("raise")){
			return "raise";
		}
		else{
			return "call";
		}
	}
	
	//Check-Call actions
	public String CC(TableData data){
		//Variables
		String pull = data.getValidActions();
		
		if(pull.contains("check")){
			return "check";
		}
		else{
			return "call";
		}
	}
	
	//Check-Fold actions
	public String CF(TableData data){
		//Variables
		String pull = data.getValidActions();
		
		if(pull.contains("check")){
			return "check";
		}
		else{
			return "fold";
		}
	}

	
	//Checks cards for possible future flush
	public int flushChase(TableData data){
		//Variables
		int[] board = data.getBoard();
		int[] pocket  = data.getPocket();
		int[] allCards = EstherTools.merge(pocket, board);
		int flushChase = 0;
		List<Integer> myList = new ArrayList<Integer>();
		
		for(int i: allCards){
			myList.add(i / 13);
		}
		
		for(int x : allCards){
			if((x / 13) == findMode(myList)){
				flushChase += 1;
			}
		}
		return flushChase;
	}
	
	//Returns most common suit in allCards. 
	//If two suits have a equal number of same suited cards this will return the lower integer value of the suits.
	public static <T> T findMode(List<T> list) {
		//Variables
	    Map<T, Integer> map = new HashMap<>();
	    
	    for (T i : list){
	        Integer val = map.get(i);
	        map.put(i, val == null ? 1 : val + 1);
	    }

	    Entry<T, Integer> mode = null;

	    for (Entry<T, Integer> x : map.entrySet()){
	        if (mode == null || x.getValue() > mode.getValue())
	            mode = x;
	    }
	    return mode.getKey();
	}
	
	//Prints Hand data for debugging purposes.
	public void handDebugger(TableData data){
		
		//Variables
		int[] board = data.getBoard();
		int[] pocket  = data.getPocket();
		int[] allCards = EstherTools.merge(pocket, board);
		int card1 = pocket[0];
		int card2 = pocket[1];
		
		if(data.getBettingRound() ==  4){
			System.out.println("Hand: " + data.getHandsPlayed());
			System.out.println("Betting Round: " + data.getBettingRound());
			System.out.println("Pocket Cards: " + EstherTools.intCardToStringCard(card1) + " " + EstherTools.intCardToStringCard(card2));
			System.out.print("Board Cards: ");
			for(int i  : board){
				System.out.print(EstherTools.intCardToStringCard(i) + " ");
			}
			System.out.println();
			System.out.print("All Cards: ");
			for(int i : allCards){
				System.out.print(EstherTools.intCardToStringCard(i) + " ");
			}
			System.out.println();
			System.out.println("rateHand: " + rateHand(data));
			System.out.println("flushChase: " + flushChase(data));
			System.out.println();
		}
	}
	
	//Returns numeric value of how strong the hand is.
	public int rateHand(TableData data){
		
		//Variables
		int[] board = data.getBoard();
		int[] pocket  = data.getPocket();
		int[] allCards = EstherTools.merge(pocket, board);
		int rank = 0;
		
		//No Pairs
		if(DunakeyTools.containsNoPair(allCards)  == true){
			rank = 0;
		}
		//One Pair
		if(DunakeyTools.containsOnePair(allCards) == true){
			rank = 1;
		}
		//Two Pair
		if(DunakeyTools.containsTwoPair(allCards) == true){
			rank = 2;
		}
		//Three of a Kind
		if(DunakeyTools.containsThreeOfAKind(allCards) == true){
			rank = 3;
		}
		//Straight
		if(DunakeyTools.containsStraight(allCards) == true){
			rank = 4;
		}
		//Flush
		if(DunakeyTools.containsFlush(allCards) == true){
			rank = 5;
		}
		//Full House
		if(DunakeyTools.containsFullHouse(allCards) == true){
			rank = 6;
		}
		//Four of a Kind
		if(DunakeyTools.containsFourOfAKind(allCards) == true){
			rank = 7;
		}
		//Straight Flush
		if(DunakeyTools.containsStraightFlush(allCards) == true){
			rank = 8;
		}
		return rank;
	}
	
}

