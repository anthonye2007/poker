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
        suite.addTest(new JUnit4TestAdapter(ProbFlush.class));
        suite.addTest(new JUnit4TestAdapter(ProbPairTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbStraightTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbThreeOfAKind.class));
        suite.addTest(new JUnit4TestAdapter(ProbTwoPairTest.class));
        suite.addTest(new JUnit4TestAdapter(ProbFullHouse.class));

        return suite;
    }
}
