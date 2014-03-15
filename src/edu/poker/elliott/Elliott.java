package edu.poker.elliott;

/**
 * This class can be run in a genetic algorithm to determine the 'optimal' strategy.
 *
 * Created by anthonyelliott on 3/12/14.
 */
public class Elliott extends Player {

    private Genome genome;

    public Elliott() {

    }

    public Elliott(Genome genome) {
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
        HandStrength handStrength;

        if (Elliott_Tools.sameRank(pocket) && Elliott_Tools.sameSuit(pocket)) {
            handStrength = HandStrength.GREAT;
        } else if (Elliott_Tools.sameRank(pocket)) {
            handStrength = HandStrength.GOOD;
        } else if (Elliott_Tools.sameRank(pocket)
                || Elliott_Tools.sameSuit(pocket)
                || Elliott_Tools.isPossibleStraight(pocket[0], pocket[1])
                || Elliott_Tools.hasHighCard(pocket)) {
            handStrength = HandStrength.OK;
        } else {
            handStrength = HandStrength.BAD;
        }

        return determineAction(BettingRound.FIRST, handStrength, data);
    }

    private String takeActionSecondRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);
        HandStrength handStrength;

        if (score >= Elliott_Tools.FULL_HOUSE) {
            handStrength = HandStrength.GREAT;
        } else if (score >= Elliott_Tools.THREE_OF_A_KIND) {
            handStrength = HandStrength.GOOD;
        } else if (score >= Elliott_Tools.ONE_PAIR) {
            handStrength = HandStrength.OK;
        } else {
            handStrength = HandStrength.BAD;
        }

        return determineAction(BettingRound.SECOND, handStrength, data);
    }

    private String takeActionThirdRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);
        HandStrength handStrength;

        if (score >= Elliott_Tools.FULL_HOUSE) {
            handStrength = HandStrength.GREAT;
        } else if (score >= Elliott_Tools.TWO_PAIR) {
            handStrength = HandStrength.GOOD;
        } else if (score >= Elliott_Tools.ONE_PAIR) {
            handStrength = HandStrength.OK;
        } else {
            handStrength = HandStrength.BAD;
        }

        return determineAction(BettingRound.THIRD, handStrength, data);
    }

    private String takeActionFourthRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);
        HandStrength handStrength;

        if (score >= Elliott_Tools.FULL_HOUSE) {
            handStrength = HandStrength.GREAT;
        } else if (score >= Elliott_Tools.TWO_PAIR) {
            handStrength = HandStrength.GOOD;
        } else if (score >= Elliott_Tools.ONE_PAIR) {
            handStrength = HandStrength.OK;
        } else {
            handStrength = HandStrength.BAD;
        }

        return determineAction(BettingRound.FOURTH, handStrength, data);
    }

    private String determineAction(BettingRound round, HandStrength handStrength, TableData data) {
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
