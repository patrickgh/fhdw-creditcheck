package de.puzzles.core;

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

}
