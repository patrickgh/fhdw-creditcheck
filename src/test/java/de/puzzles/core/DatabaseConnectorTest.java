package de.puzzles.core;

import de.puzzles.core.domain.CreditRequest;
import de.puzzles.core.domain.CreditState;
import de.puzzles.core.domain.Customer;
import de.puzzles.core.domain.Transaction;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: pgh
 * Date: 28.02.13
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseConnectorTest {

    @Test
    public void testLogin() {
        DatabaseConnector conn = DatabaseConnector.getInstance();
        Assert.assertNotNull(conn.checkLogin("bstinson", "test"));
        Assert.assertNull(conn.checkLogin("bstinson", "legendary"));
    }

    @Test
    public void testSaveCreditrequest() {
        DatabaseConnector conn = DatabaseConnector.getInstance();

        CreditRequest req = new CreditRequest();
        Customer customer = new Customer();
        customer.setFirstname("Patrick");
        customer.setLastname("Groß-Holtwick");
        customer.setAccountnumber("22334545");
        customer.setBankcode("blz");
        customer.setBirthday(new DateTime());
        customer.setCity("Bocholt");
        customer.setZipcode("46399");
        customer.setEmail("patrickgh@web.de");
        customer.setStreet("Richterstraße 10");
        customer.setTelephone("0000000");

        req.setCustomer(customer);
        req.setAmount(10000.0);
        req.setConsultantId(1);
        req.setState(CreditState.PENDING);
        req.setRate(100.0);

        req.addTransaction(new Transaction(null,null,"Lohn/Gehalt","Laudert Gmbh+Co KG","",450.0));
        req.addTransaction(new Transaction(null,null,"Miete","Wohnung Bielefeld","", -300.0));

        Assert.assertTrue(conn.saveCreditrequest(req));
    }

}
