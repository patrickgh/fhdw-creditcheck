package de.puzzles.webapp;

import de.puzzles.webapp.page.LoginPage;
import de.puzzles.webapp.page.dashboard.CreditDetailsPage;
import de.puzzles.webapp.page.dashboard.DashboardPage;
import de.puzzles.webapp.page.newrequest.NewCreditRequestPage;
import org.apache.wicket.util.tester.WicketTester;
import org.testng.annotations.Test;

/**
 * Test-Class for the Wicket-Tester tests.
 * @author Patrick Groß-Holtwick
 */
public class WicketTest {

    private WicketTester tester;

    /**
     * constructor which creates a Wicket-Tester instance for the PuzzlesApplication
     */
    public WicketTest() {
        tester = new WicketTester(new PuzzlesApplication());
    }

    /**
     * simple test which loads every page, checks if there were no errors and validates the rendered page.
     */
    @Test
    public void testApplication() {
        tester.startPage(LoginPage.class);
        tester.assertNoErrorMessage();
        tester.assertRenderedPage(LoginPage.class);

        tester.startPage(DashboardPage.class);
        tester.assertNoErrorMessage();
        tester.assertRenderedPage(LoginPage.class); //expect LoginPage, because the user is not logged in

        tester.startPage(CreditDetailsPage.class);
        tester.assertNoErrorMessage();
        tester.assertRenderedPage(LoginPage.class); //expect LoginPage, because the user is not logged in

        tester.startPage(NewCreditRequestPage.class);
        tester.assertNoErrorMessage();
        tester.assertRenderedPage(NewCreditRequestPage.class);
    }
}
