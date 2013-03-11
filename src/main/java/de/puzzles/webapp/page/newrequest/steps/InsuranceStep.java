package de.puzzles.webapp.page.newrequest.steps;

import de.puzzles.core.domain.Transaction;
import de.puzzles.webapp.components.listeditor.ListEditor;
import de.puzzles.webapp.components.listeditor.ListItem;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 07.03.13
 */
public class InsuranceStep extends WizardStep {

    List<Transaction> insuranceList = new ArrayList<Transaction>();
    List<Transaction> creditList = new ArrayList<Transaction>();

    private TextField<Double> resultField;

    public InsuranceStep() {
        super();
        final WebMarkupContainer container = new WebMarkupContainer("insuranceContainer");
        container.setOutputMarkupId(true);
        insuranceList.add(new Transaction());
        final ListEditor<Transaction> insurance = new ListEditor<Transaction>("insurance", new PropertyModel<List<Transaction>>(this, "insuranceList")) {
            @Override
            protected void onPopulateItem(ListItem<Transaction> item) {
                CompoundPropertyModel<Transaction> model = new CompoundPropertyModel<Transaction>(item.getModel());
                item.add(new DropDownChoice<String>("insuranceType", model.<String>bind("description"), getInsuranceTypes()));
                item.add(new TextField<String>("insuranceCompany", model.<String>bind("description1")));
                item.add(new TextField<String>("insuranceAmount", model.<String>bind("description2")));
                TextField<Double> valueField = new TextField<Double>("insuranceValue", model.<Double>bind("value"));
                valueField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
                    @Override
                    protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                        updateResultField(ajaxRequestTarget);
                    }
                });
                item.add(valueField);
            }
        };
        container.add(insurance);
        add(container);

        add(new AjaxLink("insuranceAdd") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                insurance.addItem(new Transaction());
                ajaxRequestTarget.add(container);
            }
        });

        final WebMarkupContainer creditContainer = new WebMarkupContainer("creditContainer");
        creditContainer.setOutputMarkupId(true);
        creditList.add(new Transaction());
        final ListEditor<Transaction> credit = new ListEditor<Transaction>("credit", new PropertyModel<List<Transaction>>(this, "creditList")) {
            @Override
            protected void onPopulateItem(ListItem<Transaction> item) {
                CompoundPropertyModel<Transaction> model = new CompoundPropertyModel<Transaction>(item.getModel());
                item.add(new TextField<String>("creditBank", model.<String>bind("description")));
                item.add(new TextField<String>("creditAmount", model.<String>bind("description1")));
                item.add(new TextField<String>("creditRest", model.<String>bind("description2")));
                TextField<Double> valueField = new TextField<Double>("creditValue", model.<Double>bind("value"));
                valueField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
                    @Override
                    protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                        updateResultField(ajaxRequestTarget);
                    }
                });
                item.add(valueField);
            }
        };
        creditContainer.add(credit);
        add(creditContainer);

        add(new AjaxLink("creditAdd") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                credit.addItem(new Transaction());
                ajaxRequestTarget.add(creditContainer);
            }
        });

        resultField = new TextField<Double>("total", new Model<Double>(0.0));
        resultField.setOutputMarkupId(true);
        add(resultField);
    }

    private void updateResultField(AjaxRequestTarget target) {
        Double sum = 0.0;
        for (Transaction transaction : creditList) {
            sum += transaction.getValue();
        }
        for (Transaction transaction : insuranceList) {
            sum += transaction.getValue();
        }
        resultField.setDefaultModelObject(sum);
        target.add(resultField);
    }

    private List<String> getInsuranceTypes() {
        return Arrays.<String>asList(new String[]{"Unfallversicherung", "Reiseversicherung", "Haftpflichtversicherung", "Rechtsschutzversicherung", "Sonstige Versicherung"});
    }

    public List<Transaction> getTransactions() {
        List<Transaction> list = new ArrayList<Transaction>();
        list.addAll(insuranceList);
        list.addAll(creditList);
        return list;
    }
}
