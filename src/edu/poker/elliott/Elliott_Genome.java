package edu.poker.elliott;

import java.util.Map;
import java.util.TreeMap;

/**
 * Used by a genetic algorithm to determine which action is best to take at which time.
 * Genome currently allows for two factors: round of betting and relative hand strength.
 */
public class Elliott_Genome {

    /**
     * This must be a TreeMap to preserve the natural ordering in order to print a consistent toString.
     */
    private TreeMap<Elliott_BettingRound, Map<Elliott_HandStrength, Character>> genome;

    public Elliott_Genome() {
        genome = new TreeMap<>();

        for (Elliott_BettingRound round : Elliott_BettingRound.values()) {
            Map<Elliott_HandStrength, Character> handToAction = new TreeMap<>();

            for (Elliott_HandStrength hand : Elliott_HandStrength.values()) {
                char c = Action.randomAction();

                handToAction.put(hand, c);
            }

            genome.put(round, handToAction);
        }
    }

    public Elliott_Genome(String genomeStr) {
        genome = new TreeMap<>();

        int i = 0;
        for (Elliott_BettingRound round : Elliott_BettingRound.values()) {
            Map<Elliott_HandStrength, Character> handToAction = new TreeMap<>();

            for (Elliott_HandStrength hand : Elliott_HandStrength.values()) {
                handToAction.put(hand, genomeStr.charAt(i));
                i++;
            }

            genome.put(round, handToAction);
        }
    }

    public Character getAction(Elliott_BettingRound round, Elliott_HandStrength hand) {
        return genome.get(round).get(hand);
    }

    public static int numGenes() {
        return Elliott_BettingRound.values().length * Elliott_HandStrength.values().length * Action.numActions();
    }

    public String toString() {
        char[] chars = new char[numGenes()];

        int i = 0;
        for (Map.Entry<Elliott_BettingRound, Map<Elliott_HandStrength, Character>> round : genome.entrySet()) {
            for (Map.Entry<Elliott_HandStrength, Character> hand : round.getValue().entrySet()) {
                chars[i] = hand.getValue();
                i++;
            }
        }

        return new String(chars);
    }

    public boolean equals(Elliott_Genome other) {
        return toString().equals(other.toString());
    }

}
