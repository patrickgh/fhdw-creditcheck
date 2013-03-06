package de.puzzles.core.domain;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the <code>creditrequest</code> object.
 * This class is a model class. Instances of this class represent <code>creditrequest</code> objects.
 * The instance of the object stores the data of an <code>creditrequest</code> during the runtime.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 03.03.13
 *
 *
 */
public class CreditRequest implements Serializable {

    private Integer id;
    private Customer customer;
    private Integer consultantId;
    private DateTime creationDate;
    private CreditState state;
    private Double amount;
    private Double rate;
    private Integer duration;
    private List<Transaction> transactions = new ArrayList<Transaction>();

    public CreditRequest() {
    }

    /**
     * Constructor
     * @param id
     * @param customer
     * @param consultantId
     * @param creationDate
     * @param state
     * @param amount
     * @param rate
     * @param duration
     * @param transactions
     */
    public CreditRequest(Integer id, Customer customer, Integer consultantId, DateTime creationDate, CreditState state, Double amount, Double rate, Integer duration, List<Transaction> transactions) {
        this.id = id;
        this.customer = customer;
        this.consultantId = consultantId;
        this.creationDate = new DateTime(creationDate);
        this.state = state;
        this.amount = amount;
        this.rate = rate;
        this.duration = duration;
        if (transactions != null) {
            this.transactions = transactions;
        }
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

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = new DateTime(creationDate);
    }

    public CreditState getState() {
        return state;
    }

    public void setState(CreditState state) {
        this.state = state;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * This method checks if the creditrequest has a fixed lenght (duration) or a fixed rate.
     * If the duration of the creditrequest is fix, the rate will be automatically calculated.
     * If the rate of the creditrequest is fix, the duration will be automatically calculated.
     * @return  true or false. Returns true if the creditrequest has a fixed length. Returns true if the creditrequest has a fixed rate.
     */
    public boolean hasFixedLength() {
        return duration != null;
    }

    /**
     * This method adds a transaction to the list of the transactions, which are related to the creditrequest.
     * @param trans
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
}
