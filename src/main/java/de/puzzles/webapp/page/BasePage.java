package de.puzzles.webapp.page;

import de.puzzles.webapp.components.LogoutLink;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;
import webresources.ImportResourceLocator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 25.01.13
 *         Time: 14:06
 *         To change this template use File | Settings | File Templates.
 */
public abstract class BasePage extends WebPage implements IAjaxIndicatorAware {

    public BasePage() {
        DebugBar debugBar = new DebugBar("debugbar");
        debugBar.setOutputMarkupId(true);
        debugBar.setVisible(getApplication().getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT);
        add(debugBar);
        WebMarkupContainer globalAjaxIndicator = new WebMarkupContainer("globalAjaxIndicator");
        globalAjaxIndicator.setOutputMarkupId(true);
        add(globalAjaxIndicator);
        add(new LogoutLink("logoutLink"));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(new PackageResourceReference(ImportResourceLocator.class, "css/bootstrap.css")));
        response.render(CssHeaderItem.forReference(new PackageResourceReference(ImportResourceLocator.class, "css/puzzles.css")));

        response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(ImportResourceLocator.class, "js/bootstrap.js")));
        response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(ImportResourceLocator.class, "js/jquery-ui-1.10.1.custom.js")));
        response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(ImportResourceLocator.class, "js/puzzles.js")));
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return "globalAjaxIndicator";
    }
}