package de.puzzles.webapp;

import de.puzzles.webapp.page.LoginPage;
import de.puzzles.webapp.page.dashboard.DashboardPage;
import org.apache.wicket.util.tester.WicketTester;
import org.testng.annotations.Test;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 06.03.13
 */
public class WicketTest {

    private WicketTester tester;

    public WicketTest() {
        tester = new WicketTester(new PuzzlesApplication());

    }

    @Test
    public void testApplication() {
        tester.startPage(LoginPage.class);
        tester.assertNoErrorMessage();
        tester.assertRenderedPage(LoginPage.class);


        tester.startPage(DashboardPage.class);
        tester.assertRenderedPage(LoginPage.class);
    }
}
