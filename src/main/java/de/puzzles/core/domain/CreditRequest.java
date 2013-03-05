package de.puzzles.core.domain;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 03.03.13
 *         Time: 16:58
 *         To change this template use File | Settings | File Templates.
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

    public boolean hasFixedLength() {
        return duration != null;
    }

    public void addTransaction(Transaction trans) {
        if (trans != null) {
            transactions.add(trans);
        }
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<Transaction>(transactions);
    }
}
