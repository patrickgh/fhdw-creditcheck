package de.puzzles.webapp.page.newrequest.wizard;

import de.puzzles.core.domain.CreditRequest;
import de.puzzles.webapp.page.newrequest.steps.EarningsStep;
import de.puzzles.webapp.page.newrequest.steps.InsuranceStep;
import de.puzzles.webapp.page.newrequest.steps.OverviewStep;
import de.puzzles.webapp.page.newrequest.steps.PersonalInformationStep;
import de.puzzles.webapp.page.newrequest.steps.SpendingsStep;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.extensions.wizard.WizardModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 06.03.13
 */
public class NewCreditRequestWizard extends Wizard {

    public NewCreditRequestWizard(String id) {
        super(id, false);
        IModel<CreditRequest> model = new CompoundPropertyModel<CreditRequest>(new CreditRequest());
        setDefaultModel(model);
        WizardModel wizardModel = new WizardModel();
        wizardModel.add(new PersonalInformationStep(model));
        wizardModel.add(new EarningsStep());
        wizardModel.add(new SpendingsStep());
        wizardModel.add(new InsuranceStep());
        wizardModel.add(new OverviewStep());
        init(wizardModel);
    }

}
