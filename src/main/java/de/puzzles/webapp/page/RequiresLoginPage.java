package de.puzzles.webapp.page;

/**
 * Abstract page which requires a login. Every page which extends this page is only accessible when the user is logged in. If the user is not logged in he gets redirected to the login page.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 02.03.13
 */
public abstract class RequiresLoginPage extends BasePage {

    public RequiresLoginPage() {
        super();
        if (getSession().getAttribute("userId") == null) {
            setResponsePage(getApplication().getHomePage());
        }
    }

    protected int getUserId() {
        Integer id = (Integer) getSession().getAttribute("userId");
        if (id != null) {
            return id;
        }
        return 0;
    }
}
