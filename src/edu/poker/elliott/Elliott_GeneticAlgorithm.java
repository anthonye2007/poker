package edu.poker.elliott;

/**
 * Created by anthonyelliott on 3/12/14.
 */
public class Elliott_GeneticAlgorithm {

    public static void main(String[] args) {
        Elliott_GeneticAlgorithm runner = new Elliott_GeneticAlgorithm();
        runner.run();
    }

    private void run() {
        for (int i = 0; i <= 10; i++) {
            Elliott player = new Elliott(i);
            int score = fitness(player);
            System.out.println("Score for " + i + ": " + score);
        }
    }

    private int fitness(Player player) {
        Elliott_Runner runner = new Elliott_Runner(player);
        runner.setNumTournaments(5);
        int score = runner.play();

        return score;
    }
}
