package de.puzzles.webapp.page.newrequest;

import de.puzzles.webapp.page.BasePage;
import de.puzzles.webapp.page.newrequest.steps.ConfirmationStep;
import de.puzzles.webapp.page.newrequest.steps.EarningsStep;
import de.puzzles.webapp.page.newrequest.steps.InsuranceStep;
import de.puzzles.webapp.page.newrequest.steps.OverviewStep;
import de.puzzles.webapp.page.newrequest.steps.PersonalInformationStep;
import de.puzzles.webapp.page.newrequest.steps.SpendingsStep;
import de.puzzles.webapp.page.newrequest.wizard.NewCreditRequestWizard;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.wizard.IWizardStep;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 02.03.13
 *         Time: 14:20
 *         To change this template use File | Settings | File Templates.
 */
public class NewCreditRequestPage extends BasePage {

    WebMarkupContainer progressBar = new WebMarkupContainer("progress");

    public NewCreditRequestPage() {
        super();

        updateProgressBar(20);

        add(progressBar);
        add(new NewCreditRequestWizard("content") {
            @Override
            public void onActiveStepChanged(IWizardStep newStep) {
                super.onActiveStepChanged(newStep);
                int percent = 0;
                if (newStep instanceof PersonalInformationStep) { percent = 20; }
                if (newStep instanceof EarningsStep) { percent = 40; }
                if (newStep instanceof SpendingsStep) { percent = 60; }
                if (newStep instanceof InsuranceStep) { percent = 80; }
                if (newStep instanceof OverviewStep) { percent = 99; }
                if (newStep instanceof ConfirmationStep) {percent = 100;}
                updateProgressBar(percent);
            }
        });
    }

    public void updateProgressBar(Integer percent) {
        progressBar.add(new AttributeModifier("style", "width: " + percent + "%"));
        AjaxRequestTarget target = getRequestCycle().find(AjaxRequestTarget.class);
        if (target != null) {
            target.add(progressBar);
        }
    }

    @Override
    public String getTitle() {
        return "Neuer Kreditantrag";
    }
}
