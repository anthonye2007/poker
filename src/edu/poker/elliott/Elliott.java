package edu.poker.elliott;

/*
 * ESTHER represents cards as integers from 0-51. Cards 0-12 are clubs Cards
 * 13-25 are diamonds Cards 26-38 are hearts Cards 39-51 are spades
 *
 * Within each set the cards are ordered from 2 up through Ace
 * */

public class Elliott extends Player {

    @Override
    public String getScreenName() {
        return "Elliott";
    }

    @Override
    public String getAction(TableData data) {
        // first round only considers pocket cards
        switch (data.getBettingRound()) {
            case 1:
                return roundOne(data);
            case 2: // now have 3 cards in board
                return roundTwo(data);
            case 3: // now have 4 cards in board
                return roundThree(data);
            case 4: // now have all 7 cards (board=5 pocket=2)
                return roundFour(data);
            default:
                return passive(data);
        }

    }

    private String roundFour(TableData data) {
        double[] probabilities = calculateProbabilities(data);
        int[] weights = new int[] {1, 2, 3, 5, 8, 13, 21, 34};
        int bias = 2;
        double weightedSum = -bias;

        for (int i = 0; i < probabilities.length - 1; i++) {
            weightedSum += probabilities[i] * weights[i];
        }
        //System.out.println(String.format("%.2f", weightedSum));

        if (weightedSum > 2) {
            return aggressive(data);
        } else if (weightedSum > 0) {
            return stay(data);
        } else {
            return passive(data);
        }
    }

    private String roundThree(TableData data) {
        double[] probabilities = calculateProbabilities(data);
        int[] weights = new int[] {1, 2, 3, 5, 8, 13, 21, 34};
        int bias = 2;
        double weightedSum = -bias;

        for (int i = 0; i < probabilities.length - 1; i++) {
            weightedSum += probabilities[i] * weights[i];
        }
        //System.out.println(String.format("%.2f", weightedSum));

        if (weightedSum > 2) {
            return aggressive(data);
        } else if (weightedSum > 0) {
            return stay(data);
        } else {
            return passive(data);
        }
    }

    private String roundTwo(TableData data) {
        double[] probabilities = calculateProbabilities(data);
        int[] weights = new int[] {1, 2, 3, 5, 8, 13, 21, 34};
        int bias = 2;
        double weightedSum = -bias;

        for (int i = 0; i < probabilities.length - 1; i++) {
            weightedSum += probabilities[i] * weights[i];
        }
        //System.out.println(String.format("%.2f", weightedSum));

        if (weightedSum > 2) {
            return aggressive(data);
        } else if (weightedSum > 0) {
            return stay(data);
        } else {
            return passive(data);
        }
    }

    private String roundOne(TableData data) {
        int[] pocket = data.getPocket();

        for (int card : pocket) {
            System.out.print(card + " ");
        }

        double[] probabilities = calculateProbabilities(data);
        int[] weights = new int[] {1, 2, 3, 5, 8, 13, 21, 34};
        int bias = 0;
        double weightedSum = -bias;

        for (int i = 0; i < probabilities.length - 1; i++) {
            if (i == 0) {
                System.out.println(String.format("%.5f", probabilities[i]));
            }
            weightedSum += probabilities[i] * weights[i];
        }
        //System.out.println(String.format("%.5f", weightedSum));

        if (weightedSum > 2) {
            return aggressive(data);
        } else if (weightedSum > 0) {
            return stay(data);
        } else {
            return passive(data);
        }
    }

    private double[] calculateProbabilities(TableData data) {
        double[] probabilities = new double[Elliott_Tools.NUM_POSSIBLE_HANDS];

        int[] pocket = data.getPocket();
        int[] board = data.getBoard();

        probabilities[0] = Elliott_Tools.probOfOnePair(pocket, board);
        probabilities[1] = Elliott_Tools.probOfTwoPair(pocket, board);
        probabilities[2] = Elliott_Tools.probOfThreeOfAKind(pocket, board);
        probabilities[3] = Elliott_Tools.probOfStraight(pocket, board);
        probabilities[4] = Elliott_Tools.probOfFlush(pocket, board);
        probabilities[5] = Elliott_Tools.probOfFullHouse(pocket, board);
        probabilities[6] = Elliott_Tools.probOfFourOfAKind(pocket, board);
        probabilities[7] = Elliott_Tools.probOfStraightFlush(pocket, board);

        return probabilities;
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

    private String passive(TableData data) {
        return "fold";
    }

}
