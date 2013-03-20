package de.puzzles.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents the <code>creditrequest</code> object.
 * This class is a model class. Instances of this class represent <code>creditrequest</code> objects.
 * The instance of the object stores the data of an <code>creditrequest</code> during the runtime.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 03.03.13
 */
public class CreditRequest implements Serializable {

    private RepaymentPlan repaymentPlan = new RepaymentPlan();
    private Integer id;
    private Customer customer = new Customer();
    private Integer consultantId;
    private Date creationDate;
    private CreditState state = CreditState.PENDING;
    private List<Transaction> transactions = new ArrayList<Transaction>();

    public CreditRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Integer consultantId) {
        this.consultantId = consultantId;
    }

    public Date getCreationDate() {
        return new Date(creationDate.getTime());
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = new Date(creationDate.getTime());
    }

    public CreditState getState() {
        return state;
    }

    public void setState(CreditState state) {
        if (state != null) {
            this.state = state;
        }
    }

    /**
     * This method adds a transaction to the list of the transactions, which are related to the creditrequest.
     *
     * @param trans the transaction object which should be added
     */
    public void addTransaction(Transaction trans) {
        if (trans != null) {
            transactions.add(trans);
        }
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<Transaction>(transactions);
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public RepaymentPlan getRepaymentPlan() {
        return repaymentPlan;
    }
}
