package de.puzzles.webapp.components;

import de.puzzles.core.DatabaseConnector;
import de.puzzles.core.domain.CreditRequest;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import java.util.Date;
import java.util.Iterator;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 10.03.13
 */
public class CreditRequestDataProvider extends SortableDataProvider<CreditRequest,String>{

    private int userId;

    private Date startDate;
    private Date endDate;
    private String searchString;

    public CreditRequestDataProvider(int userId) {
        super();
        this.userId = userId;
        setSort("creationdate", SortOrder.ASCENDING);
    }

    @Override
    public Iterator<? extends CreditRequest> iterator(long first, long count) {
        return DatabaseConnector.getInstance().getCreditRequest(userId,searchString,startDate,endDate,Long.valueOf(first).intValue(),Long.valueOf(count).intValue(),getSortString()).iterator();
    }

    @Override
    public long size() {
        return DatabaseConnector.getInstance().getCreditRequest(userId,searchString,startDate,endDate,null,null,getSortString()).size();
    }

    @Override
    public IModel<CreditRequest> model(CreditRequest creditRequest) {
        return new Model<CreditRequest>(creditRequest);
    }

    private String getSortString() {
        String sortString = getSort().getProperty() + " ";
        if(getSort().isAscending()) {
            return sortString+"ASC";
        }
        return sortString +"DESC";
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
