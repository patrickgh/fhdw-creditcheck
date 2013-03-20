package de.puzzles.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hm
 * Date: 08.03.13
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public class RepaymentPlan implements Serializable {

    private Double duration = 5.0;
    private double amount = 10000;
    private double interest = 0.07; // example: 0.10 => 10%
    private Double rate;
    private double[] repaymentRates;
    private double[] interestPayments;
    private double[] restDebtAmount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = Math.abs(amount);
        this.rate = calculateRate();
    }

    public double getDuration() {
        return (duration != null) ? duration : 0.0;
    }

    public void setDuration(double duration) {
        this.duration = Math.abs(duration);
        this.rate = calculateRate();
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
        this.duration = null;
    }

    public double[] getRepaymentRates() {
        return Arrays.copyOf(repaymentRates, repaymentRates.length);
    }

    public double[] getRestDebtAmount() {
        return Arrays.copyOf(restDebtAmount, restDebtAmount.length);
    }

    public double[] getInterestPayments() {
        return Arrays.copyOf(interestPayments, interestPayments.length);
    }

    private double calculateRate() {
        return amount * ((interest * Math.pow(interest + 1, duration)) / (Math.pow(1 + interest, duration) - 1));
    }

    private double calculateDuration() {
        return -1 * (Math.log(1 - ((interest * amount) / rate)) / Math.log(1 + interest));
    }

    public int getTableSize() {
        return ((int) ((double) duration)) + 1;
    }

    private double[] calculateRestDebtAmount() {
        restDebtAmount = new double[getTableSize() + 1];
        restDebtAmount[0] = amount;
        for (int i = 1; i <= getTableSize(); i++) {
            restDebtAmount[i] = amount * ((Math.pow(1 + interest, duration) - Math.pow(1 + interest, i)) / (Math.pow(1 + interest, duration) - 1));
            if (restDebtAmount[i] < 0.0) {
                restDebtAmount[i] = 0.0;
            }
        }
        return restDebtAmount;
    }

    private double[] calculateInterestPayments() {
        interestPayments = new double[getTableSize() + 1];
        interestPayments[0] = 0.0;
        for (int i = 1; i <= getTableSize(); i++) {
            interestPayments[i] = restDebtAmount[i - 1] * interest;
        }
        return interestPayments;

    }

    private double[] calculateRepaymentRates() {
        repaymentRates = new double[getTableSize() + 1];
        repaymentRates[0] = 0.0;
        for (int i = 1; i <= getTableSize(); i++) {
            repaymentRates[i] = rate;
            if ((restDebtAmount[i - 1] + interestPayments[i]) < rate) {
                repaymentRates[i] = restDebtAmount[i - 1] + interestPayments[i];
            }
        }
        return repaymentRates;
    }

    public List<RepaymentEntry> generateRepaymentPlan() {
        if (rate == null && duration != null) {
            rate = calculateRate();
        }
        else if (duration == null && rate != null) {
            duration = calculateDuration();
        }
        calculateRestDebtAmount();
        calculateInterestPayments();
        calculateRepaymentRates();
        List<RepaymentEntry> list = new ArrayList<RepaymentEntry>();
        for (int i = 0; i < getTableSize(); i++) {
            list.add(new RepaymentEntry(getRestDebtAmount()[i], getInterestPayments()[i], getRepaymentRates()[i]));
        }
        return list;
    }
}

