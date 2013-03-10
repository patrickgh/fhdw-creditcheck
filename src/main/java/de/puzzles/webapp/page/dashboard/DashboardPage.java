package de.puzzles.webapp.page.dashboard;

import de.puzzles.core.domain.CreditRequest;
import de.puzzles.webapp.components.CreditRequestDataProvider;
import de.puzzles.webapp.page.RequiresLoginPage;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
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

    public DashboardPage() {
        super();
        final CreditRequestDataProvider provider = new CreditRequestDataProvider(getUserId());
        DataTable<CreditRequest, String> table = new DataTable<CreditRequest, String>("table", generateColumns(), provider, ROWS_PER_PAGE);
        table.addTopToolbar(new HeadersToolbar<String>(table,new ISortStateLocator<String>() {
            @Override
            public ISortState<String> getSortState() {
                return provider.getSortState();
            }
        }));
        add(table);
    }

    private List<? extends IColumn<CreditRequest, String>> generateColumns() {
        List<IColumn<CreditRequest,String>> list = new ArrayList<IColumn<CreditRequest, String>>();
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("Vorname"),"customer.firstname"));
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("Nachname"),"customer.lastname"));
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("Datum"),"creationdate","creationDate"));
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("Kreditsumme"),"amount","amount"));
        list.add(new PropertyColumn<CreditRequest, String>(new Model<String>("Status"),"state","state"));
        return list;
    }
}
