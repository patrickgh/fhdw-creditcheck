package de.puzzles.webapp.components;

import de.puzzles.webapp.page.dashboard.CreditDetailsPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 13.03.13
 */
public class InfoButtonPanel extends Panel {

    public InfoButtonPanel(String id, int requestId) {
        super(id);
        PageParameters params = new PageParameters();
        params.add("requestId",requestId);
        add(new BookmarkablePageLink("link", CreditDetailsPage.class,params));
    }
}
