package de.puzzles.webapp.components.panel;

import de.puzzles.core.domain.RepaymentEntry;
import de.puzzles.core.domain.RepaymentPlan;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import java.util.List;

/**
 * Panel for a RepaymentPlan table. Expects a given list with RepaymentEntry objects and renders the table (for example on CreditDetailsPage)
 *
 * @author Patrick Groß-Holtwick
 *         Date: 13.03.13
 */
public class RepaymentPlanPanel extends GenericPanel<List<RepaymentEntry>> {

    public RepaymentPlanPanel(String id) {
        super(id);
        RepaymentPlan plan = new RepaymentPlan();
        setModel(new ListModel<RepaymentEntry>(plan.generateRepaymentPlan()));
    }

    public RepaymentPlanPanel(String id, IModel<List<RepaymentEntry>> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new ListView<RepaymentEntry>("table", getModel()) {
            @Override
            protected void populateItem(ListItem<RepaymentEntry> item) {
                CompoundPropertyModel<RepaymentEntry> model = new CompoundPropertyModel<RepaymentEntry>(item.getModel());
                item.add(new Label("year", item.getIndex()));
                item.add(new Label("rate", model.<Double>bind("rate")));
                item.add(new Label("intrest", model.<Double>bind("interestPayment")));
                item.add(new Label("restdebt", model.<Double>bind("restDebt")));
            }
        });
    }
}
