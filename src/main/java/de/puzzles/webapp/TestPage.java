package de.puzzles.webapp;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pgh
 * Date: 25.01.13
 * Time: 14:06
 * To change this template use File | Settings | File Templates.
 */
public class TestPage extends WebPage {

    public TestPage() {
        add(new Label("test",new Date().toString()));
    }
}
