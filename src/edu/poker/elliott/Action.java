package edu.poker.elliott;

import java.util.Random;

/**
 * Created by anthonyelliott on 3/14/14.
 */
public class Action {

    public static final char RAISE = 'r';
    public static final char STAY = 's';
    public static final char FOLD = 'f';

    public static int numActions() {
        return 3;
    }

    public static char randomAction() {
        Random rand = new Random();
        int randomInt = rand.nextInt(numActions());

        char retVal;

        if (randomInt == 0) {
            retVal = RAISE;
        } else if (randomInt == 1) {
            retVal = STAY;
        } else {
            retVal = FOLD;
        }

        return retVal;
    }
}
