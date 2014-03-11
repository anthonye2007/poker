package edu.poker.elliott;

/*
 * ESTHER
 * The Educational Simulated Texas Hold Em Room
 */

import java.lang.Integer;
import java.lang.NumberFormatException;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;

/**
 * @author Zach Toben
 */

public class Toben extends Player {

    @Override
    public String getScreenName() {
        return "Tobenator";
    }

    @Override
    public String getAction(TableData data) {
        //Case statement for round
        int round = data.getBoard().length;
        switch (round) {
            case 0:
                // Round 1
                return (checkHand(data, 0));
            case 1:
                // Round 2
                return (checkHand(data, 1));
            case 2:
                // Round 3
                return (checkHand(data, 2));
            case 3:
                // Round 4
                return (checkHand(data, 3));
            default:
                // Default case
                return (checkHand(data, 4));
        }
    }

    public String checkHand(TableData data, int round) {
        String pull = data.getValidActions();
        int[] hand = data.getPocket();
        int[] board = data.getBoard();
        String pocket1 = EstherTools.intCardToStringCard(hand[0]);
        String pocket2 = EstherTools.intCardToStringCard(hand[1]);

        if (round == 0) {
            // ROUND 1
            if (pull.contains("check")) {
                return "check";
            } else {
                // If pocket pair, raise
                if (pocket1.substring(0, 1).equals(pocket2.substring(0, 1))) {
                    if (pull.contains("raise")) {
                        return "raise";
                    } else if (pull.contains("call")) {
                        return "call";
                    }
                }
                // If pocket cards are same suit, call
                if (pocket1.substring(1, 2).equals(pocket2.substring(1, 2))) {
                    if (pull.contains("call")) {
                        return "call";
                    }
                }
                // If pocket values are lower than 6, fold
                try {
                    int pocketVal1 = Integer.parseInt(pocket1.substring(0, 1));
                    int pocketVal2 = Integer.parseInt(pocket2.substring(0, 1));
                    if (pocketVal1 < 7) {
                        if (pocketVal2 < 7) {
                            return "fold";
                        }
                    }
                } catch (NumberFormatException e) {
                    if (pull.contains("call")) {
                        return "call";
                    }
                }
                //Fold if no other options
                return "fold";
            }
        } else if (round == 1) {
            // ROUND 2
            // Last round to fold
            String board1 = EstherTools.intCardToStringCard(board[0]);
            if (pull.contains("check")) {
                return "check";
            } else {
                // If three of a kind, raise
                if (EstherTools.containsThreeOfAKind(hand, board)) {
                    if (pull.contains("raise")) {
                        return "raise";
                    }
                    if (pull.contains("call")) {
                        return "call";
                    }
                }
                // If all same suit, raise
                if (pocket1.substring(1, 2).equals(pocket2.substring(1, 2))) {
                    if (pocket2.substring(1, 2).equals(board1.substring(1, 2))) {
                        if (pull.contains("raise")) {
                            return "raise";
                        }
                    } else {
                        if (pull.contains("call")) {
                            return "call";
                        }
                    }
                } else if (pocket2.substring(1, 2).equals(board1.substring(1, 2))) {
                    if (pull.contains("call")) {
                        return "call";
                    }
                }
                // If pair, raise
                if (EstherTools.containsOnePair(hand, board)) {
                    if (pull.contains("raise")) {
                        return "raise";
                    }
                    if (pull.contains("call")) {
                        return "call";
                    }
                }
                // If all less than 9, fold
                try {
                    int pocketVal1 = Integer.parseInt(pocket1.substring(0, 1));
                    int pocketVal2 = Integer.parseInt(pocket2.substring(0, 1));
                    int boardVal1 = Integer.parseInt(board1.substring(0, 1));
                    if (pocketVal1 < 9) {
                        if (pocketVal2 < 9) {
                            if (boardVal1 < 9) {
                                return "fold";
                            }
                        }
                    }
                    if (pull.contains("call")) {
                        return "call";
                    }
                } catch (NumberFormatException e) {
                    if (pull.contains("call")) {
                        return "call";
                    }
                }
                // Fold if no other options
                return "fold";
            }

        } else if (round == 2) {
            // ROUND 3
            String board1 = EstherTools.intCardToStringCard(board[0]);
            String board2 = EstherTools.intCardToStringCard(board[0]);
            if (pull.contains("check")) {
                return "check";
            } else {

                // Check 4 of a kind
                if (EstherTools.containsFourOfAKind(hand, board)) {
                    if (pull.contains("raise")) {
                        return "raise";
                    }
                    if (pull.contains("call")) {
                        return "call";
                    }
                }
                // Check 3 of a kind
                if (EstherTools.containsThreeOfAKind(hand, board)) {
                    if (pull.contains("raise")) {
                        return "raise";
                    }
                    if (pull.contains("call")) {
                        return "call";
                    }
                }
                // Check 2 pair
                if (EstherTools.containsTwoPair(hand, board)) {
                    if (pull.contains("raise")) {
                        return "raise";
                    }
                    if (pull.contains("call")) {
                        return "call";
                    }
                }
                // Check pair
                if (EstherTools.containsOnePair(hand, board)) {
                    if (pull.contains("call")) {
                        return "call";
                    }
                }
                //Check all same suit
                if (pocket1.substring(1, 2).equals(pocket2.substring(1, 2))) {
                    if (pocket2.substring(1, 2).equals(board1.substring(1, 2))) {
                        if (board2.substring(1, 2).equals(board1.substring(1, 2))) {
                            if (pull.contains("raise")) {
                                return "raise";
                            }
                        } else {
                            if (pull.contains("call")) {
                                return "call";
                            }
                        }
                    }
                }
                return "call";
            }

        } else if (round == 3) {
            // ROUND 4
            if (pull.contains("check")) {
                return "check";
            } else {
                // Check 4 of a kind
                if (EstherTools.containsFourOfAKind(hand, board)) {
                    if (pull.contains("raise")) {
                        return "raise";
                    }
                }
                // Check full house
                if (EstherTools.containsFullHouse(hand, board)) {
                    if (pull.contains("raise")) {
                        return "raise";
                    }
                }
                // Check flush
                if (EstherTools.containsFlush(hand, board)) {
                    if (pull.contains("raise")) {
                        return "raise";
                    }
                }
                // Check 3 of a kind
                if (EstherTools.containsThreeOfAKind(hand, board)) {
                    if (pull.contains("raise")) {
                        return "raise";
                    }
                }
                // Check 2 pair
                if (EstherTools.containsTwoPair(hand, board)) {
                    if (pull.contains("raise")) {
                        return "raise";
                    }
                }
                if (pull.contains("call")) {
                    return "call";
                }
            }
        } else {
            //DEFAULT
            if (pull.contains("check")) {
                return "check";
            } else {
                return "call";
            }
        }
        //Fold if no other options
        if (pull.contains("call")) {
            return "call";
        } else {
            return "fold";
        }
    }
}