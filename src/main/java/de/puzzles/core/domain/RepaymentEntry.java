package de.puzzles.core.domain;

import java.io.Serializable;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 20.03.13
 */
public class RepaymentEntry implements Serializable {

    private Double restDebt;
    private Double interestPayment;
    private Double rate;

    public RepaymentEntry(Double restDebt, Double interestPayment, Double rate) {
        this.restDebt = restDebt;
        this.interestPayment = interestPayment;
        this.rate = rate;
    }

    public Double getRestDebt() {
        return restDebt;
    }

    public void setRestDebt(Double restDebt) {
        this.restDebt = restDebt;
    }

    public Double getInterestPayment() {
        return interestPayment;
    }

    public void setInterestPayment(Double interestPayment) {
        this.interestPayment = interestPayment;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
