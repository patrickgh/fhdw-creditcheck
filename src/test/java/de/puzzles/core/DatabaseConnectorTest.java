package de.puzzles.core;

import de.puzzles.core.domain.CreditRequest;
import de.puzzles.core.domain.CreditState;
import de.puzzles.core.domain.Customer;
import de.puzzles.core.domain.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Test-Class for the DatabaseConnector unit tests.
 * @author Patrick Groß-Holtwick
 */
public class DatabaseConnectorTest {

    /**
     * This Test checks the login method. it tests valid credentials and wrong credentials analyses the results.
     */
    @Test
    public void testLogin() {
        DatabaseConnector conn = DatabaseConnector.getInstance();
        Assert.assertNotNull(conn.checkLogin("bstinson", "test"));
        Assert.assertNull(conn.checkLogin("bstinson", "legendary"));
    }

    /**
     * This test saves a new CreditRequest and reads the request back from the database.
     */
    @Test
    public void testSaveCreditrequest() {
        DatabaseConnector conn = DatabaseConnector.getInstance();

        CreditRequest req = new CreditRequest();
        Customer customer = new Customer();
        customer.setFirstname("Patrick");
        customer.setLastname("Groß-Holtwick");
        customer.setAccountnumber("22334545");
        customer.setBankcode("blz");
        customer.setBirthday(new Date());
        customer.setCity("Bielefeld");
        customer.setZipcode("12345");
        customer.setEmail("patrickgh@web.de");
        customer.setStreet("Teststraße 10");
        customer.setTelephone("0000000");

        req.setCustomer(customer);
        req.getRepaymentPlan().setAmount(10000.0);
        req.setConsultantId(1);
        req.setState(CreditState.PENDING);
        req.getRepaymentPlan().setRate(100.0);

        req.addTransaction(new Transaction(null, null, "Lohn/Gehalt", "FHDW", "", 450.0));
        req.addTransaction(new Transaction(null, null, "Miete", "Wohnung Bielefeld", "", -300.0));

        Integer resultId = conn.saveCreditrequest(req);
        Assert.assertNotNull(resultId);

        req = conn.getCreditRequestById(resultId);
        Assert.assertNotNull(req);
        Assert.assertNotNull(req.getCustomer());
        Assert.assertEquals(req.getCustomer().getFirstname(), "Patrick");
        Assert.assertEquals(req.getRepaymentPlan().getAmount(), 10000.0);
        Assert.assertNotNull(req.getTransactions());
        Assert.assertEquals(req.getTransactions().size(), 2);
    }

    /**
     * This test tests the getLivingCosts method with testdata. It compares the result with the expected results.
     */
    @Test
    public void testGetLivingCost() {
        Assert.assertEquals(DatabaseConnector.getInstance().getLivingCosts(0),0.0);
        Assert.assertEquals(DatabaseConnector.getInstance().getLivingCosts(1),450.0);
        Assert.assertEquals(DatabaseConnector.getInstance().getLivingCosts(2),700.0);
        Assert.assertEquals(DatabaseConnector.getInstance().getLivingCosts(3),850.0);
        Assert.assertEquals(DatabaseConnector.getInstance().getLivingCosts(5),1150.0);
    }

    /**
     * This test tests the getBaseInterest() method and analyses the result.
     */
    @Test
    public void testGetBaseInterest() {
        Assert.assertNotNull(DatabaseConnector.getInstance().getBaseInterest());
    }
}
