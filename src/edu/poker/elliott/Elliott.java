package edu.poker.elliott;

/**
 * This class can be run in a genetic algorithm to determine the 'optimal' strategy.
 *
 * Created by anthonyelliott on 3/12/14.
 */
public class Elliott extends Player {
    /**
     * Scale of 0 (always fold) to 10 (always raise)
     */
    private int aggressiveness;

    public Elliott() {
        this.aggressiveness = 5;
    }

    public Elliott(int aggressiveness) {
        this.aggressiveness = aggressiveness;
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
        String action = null; // if this may fail, then fail hard

        switch (aggressiveness) {
            case 10: // ultra aggressive
                if (Elliott_Tools.sameRank(pocket)
                        || Elliott_Tools.sameSuit(pocket)
                        || Elliott_Tools.isPossibleStraight(pocket[0], pocket[1])
                        || Elliott_Tools.hasHighCard(pocket)) {
                    action = raise(data);
                } else {
                    action = fold();
                }
                break;
            case 7:case 8:case 9: // aggressive
                if (Elliott_Tools.sameRank(pocket) || Elliott_Tools.sameSuit(pocket)) {
                    action = raise(data);
                } else {
                    action = fold();
                }
                break;
            case 4:case 5:case 6: // middle
                action = stay(data);
                break;
            case 1:case 2:case 3: // passive
                if (Elliott_Tools.sameRank(pocket) && Elliott_Tools.sameSuit(pocket)) {
                    action = raise(data);
                } else {
                    action = fold();
                }
                break;
            case 0: // ultra passive
                action = fold();
                break;
            default:
                action = stay(data);
        }

        return action;
    }

    private String takeActionSecondRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);

        if (score >= Elliott_Tools.THREE_OF_A_KIND) {
            // if near maximum score then raise
            return raise(data);
        } else if (score >= Elliott_Tools.ONE_PAIR) {
            // if near average score then stay
            return stay(data);
        } else {
            // if below average score then fold
            return fold();
        }
    }

    private String takeActionThirdRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);

        if (score >= Elliott_Tools.FULL_HOUSE) {
            return raise(data);
        } else if (score >= Elliott_Tools.TWO_PAIR) {
            return stay(data);
        } else {
            return fold();
        }
    }

    private String takeActionFourthRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);
        int scoreFromBoard = Elliott_Tools.getPointsOfHand(board);

        // ensure my pocket cards are actually worthwhile
        if (score <= scoreFromBoard) {
            return fold();
        }

        if (score >= Elliott_Tools.FULL_HOUSE) {
            return raise(data);
        } else if (score >= Elliott_Tools.TWO_PAIR) {
            return stay(data);
        } else {
            return fold();
        }
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
