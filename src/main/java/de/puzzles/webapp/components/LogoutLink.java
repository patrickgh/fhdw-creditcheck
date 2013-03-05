package de.puzzles.webapp.components;

import de.puzzles.webapp.page.RequiresLoginPage;
import org.apache.wicket.markup.html.link.Link;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 03.03.13
 *         Time: 15:26
 *         To change this template use File | Settings | File Templates.
 */
public class LogoutLink extends Link {

    public LogoutLink(String id) {
        super(id);
    }

    @Override
    public void onClick() {
        getSession().setAttribute("userId", null);
        if(getPage() instanceof RequiresLoginPage) {
            setResponsePage(getApplication().getHomePage());
        }
    }

    @Override
    public boolean isVisible() {
        return getSession().getAttribute("userId") != null;
    }
}
