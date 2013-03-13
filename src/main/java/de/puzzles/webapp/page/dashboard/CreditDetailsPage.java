package de.puzzles.webapp.page.dashboard;

import de.puzzles.core.DatabaseConnector;
import de.puzzles.core.domain.CreditRequest;
import de.puzzles.core.domain.CreditState;
import de.puzzles.core.util.PuzzlesUtils;
import de.puzzles.webapp.page.RequiresLoginPage;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 13.03.13
 */
public class CreditDetailsPage extends RequiresLoginPage {

    CreditRequest request;

    public CreditDetailsPage() {
        super();
        String reqId = String.valueOf(RequestCycle.get().getRequest().getQueryParameters().getParameterValue("requestId"));
        final Integer requestId = PuzzlesUtils.parseInt(reqId);
        if (requestId != null) {
            request = DatabaseConnector.getInstance().getCreditRequestById(Integer.valueOf(reqId));
            if (request.getConsultantId() != getUserId()) {
                setResponsePage(DashboardPage.class);
            }
            else {
                final CompoundPropertyModel<CreditRequest> model = new CompoundPropertyModel<CreditRequest>(request);
                add(new TextField<String>("name", model.<String>bind("customer.lastname")));
                add(new TextField<String>("firstname", model.<String>bind("customer.firstname")));
                add(new TextField<String>("state", model.<String>bind("state")));
                add(new TextField<String>("interest", model.<String>bind("repaymentPlan.interest")));
                add(new TextField<String>("amount", model.<String>bind("repaymentPlan.amount")));
                add(new TextField<String>("duration", model.<String>bind("repaymentPlan.duration")));
                add(new TextField<String>("rate", model.<String>bind("repaymentPlan.rate")));
                WebMarkupContainer buttonContainer = new WebMarkupContainer("buttonContainer"){
                    @Override
                    public boolean isVisible() {
                        return model.getObject().getState() == CreditState.PENDING;
                    }
                };
                Button acceptButton = new Button("acceptButton");
                acceptButton.add(new AjaxEventBehavior("onclick") {
                    @Override
                    protected void onEvent(AjaxRequestTarget target) {
                        DatabaseConnector.getInstance().changeRequestState(requestId, CreditState.ACCEPTED);
                        setResponsePage(DashboardPage.class);
                    }
                });
                buttonContainer.add(acceptButton);

                Button declineButton = new Button("declineButton");
                declineButton.add(new AjaxEventBehavior("onclick") {
                    @Override
                    protected void onEvent(AjaxRequestTarget target) {
                        DatabaseConnector.getInstance().changeRequestState(requestId, CreditState.REJECTED);
                        setResponsePage(DashboardPage.class);
                    }
                });
                buttonContainer.add(declineButton);
                add(buttonContainer);
            }
        }
        else {
            setResponsePage(DashboardPage.class);
        }
    }
}
