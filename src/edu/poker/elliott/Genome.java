package edu.poker.elliott;

import java.util.HashMap;
import java.util.Map;

/**
 * Used by a genetic algorithm to determine which action is best to take at which time.
 * Genome currently allows for two factors: round of betting and relative hand strength.
 */
public class Genome {

    private Map<BettingRound, Map<HandStrength, Character>> genome;

    public Genome() {
        genome = new HashMap<>(BettingRound.values().length);

        for (BettingRound round : BettingRound.values()) {
            Map<HandStrength, Character> handToAction = new HashMap<>(HandStrength.values().length);

            for (HandStrength hand : HandStrength.values()) {
                handToAction.put(hand, Action.randomAction());
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

}
