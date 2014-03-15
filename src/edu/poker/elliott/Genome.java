package edu.poker.elliott;

import java.util.Map;
import java.util.TreeMap;

/**
 * Used by a genetic algorithm to determine which action is best to take at which time.
 * Genome currently allows for two factors: round of betting and relative hand strength.
 */
public class Genome {

    /**
     * This must be a TreeMap to preserve the natural ordering in order to print a consistent toString.
     */
    private TreeMap<BettingRound, Map<HandStrength, Character>> genome;

    public Genome() {
        genome = new TreeMap<>();

        for (BettingRound round : BettingRound.values()) {
            Map<HandStrength, Character> handToAction = new TreeMap<>();

            for (HandStrength hand : HandStrength.values()) {
                char c = Action.randomAction();

                handToAction.put(hand, c);
            }

            genome.put(round, handToAction);
        }
    }

    public Genome(String genomeStr) {
        genome = new TreeMap<>();

        int i = 0;
        for (BettingRound round : BettingRound.values()) {
            Map<HandStrength, Character> handToAction = new TreeMap<>();

            for (HandStrength hand : HandStrength.values()) {
                handToAction.put(hand, genomeStr.charAt(i));
                i++;
            }

            genome.put(round, handToAction);
        }
    }

    public Character getAction(BettingRound round, HandStrength hand) {
        return genome.get(round).get(hand);
    }

    public static int numGenes() {
        return BettingRound.values().length * HandStrength.values().length * Action.numActions();
    }

    public String toString() {
        char[] chars = new char[numGenes()];

        int i = 0;
        for (Map.Entry<BettingRound, Map<HandStrength, Character>> round : genome.entrySet()) {
            for (Map.Entry<HandStrength, Character> hand : round.getValue().entrySet()) {
                chars[i] = hand.getValue();
                i++;
            }
        }

        return new String(chars);
    }

    public boolean equals(Genome other) {
        return toString().equals(other.toString());
    }

}
