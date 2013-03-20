package de.puzzles;

import de.puzzles.core.domain.RepaymentPlan;

/**
 * temporary Test-Class for testing a RepaymentPlan. Does not affect the webapp in any case.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 12.03.13
 */
public class TestPgh {

    private TestPgh() {
    }

    public static void main(String[] args) {
        RepaymentPlan plan = new RepaymentPlan();

        plan.setAmount(10000.0);
        plan.setInterest(0.10);
        plan.setRate(1000.0);

        plan.generateRepaymentPlan();

        for (int i = 0; i <= plan.getTableSize(); i++) {
            System.out.print("jahr: " + i);
            System.out.print("\t rate: " + plan.getRepaymentRates()[i]);
            System.out.print("\t zinsen: " + plan.getInterestPayments()[i]);
            System.out.println("\t rest: " + plan.getRestDebtAmount()[i]);
        }

    }

}
