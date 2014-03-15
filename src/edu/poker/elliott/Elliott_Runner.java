package edu.poker.elliott;

import java.util.HashMap;
import java.util.Map.Entry;

public class Elliott_Runner {

    public static void main(String[] args) {
        /*for (int i = 0; i <= 10; i++) {
            Elliott me = new Elliott(i);
            Elliott_Runner runner = new Elliott_Runner(me);
            int score = runner.play();
            System.out.println("Score for " + i + ": " + score);
        }*/
    }
	
	private Player[] players;
	private int numTournaments;
    private boolean shouldPrint = false;

    public Elliott_Runner(Player player) {
        Player[] players = new Player[6];

        players[0] = new Toben();
        players[1] = new Dunakey();
        players[2] = new Graham();
        players[3] = new kakavas();
        players[4] = new Nielsen();
        players[5] = player;

        this.players = players;
        this.numTournaments = 100;
    }

	public Elliott_Runner(Player[] players, int tournamentsToPlay, boolean shouldPrint) {
		this.players = players;
		this.numTournaments = tournamentsToPlay;
        this.shouldPrint = shouldPrint;
	}

    public Elliott_Runner(Player[] players, int tournamentsToPlay) {
        this(players, tournamentsToPlay, false);
    }

    public Elliott_Runner() {
        this(false);
    }

    public Elliott_Runner(boolean shouldPrint) {
        Player[] players = new Player[6];

        players[0] = new Toben();
        players[1] = new Dunakey();
        players[2] = new Graham();
        players[3] = new kakavas();
        players[4] = new Nielsen();
        players[5] = new Elliott_AdvancedRules();

        this.players = players;
        this.numTournaments = 100;
        this.shouldPrint = shouldPrint;
    }

    public void setNumTournaments(int numTournaments) {
        this.numTournaments = numTournaments;
    }

    public void setShouldPrint(boolean shouldPrint) {
        this.shouldPrint = shouldPrint;
    }

	/**
	 * Plays through multiple tournaments and prints out the score of each player.
	 */
    public int play() {
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
		
		print("Number tournaments: " + numTournaments + "\n");

        int myScore = 0;
		
		// average scores
		for (Entry<String, Integer> pair : runningTotals.entrySet()) {
			int score = pair.getValue();
			int average = score / numTournaments;
			String name = pair.getKey();

            if (name.equals("Elliott")) {
                myScore = average;
            }

            print(name + ": " + average);
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

    public void print(String msg) {
        if (this.shouldPrint)
            System.out.println(msg);
    }
}
