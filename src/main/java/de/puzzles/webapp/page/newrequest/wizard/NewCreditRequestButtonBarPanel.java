package de.puzzles.webapp.page.newrequest.wizard;

import org.apache.wicket.extensions.wizard.FinishButton;
import org.apache.wicket.extensions.wizard.IDefaultButtonProvider;
import org.apache.wicket.extensions.wizard.IWizard;
import org.apache.wicket.extensions.wizard.IWizardModel;
import org.apache.wicket.extensions.wizard.NextButton;
import org.apache.wicket.extensions.wizard.PreviousButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * A panel for the button bar which is used in the NewCreditRequestWizard. We use this instead of the default implementation to style the buttons with css & remove uneccesary buttons (e.g. cancel button).
 *
 * @author Patrick Groß-Holtwick
 *         Date: 08.03.13
 */
public class NewCreditRequestButtonBarPanel extends Panel implements IDefaultButtonProvider {

    private static final long serialVersionUID = 1L;

    public NewCreditRequestButtonBarPanel(final String id, final IWizard wizard) {
        super(id);
        add(new PreviousButton("previous", wizard));

        final NextButton next = new NextButton("next", wizard);
        WebMarkupContainer nextContainer = new WebMarkupContainer("nextContainer") {
            @Override
            public boolean isVisible() {
                return next.isEnabled();
            }
        };
        nextContainer.add(next);
        add(nextContainer);
        final FinishButton finish = new FinishButton("finish", wizard);
        WebMarkupContainer finishContainer = new WebMarkupContainer("finishContainer") {
            @Override
            public boolean isVisible() {
                return finish.isEnabled();
            }
        };
        finishContainer.add(finish);
        add(finishContainer);
    }

    @Override
    public IFormSubmittingComponent getDefaultButton(final IWizardModel model) {
        if (model.isNextAvailable()) {
            return (IFormSubmittingComponent) get("nextContainer").get("next");
        }
        else if (model.isLastStep(model.getActiveStep())) {
            return (IFormSubmittingComponent) get("finishContainer").get("finish");
        }
        return null;
    }
}
