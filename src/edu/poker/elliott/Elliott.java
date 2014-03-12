package edu.poker.elliott;

/**
 * Created by anthonyelliott on 3/12/14.
 */
public class Elliott extends Player {
    /**
     * Scale of 0 (always fold) to 10 (always raise)
     */
    private int aggressiveness = 10;

    public Elliott() {
        this.aggressiveness = 10;
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
                return "fold";
        }
    }

    private String stay(TableData data) {
        if (data.getValidActions().contains("call")) {
            return "call";
        } else {
            return "check";
        }
    }

    private String aggressive(TableData data) {
        String pull = data.getValidActions();
        if (pull.contains("bet")) {
            return "bet";
        } else if (pull.contains("raise")) {
            return "raise";
        } else {
            return "call";
        }
    }

    private String takeActionFirstRound(TableData data) {
        int[] pocket = data.getPocket();

        if (Elliott_Tools.sameRank(pocket)
                || Elliott_Tools.sameSuit(pocket)) {
            return aggressive(data);
        } else if (Elliott_Tools.isPossibleStraight(pocket[0], pocket[1]) ||
                data.getRaisesLeft() >= 2) {
            // a possible straight is a long shot so only stay if no one pushes hard on first round
            return stay(data);
        } else {
            return passive(data);
        }
    }

    private String takeActionSecondRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);

        if (score >= Elliott_Tools.THREE_OF_A_KIND) {
            // if near maximum score then aggressive
            return aggressive(data);
        } else if (score >= Elliott_Tools.ONE_PAIR) {
            // if near average score then stay
            return stay(data);
        } else {
            // if below average score then fold
            return passive(data);
        }
    }

    private String takeActionThirdRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);

        if (score >= Elliott_Tools.FULL_HOUSE) {
            return aggressive(data);
        } else if (score >= Elliott_Tools.TWO_PAIR) {
            return stay(data);
        } else {
            return passive(data);
        }
    }

    private String takeActionFourthRound(TableData data) {
        int[] pocket = data.getPocket();
        int[] board = data.getBoard();
        int score = Elliott_Tools.getPointsOfHand(pocket, board);
        int scoreFromBoard = Elliott_Tools.getPointsOfHand(board);

        // ensure my pocket cards are actually worthwhile
        if (score <= scoreFromBoard) {
            return passive(data);
        }

        if (score >= Elliott_Tools.FULL_HOUSE) {
            return aggressive(data);
        } else if (score >= Elliott_Tools.TWO_PAIR) {
            return stay(data);
        } else {
            return passive(data);
        }
    }

    private String passive(TableData data) {
        return "fold";
    }
}
