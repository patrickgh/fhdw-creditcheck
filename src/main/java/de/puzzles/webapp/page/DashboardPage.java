package de.puzzles.webapp.page;

import org.apache.wicket.markup.html.basic.Label;

/**
 * Created with IntelliJ IDEA.
 * @author Patrick Groß-Holtwick
 * Date: 02.03.13
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class DashboardPage extends RequiresLoginPage {

    public DashboardPage() {
        super();
        add(new Label("id", getSession().getAttribute("userId")));
    }
}
