package de.puzzles.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
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
        return repaymentRates;
    }

    public double[] getRestDebtAmount() {
        return restDebtAmount;
    }

    public double[] getInterestPayments() {
        return interestPayments;
    }

    private double calculateRate() {
        double rate = amount * ((interest * Math.pow(interest + 1, duration)) / (Math.pow(1 + interest, duration) - 1));
        return rate;
    }

    private double calculateDuration() {
        double tempDuration = -1 * (Math.log(1 - ((interest * amount) / rate)) / Math.log(1 + interest));
        return tempDuration;
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
            if((restDebtAmount[i-1] + interestPayments[i]) < rate) {
                repaymentRates[i] = restDebtAmount[i-1] + interestPayments[i];
            }
        }
        return repaymentRates;
    }

    public List<Entry> generateRepaymentPlan() {
        if (rate == null && duration != null) {
            rate = calculateRate();
        }
        else if (duration == null && rate != null) {
            duration = calculateDuration();
        }
        calculateRestDebtAmount();
        calculateInterestPayments();
        calculateRepaymentRates();
        List<Entry> list = new ArrayList<Entry>();
        for (int i=0; i<getTableSize();i++) {
            list.add(new Entry(getRestDebtAmount()[i],getInterestPayments()[i],getRepaymentRates()[i]));
        }
        return list;
    }


    public class Entry implements Serializable{
        Double restDebt;
        Double interestPayment;
        Double rate;

        public Entry(Double restDebt, Double interestPayment, Double rate) {
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
}

