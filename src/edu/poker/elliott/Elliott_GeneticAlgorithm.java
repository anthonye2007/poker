package edu.poker.elliott;

/**
 * Created by anthonyelliott on 3/12/14.
 */
public class Elliott_GeneticAlgorithm {

    private int fitness(Player player) {
        // TOOD will need to pass in the new agent
        Elliott_Runner runner = new Elliott_Runner(player);
        int score = runner.play();

        return score;
    }
}
