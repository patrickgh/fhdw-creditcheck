package de.puzzles.webapp.page.newrequest.wizard.steps;

import com.vaynberg.wicket.select2.Select2Choice;
import de.puzzles.core.DatabaseConnector;
import de.puzzles.core.domain.Transaction;
import de.puzzles.core.util.PuzzlesUtils;
import de.puzzles.webapp.components.provider.CarCostChoiceProvider;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Wizard step for the spendings. Contains all form components for the general spendings (e.g. rent, car costs, telephone costs,...)
 *
 * @author Patrick Groß-Holtwick
 *         Date: 07.03.13
 */
public class SpendingsStep extends WizardStep {

    public static final String ONCHANGE = "onchange";
    private Transaction rent = new Transaction(null, null, "Miete", "", "", null);
    private Transaction incidentials = new Transaction(null, null, "Wohnnebenkosten", "", "", null);
    private Transaction phone = new Transaction(null, null, "Telefon/Internet/Handy", "", "", null);
    private Transaction savingplans = new Transaction(null, null, "Sparverträge", "", "", null);
    private Transaction livingcosts = new Transaction(null, null, "Lebenshaltungskosten", "0 Personen", "", 0.0);
    private Transaction carcosts = new Transaction(null, null, "KFZ-Kosten", "", "", null);
    private Transaction additional = new Transaction(null, null, "", "", "", null);
    private TextField<Double> resultField;

    public SpendingsStep() {
        super();
        TextField<Double> rent = new TextField<Double>("rent", new PropertyModel<Double>(this, "rent.value"));
        rent.add(new AjaxFormComponentUpdatingBehavior(ONCHANGE) {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                updateResultField(ajaxRequestTarget);
            }
        });
        add(rent);

        TextField<Double> incidentials = new TextField<Double>("incidentials", new PropertyModel<Double>(this, "incidentials.value"));
        incidentials.add(new AjaxFormComponentUpdatingBehavior(ONCHANGE) {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                updateResultField(ajaxRequestTarget);
            }
        });
        add(incidentials);

        TextField<Double> phone = new TextField<Double>("phone", new PropertyModel<Double>(this, "phone.value"));
        phone.add(new AjaxFormComponentUpdatingBehavior(ONCHANGE) {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                updateResultField(ajaxRequestTarget);
            }
        });
        add(phone);

        TextField<Double> savingplans = new TextField<Double>("savingplans", new PropertyModel<Double>(this, "savingplans.value"));
        savingplans.add(new AjaxFormComponentUpdatingBehavior(ONCHANGE) {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                updateResultField(ajaxRequestTarget);
            }
        });
        add(savingplans);

        TextField<Double> additional = new TextField<Double>("additionalValue", new PropertyModel<Double>(this, "additional.value"));
        additional.add(new AjaxFormComponentUpdatingBehavior(ONCHANGE) {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                updateResultField(ajaxRequestTarget);
            }
        });
        add(additional);

        TextField<String> additionalDescription = new TextField<String>("additionalDescription", new PropertyModel<String>(this, "additional.description"));
        add(additionalDescription);

        final TextField<Double> personsResult = new TextField<Double>("personsResult", new PropertyModel<Double>(this, "livingcosts.value"));
        personsResult.setOutputMarkupId(true);
        personsResult.setEnabled(false);
        add(personsResult);

        final TextField<String> persons = new TextField<String>("persons", new Model<String>("0")) {
            @Override
            protected String getInputType() {
                return "number";
            }
        };
        persons.add(new AjaxFormComponentUpdatingBehavior(ONCHANGE) {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                Integer personCount = PuzzlesUtils.parseInt(persons.getModelObject());
                if (personCount != null) {
                    livingcosts.setDescription1(personCount + " Personen");
                    Double costs = DatabaseConnector.getInstance().getLivingCosts(personCount);
                    livingcosts.setValue(costs);
                    ajaxRequestTarget.add(personsResult);
                    updateResultField(ajaxRequestTarget);
                }
            }
        });
        add(persons);

        final TextField<Double> carcostLabel = new TextField<Double>("carcostlabel", new PropertyModel<Double>(this, "carcosts.value"));
        carcostLabel.setOutputMarkupId(true);
        carcostLabel.setEnabled(false);
        add(carcostLabel);

        Select2Choice<Double> carcost = new Select2Choice<Double>("carcost", new PropertyModel<Double>(this, "carcosts.value"), new CarCostChoiceProvider());
        carcost.add(new AjaxFormComponentUpdatingBehavior(ONCHANGE) {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                ajaxRequestTarget.add(carcostLabel);
                updateResultField(ajaxRequestTarget);
            }
        });
        add(carcost);

        resultField = new TextField<Double>("total", new Model<Double>(0.0));
        resultField.setOutputMarkupId(true);
        add(resultField);

    }

    private void updateResultField(AjaxRequestTarget target) {
        Double sum = rent.getValue();
        sum += incidentials.getValue();
        sum += savingplans.getValue();
        sum += phone.getValue();
        sum += livingcosts.getValue();
        sum += carcosts.getValue();
        sum += additional.getValue();
        resultField.setDefaultModelObject(sum);
        target.add(resultField);
    }

    public List<Transaction> getTransactions() {
        List<Transaction> list = new ArrayList<Transaction>();
        list.add(rent);
        list.add(incidentials);
        list.add(savingplans);
        list.add(phone);
        list.add(livingcosts);
        list.add(additional);
        list.add(carcosts);
        return list;
    }

}
