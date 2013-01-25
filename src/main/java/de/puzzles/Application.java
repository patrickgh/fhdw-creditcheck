package de.puzzles;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Created with IntelliJ IDEA.
 * User: pgh
 * Date: 25.01.13
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class Application extends WebApplication{

    @Override
    public Class<? extends Page> getHomePage() {
        return TestPage.class;
    }
}
