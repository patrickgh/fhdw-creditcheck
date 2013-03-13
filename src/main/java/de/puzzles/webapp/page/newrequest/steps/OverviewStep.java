package de.puzzles.webapp.page.newrequest.steps;

import de.puzzles.core.domain.CreditRequest;
import de.puzzles.core.domain.RepaymentPlan;
import de.puzzles.core.domain.Transaction;
import de.puzzles.webapp.page.newrequest.RepaymentPlanPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 07.03.13
 */
public class OverviewStep extends WizardStep {

    private CompoundPropertyModel<CreditRequest> requestModel;
    private CompoundPropertyModel<List<Transaction>> incomeModel;
    private CompoundPropertyModel<List<Transaction>> spendingsModel;
    private TextField<Double> rateField;
    private TextField<Double> durationField;
    private RepaymentPlanPanel repaymentPlan;

    public OverviewStep(IModel<CreditRequest> model, IModel<List<Transaction>> incomes, IModel<List<Transaction>> spendings) {
        super();
        requestModel = new CompoundPropertyModel<CreditRequest>(model);
        incomeModel = new CompoundPropertyModel<List<Transaction>>(incomes);
        spendingsModel = new CompoundPropertyModel<List<Transaction>>(spendings);

        Label interestLabel = new Label("interest", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return (requestModel.getObject().getRepaymentPlan().getInterest() * 100) + " %";
            }
        });
        add(interestLabel);
        TextField<Double> amount = new TextField<Double>("creditAmount", requestModel.<Double>bind("repaymentPlan.amount"));
        amount.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                updateFields(target);
            }
        });
        amount.setRequired(true);
        add(amount);

        durationField = new TextField<Double>("creditDuration", requestModel.<Double>bind("repaymentPlan.duration"));
        durationField.setRequired(true);
        durationField.setOutputMarkupId(true);
        durationField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                updateFields(target);
            }
        });
        add(durationField);

        rateField = new TextField<Double>("creditRate", new AbstractReadOnlyModel<Double>() {
            @Override
            public Double getObject() {
                return requestModel.getObject().getRepaymentPlan().getRate();
            }
        });
        rateField.setOutputMarkupId(true);
        add(rateField);
        repaymentPlan = new RepaymentPlanPanel("repaymentPlan",new AbstractReadOnlyModel<List<RepaymentPlan.Entry>>() {
            @Override
            public List<RepaymentPlan.Entry> getObject() {
                return requestModel.getObject().getRepaymentPlan().generateRepaymentPlan();
            }
        });
        repaymentPlan.setOutputMarkupId(true);
        add(repaymentPlan);
    }

    public void updateFields(AjaxRequestTarget target) {
        requestModel.getObject().getRepaymentPlan().setAmount(requestModel.getObject().getRepaymentPlan().getAmount());
        target.add(rateField);
        target.add(repaymentPlan);
    }
}
