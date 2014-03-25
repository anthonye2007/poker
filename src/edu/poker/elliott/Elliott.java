package edu.poker.elliott;

/**
 * I used a genetic algorithm to determine which action should be taken at each decision point.
 * This agent uses a combination of the relative hand strength and current betting round
 * to choose from a set of possible actions (raise, stay, or fold).  The agent is set up using a 'genome'
 * which instructs the agent which action to take when. This genome can be represented by a simple string
 * of a set of three characters: 'r' for raise, 's' for stay, and 'f' for fold.
 * Currently the genome contains four rounds with four possible hand strengths for a total of 16 genes.
 * Actually, I developed a model in code first and then used a String for easier mutations and readability.
 *
 * The algorithm begins with a population of 100 randomly generated genomes. These genomes are then scored based on
 * their performance in a tournament where the genome is the sixth player against five other players (these
 * players always remain constant to provide a consistent fitness function).  The algorithm uses elitism
 * to automatically pass the top 5% of the genomes into the next generation.  Also, the algorithm randomly picks about
 * 20% of the genomes and randomly picks a few genes to mutate. These new genomes are passed on to the next generation.
 * The algorithm also uses a crossover reproductive strategy in which two genomes combine their genes to create
 * a new genome. About 50% of the next generation is formed from this reproductive manner.
 *
 * After mutations, the algorithm removes any duplicate genomes that it finds.  Finally the algorithm
 * randomly generates as many genomes as needed to ensure the next generation's population size is 100 genomes.
 * Once this is all done, the algorithm passes this new generation into itself and begins again with another
 * generation.
 *
 * Each generation takes about 10 seconds to run when the population size is 100. I ran several thousand generations
 * and the algorithm produced 'rrrsssrssrrsrrrs' as the final genome.  This genome had a score of 1737 in the
 * tournament against five of the agents from the previous submission (Advanced Rules Agent).
 */
public class Elliott extends Player {

    private Elliott_Genome genome;

    public Elliott() {
        // Genome determined via genetic algorithm
        this.genome = new Elliott_Genome("rrrsssrssrrsrrrs");
    }

    public Elliott(Elliott_Genome genome) {
        this.genome = genome;
    }


    @Override
    public String getScreenName() {
        return "Elliott";
    }

    @Override
    public String getAction(TableData data) {
        int round = data.getBettingRound();

        switch (round) {
            case 1: // first round only considers pocket cards
                return takeActionFirstRound(data);
            case 2: // now have 3 cards in board
                return takeActionSecondRound(data);
            case 3: // now have 4 cards in board
                return takeActionThirdRound(data);
            case 4: // now have all 7 cards (board=5 pocket=2)
                return takeActionFourthRound(data);
            default: // should never be reached
                return fold();
        }
    }

    private String takeActionFirstRound(TableData data) {
        int[] pocket = data.getPocket();
        Elliott_HandStrength handStrength;

        if (Elliott_Tools.sameRank(pocket) && Elliott_Tools.sameSuit(pocket)) {
            handStrength = Elliott_HandStrength.GREAT;
        } else if (Elliott_Tools.sameRank(pocket)) {
            handStrength = Elliott_HandStrength.GOOD;
        } else if (Elliott_Tools.sameRank(pocket)
                || Elliott_Tools.sameSuit(pocket)
                || Elliott_Tools.isPossibleStraight(pocket[0], pocket[1])
                || Elliott_Tools.hasHighCard(pocket)) {
            handStrength = Elliott_HandStrength.OK;
        } else {
            handStrength = Elliott_HandStrength.BAD;
        }

        return determineAction(Elliott_BettingRound.FIRST, handStrength, data);
    }

    private String takeActionSecondRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);
        Elliott_HandStrength handStrength;

        if (score >= Elliott_Tools.FULL_HOUSE) {
            handStrength = Elliott_HandStrength.GREAT;
        } else if (score >= Elliott_Tools.THREE_OF_A_KIND) {
            handStrength = Elliott_HandStrength.GOOD;
        } else if (score >= Elliott_Tools.ONE_PAIR) {
            handStrength = Elliott_HandStrength.OK;
        } else {
            handStrength = Elliott_HandStrength.BAD;
        }

        return determineAction(Elliott_BettingRound.SECOND, handStrength, data);
    }

    private String takeActionThirdRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);
        Elliott_HandStrength handStrength;

        if (score >= Elliott_Tools.FULL_HOUSE) {
            handStrength = Elliott_HandStrength.GREAT;
        } else if (score >= Elliott_Tools.TWO_PAIR) {
            handStrength = Elliott_HandStrength.GOOD;
        } else if (score >= Elliott_Tools.ONE_PAIR) {
            handStrength = Elliott_HandStrength.OK;
        } else {
            handStrength = Elliott_HandStrength.BAD;
        }

        return determineAction(Elliott_BettingRound.THIRD, handStrength, data);
    }

    private String takeActionFourthRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);
        Elliott_HandStrength handStrength;

        if (score >= Elliott_Tools.FULL_HOUSE) {
            handStrength = Elliott_HandStrength.GREAT;
        } else if (score >= Elliott_Tools.TWO_PAIR) {
            handStrength = Elliott_HandStrength.GOOD;
        } else if (score >= Elliott_Tools.ONE_PAIR) {
            handStrength = Elliott_HandStrength.OK;
        } else {
            handStrength = Elliott_HandStrength.BAD;
        }

        return determineAction(Elliott_BettingRound.FOURTH, handStrength, data);
    }

    private String determineAction(Elliott_BettingRound round, Elliott_HandStrength handStrength, TableData data) {
        char act = genome.getAction(round, handStrength);
        String action = null;

        if (act == Action.RAISE) {
            action = raise(data);
        } else if (act == Action.STAY) {
            action = stay(data);
        } else if (act == Action.FOLD) {
            action = fold();
        } else {
            System.err.println("Bad action: " + act);
        }

        return action;
    }

    private String stay(TableData data) {
        if (data.getValidActions().contains("call")) {
            return "call";
        } else {
            return "check";
        }
    }

    private String raise(TableData data) {
        String pull = data.getValidActions();
        if (pull.contains("bet")) {
            return "bet";
        } else if (pull.contains("raise")) {
            return "raise";
        } else {
            return "call";
        }
    }

    private String fold() {
        return "fold";
    }
}
