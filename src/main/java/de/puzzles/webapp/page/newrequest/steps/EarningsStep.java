package de.puzzles.webapp.page.newrequest.steps;

import de.puzzles.core.domain.Transaction;
import de.puzzles.webapp.components.listeditor.ListEditor;
import de.puzzles.webapp.components.listeditor.ListItem;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 06.03.13
 */
public class EarningsStep extends WizardStep {

    List<Transaction> incomeList = new ArrayList<Transaction>();
    List<Transaction> additionalList = new ArrayList<Transaction>();

    private TextField<Double> resultField;

    public EarningsStep() {
        super();
        final WebMarkupContainer container = new WebMarkupContainer("incomeContainer");
        container.setOutputMarkupId(true);
        incomeList.add(new Transaction());
        final ListEditor<Transaction> income = new ListEditor<Transaction>("income", new PropertyModel<List<Transaction>>(this, "incomeList")) {
            @Override
            protected void onPopulateItem(ListItem<Transaction> item) {
                CompoundPropertyModel<Transaction> model = new CompoundPropertyModel<Transaction>(item.getModel());
                item.add(new TextField<String>("descriptionIncome", model.<String>bind("description")));
                TextField<Double> valueField = new TextField<Double>("valueIncome", model.<Double>bind("value"));
                valueField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
                    @Override
                    protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                        updateResultField(ajaxRequestTarget);
                    }
                });
                item.add(valueField);
            }
        };
        container.add(income);
        add(container);

        add(new AjaxLink("incomeAdd") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                income.addItem(new Transaction());
                ajaxRequestTarget.add(container);
            }
        });

        additionalList.add(new Transaction());
        final WebMarkupContainer additionalContainer = new WebMarkupContainer("additionalIncomeContainer");
        additionalContainer.setOutputMarkupId(true);
        final ListEditor<Transaction> additional = new ListEditor<Transaction>("additionalIncome", new PropertyModel<List<Transaction>>(this, "additionalList")) {
            @Override
            protected void onPopulateItem(ListItem<Transaction> item) {
                CompoundPropertyModel<Transaction> model = new CompoundPropertyModel<Transaction>(item.getModel());
                item.add(new TextField<String>("descriptionAdditional", model.<String>bind("description")));
                TextField<Double> valueField = new TextField<Double>("valueAdditional", model.<Double>bind("value"));
                valueField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
                    @Override
                    protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                        updateResultField(ajaxRequestTarget);
                    }
                });
                item.add(valueField);
            }
        };
        additionalContainer.add(additional);
        add(additionalContainer);

        add(new AjaxLink("additionalAdd") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                additional.addItem(new Transaction());
                ajaxRequestTarget.add(additionalContainer);
            }
        });

        resultField = new TextField<Double>("total", new Model<Double>(0.0));
        resultField.setOutputMarkupId(true);
        add(resultField);
    }

    private void updateResultField(AjaxRequestTarget target) {
        Double sum = 0.0;
        for (Transaction transaction : incomeList) {
            sum += transaction.getValue();
        }
        for (Transaction transaction : additionalList) {
            sum += transaction.getValue();
        }
        resultField.setDefaultModelObject(sum);
        target.add(resultField);
    }

    public List<Transaction> getTransactions() {
        List<Transaction> list = new ArrayList<Transaction>();
        list.addAll(incomeList);
        list.addAll(additionalList);
        return list;
    }
}
