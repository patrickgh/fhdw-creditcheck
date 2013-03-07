package de.puzzles.webapp.page.newrequest.steps;

import de.puzzles.core.domain.Transaction;
import de.puzzles.webapp.components.listeditor.ListEditor;
import de.puzzles.webapp.components.listeditor.ListItem;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.form.Button;
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

    public EarningsStep() {
        super();
        incomeList.add(new Transaction());
        final ListEditor<Transaction> income = new ListEditor<Transaction>("income", new PropertyModel<List<Transaction>>(this,"incomeList")) {
            @Override
            protected void onPopulateItem(ListItem<Transaction> item) {
                CompoundPropertyModel model = new CompoundPropertyModel(item.getModel());
                item.add(new TextField("descriptionIncome", model.bind("description")));
                item.add(new TextField("valueIncome", model.bind("value")));
            }
        };
        add(income);

        add(new Button("addIncome") {
            @Override
            public void onSubmit() {
                income.addItem(new Transaction());
            }
        }.setDefaultFormProcessing(false));

        additionalList.add(new Transaction());
        final ListEditor<Transaction> additional = new ListEditor<Transaction>("additionalIncome", new PropertyModel<List<Transaction>>(this,"additionalList")) {
            @Override
            protected void onPopulateItem(ListItem<Transaction> item) {
                CompoundPropertyModel model = new CompoundPropertyModel(item.getModel());
                item.add(new TextField("descriptionAdditional", model.bind("description")));
                item.add(new TextField("valueAdditional", model.bind("value")));
            }
        };
        add(additional);

        add(new Button("addAdditionalIncome") {
            @Override
            public void onSubmit() {
                additional.addItem(new Transaction());
            }
        }.setDefaultFormProcessing(false));

        add(new TextField("total", new Model("0")));
    }
}
