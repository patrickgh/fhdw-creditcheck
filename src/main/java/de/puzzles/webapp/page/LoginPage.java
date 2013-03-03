package de.puzzles.webapp.page;

import de.puzzles.core.DatabaseConnector;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.ValidationError;

/**
 * Created with IntelliJ IDEA.
 * @author Patrick Groß-Holtwick
 * Date: 02.03.13
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage extends BasePage {

    public LoginPage() {
        super();
        add(new Link("newcredit") {
            @Override
            public void onClick() {
                setResponsePage(NewCreditRequestPage.class);
            }
        });

        final TextField<String> userField = new TextField<String>("user", new Model<String>());
        userField.setRequired(false);
        final PasswordTextField passwordField = new PasswordTextField("password", new Model<String>());
        passwordField.setRequired(false);

        final Form loginForm = new Form("loginform");
        loginForm.add(userField);
        loginForm.add(passwordField);
        loginForm.add(new AjaxSubmitLink("submit"){
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                String user = userField.getModelObject();
                String password = passwordField.getModelObject();
                Integer userId = DatabaseConnector.getInstance().checkLogin(user, password);
                if (userId != null) {
                    getSession().setAttribute("userId", userId);
                    if (getSession().isTemporary()) { getSession().bind(); }
                    setResponsePage(DashboardPage.class);
                } else {
                    target.appendJavaScript("shake();");
                }
            }
        });
        final WebMarkupContainer loginContainer = new WebMarkupContainer("login") {
            @Override
            public boolean isVisible() {
                return getSession().getAttribute("userId") == null;
            }
        };
        loginContainer.add(loginForm);
        add(loginContainer);

        final WebMarkupContainer dashboardContainer = new WebMarkupContainer("dashboard") {
            @Override
            public boolean isVisible() {
                return !loginContainer.isVisible();
            }
        };
        dashboardContainer.add(new Link("dashboardLink"){
            @Override
            public void onClick() {
                setResponsePage(DashboardPage.class);
            }
        });
        add(dashboardContainer);
    }
}
