package edu.poker.elliott;

import java.util.HashMap;
import java.util.Map.Entry;

public class Elliott_Runner {
	
	private Player[] players;
	private int numTournaments;

	public static void main(String[] args) {
		Player[] players = new Player[6];

	    players[0] = new Toben();
	    players[1] = new Dunakey();
	    players[2] = new Graham();
	    players[3] = new kakavas();
	    players[4] = new Nielsen();
		players[5] = new Elliott();
		
		int tournamentsToPlay = 100; 

		Elliott_Runner runner = new Elliott_Runner(players, tournamentsToPlay);
		runner.play();
	}
	
	public Elliott_Runner(Player[] players, int tournamentsToPlay) {
		this.players = players;
		numTournaments = tournamentsToPlay;
	}

	/**
	 * Plays through multiple tournaments and prints out the score of each player.
	 */
	private int play() {
		HashMap<String, Integer> runningTotals = new HashMap<String, Integer>();
 	    // initialize score to zero for all players
		for (Player player : players) {
			runningTotals.put(player.getScreenName(), 0);
	       }
		
		for (int i = 0; i < numTournaments; i++) {
			HashMap<String, Integer> nameToScores = runTournament();
			
			// add scores to running total
			for(Entry<String, Integer> pair : nameToScores.entrySet()) {
				String name = pair.getKey();
				int newScore = pair.getValue();
				int oldScore = runningTotals.get(name);
				
				runningTotals.put(name, oldScore + newScore);
			}
		}
		
		System.out.println("Number tournaments: " + numTournaments);
		System.out.println();

        int myScore = 0;
		
		// average scores
		for (Entry<String, Integer> pair : runningTotals.entrySet()) {
			int score = pair.getValue();
			int average = score / numTournaments;
			String name = pair.getKey();

            if (name.equals("Elliott")) {
                myScore = average;
            }
			
			System.out.println(name + ": " + average);
		}

        return myScore;
	}

	private HashMap<String, Integer> runTournament() {
	   //System.out.println("Beginning another tournament!");
		
       HashMap<String,Integer> outcome = new HashMap<>();
       for (Player player : players) {
    	   // initialize score to zero for all players
           outcome.put(player.getScreenName(), 0);
       }
	   
       for (int x = 0; x < players.length; x++) {
           Dealer dealer = new Dealer(players.length, 123456789);
           GameManager g = new GameManager(players, dealer, false);
           
           int[] scoreAtEndOfGame = g.playGame();
           for (int y = 0; y < scoreAtEndOfGame.length; y++) {
               String name = players[y].getScreenName();
               int oldScore = outcome.get(name);
               outcome.put(name, oldScore + scoreAtEndOfGame[y]);
           }

           Player[] temp = new Player[players.length];
           temp[0] = players[players.length - 1];
           for (int y = 1; y < players.length; y++) {
               temp[y] = players[y - 1];
           }
           players = temp;
       }
       
       return outcome;
	}
}
