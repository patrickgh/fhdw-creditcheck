package de.puzzles.webapp.page.newrequest.wizard;

import de.puzzles.core.DatabaseConnector;
import de.puzzles.core.domain.CreditRequest;
import de.puzzles.core.domain.Transaction;
import de.puzzles.webapp.page.newrequest.wizard.steps.ConfirmationStep;
import de.puzzles.webapp.page.newrequest.wizard.steps.EarningsStep;
import de.puzzles.webapp.page.newrequest.wizard.steps.InsuranceStep;
import de.puzzles.webapp.page.newrequest.wizard.steps.OverviewStep;
import de.puzzles.webapp.page.newrequest.wizard.steps.PersonalInformationStep;
import de.puzzles.webapp.page.newrequest.wizard.steps.SpendingsStep;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.extensions.wizard.WizardModel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 06.03.13
 */
public class NewCreditRequestWizard extends Wizard {

    private EarningsStep earningsStep;
    private SpendingsStep spendingsStep;
    private InsuranceStep insuranceStep;
    private IModel<CreditRequest> model;
    private boolean submitted = false;

    public NewCreditRequestWizard(String id) {
        super(id, false);
        model = new CompoundPropertyModel<CreditRequest>(new CreditRequest());
        model.getObject().getRepaymentPlan().setInterest(DatabaseConnector.getInstance().getBaseInterest());
        setDefaultModel(model);
        WizardModel wizardModel = new WizardModel();
        wizardModel.add(new PersonalInformationStep(model));
        earningsStep = new EarningsStep();
        wizardModel.add(earningsStep);
        spendingsStep = new SpendingsStep();
        wizardModel.add(spendingsStep);
        insuranceStep = new InsuranceStep();
        wizardModel.add(insuranceStep);
        wizardModel.add(new OverviewStep(model, getIncomes(), getSpendings()));
        wizardModel.add(new ConfirmationStep());
        init(wizardModel);
    }

    private IModel<List<Transaction>> getIncomes() {
        return new AbstractReadOnlyModel<List<Transaction>>() {
            @Override
            public List<Transaction> getObject() {
                return earningsStep.getTransactions();
            }
        };
    }

    private IModel<List<Transaction>> getSpendings() {
        return new AbstractReadOnlyModel<List<Transaction>>() {
            @Override
            public List<Transaction> getObject() {
                List<Transaction> list = new ArrayList<Transaction>();
                list.addAll(spendingsStep.getTransactions());
                list.addAll(insuranceStep.getTransactions());
                return list;
            }
        };
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (!submitted) {
            List<Transaction> transactions = new ArrayList<Transaction>();
            for (Transaction t : earningsStep.getTransactions()) {
                Transaction t1 = new Transaction();
                t1.setDescription(t.getDescription());
                t1.setDescription1(t.getDescription1());
                t1.setDescription2(t.getDescription2());
                t1.setValue(Math.abs(t.getValue()));
                transactions.add(t1);
            }
            for (Transaction t : spendingsStep.getTransactions()) {
                Transaction t1 = new Transaction();
                t1.setDescription(t.getDescription());
                t1.setDescription1(t.getDescription1());
                t1.setDescription2(t.getDescription2());
                t1.setValue(Math.abs(t.getValue()) * -1);
                transactions.add(t1);
            }
            for (Transaction t : insuranceStep.getTransactions()) {
                Transaction t1 = new Transaction();
                t1.setDescription(t.getDescription());
                t1.setDescription1(t.getDescription1());
                t1.setDescription2(t.getDescription2());
                t1.setValue(Math.abs(t.getValue()) * -1);
                transactions.add(t1);
            }
            model.getObject().setTransactions(transactions);
            DatabaseConnector.getInstance().saveCreditrequest(model.getObject());
            submitted = true;
        }
        setResponsePage(getApplication().getHomePage());
    }

    @Override
    protected Component newButtonBar(String id) {
        return new NewCreditRequestButtonBarPanel(id, this);
    }
}
