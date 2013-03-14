package de.puzzles.webapp.page.newrequest;

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
 * @author Patrick Groß-Holtwick
 *         Date: 13.03.13
 */
public class RepaymentPlanPanel extends GenericPanel<List<RepaymentPlan.Entry>> {

    public RepaymentPlanPanel(String id) {
        super(id);
        RepaymentPlan plan = new RepaymentPlan();
        setModel(new ListModel<RepaymentPlan.Entry>(plan.generateRepaymentPlan()));
    }

    public RepaymentPlanPanel(String id, IModel<List<RepaymentPlan.Entry>> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new ListView<RepaymentPlan.Entry>("table",getModel()) {
            @Override
            protected void populateItem(ListItem<RepaymentPlan.Entry> item) {
                CompoundPropertyModel<RepaymentPlan.Entry> model = new CompoundPropertyModel<RepaymentPlan.Entry>(item.getModel());
                item.add(new Label("year",item.getIndex()));
                item.add(new Label("rate", model.<Double>bind("rate")));
                item.add(new Label("intrest", model.<Double>bind("interestPayment")));
                item.add(new Label("restdebt", model.<Double>bind("restDebt")));
            }
        });
    }
}
