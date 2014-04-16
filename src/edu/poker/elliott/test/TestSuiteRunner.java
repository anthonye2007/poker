package edu.poker.elliott.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;

@RunWith(AllTests.class)
public class TestSuiteRunner {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();

        suite.addTest(new JUnit4TestAdapter(ElliottToolsTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbFlushTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbFourOfAKindTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbFullHouseTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbPairTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbStraightTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbStraightFlushTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbThreeOfAKindTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbTwoPairTest.class));

        return suite;
    }
}
