package de.puzzles.webapp.page.dashboard;

import de.puzzles.core.domain.CreditRequest;
import de.puzzles.core.domain.CreditState;
import de.puzzles.webapp.components.CreditRequestDataProvider;
import de.puzzles.webapp.components.InfoButtonPanel;
import de.puzzles.webapp.page.RequiresLoginPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 02.03.13
 *         Time: 14:50
 *         To change this template use File | Settings | File Templates.
 */
public class DashboardPage extends RequiresLoginPage {

    public static int ROWS_PER_PAGE = 15;

    private DateTextField startDate;
    private DateTextField endDate;

    public DashboardPage() {
        super();
        final CreditRequestDataProvider provider = new CreditRequestDataProvider(getUserId());
        final DataTable<CreditRequest, String> table = new DataTable<CreditRequest, String>("table", generateColumns(), provider, ROWS_PER_PAGE);
        table.addTopToolbar(new AjaxFallbackHeadersToolbar<String>(table, new ISortStateLocator<String>() {
            @Override
            public ISortState<String> getSortState() {
                return provider.getSortState();
            }
        }));
        table.setOutputMarkupId(true);
        add(table);

        Form filterForm = new Form("filterForm");
        TextField customer = new TextField<String>("customer", new PropertyModel<String>(provider, "searchString"));
        filterForm.add(customer);

        startDate = new DateTextField("start", new PropertyModel<Date>(provider, "startDate"));
        filterForm.add(startDate);

        endDate = new DateTextField("end", new PropertyModel<Date>(provider, "endDate"));
        filterForm.add(endDate);

        filterForm.add(new AjaxSubmitLink("submit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                target.add(table);
            }
        });
        add(filterForm);
    }

    private List<? extends IColumn<CreditRequest, String>> generateColumns() {
        List<IColumn<CreditRequest, String>> list = new ArrayList<IColumn<CreditRequest, String>>();
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("id"),"id"){
            @Override
            public void populateItem(Item<ICellPopulator<CreditRequest>> item, String componentId, final IModel<CreditRequest> rowModel) {
                item.add(new InfoButtonPanel(componentId,rowModel.getObject().getId()));
            }
        });
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("Vorname"), "customer.firstname", "customer.firstname"));
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("Nachname"), "customer.lastname", "customer.lastname"));
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("Datum"), "creationdate", "creationDate"));
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("Kreditsumme"), "creditamount", "repaymentPlan.amount"));
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("Status"), "state", "state"));
        return list;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript("$('#" + startDate.getMarkupId() + "').datepicker({format:'dd.mm.yyyy', language:'de',autoclose:true,startView:0});"));
        response.render(OnDomReadyHeaderItem.forScript("$('#" + endDate.getMarkupId() + "').datepicker({format:'dd.mm.yyyy', language:'de',autoclose:true,startView:0});"));
    }

    @Override
    public String getTitle() {
        return "Dashboard";
    }
}
