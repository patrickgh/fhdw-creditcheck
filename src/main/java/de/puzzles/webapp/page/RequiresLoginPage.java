package de.puzzles.webapp.page;

/**
 * Created with IntelliJ IDEA.
 * @author Patrick Groß-Holtwick
 * Date: 02.03.13
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class RequiresLoginPage extends BasePage {

    public RequiresLoginPage() {
        super();
        if(getSession().getAttribute("userId") == null) {
            setResponsePage(getApplication().getHomePage());
        }
    }
}
