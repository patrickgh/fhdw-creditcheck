package de.puzzles.webapp;

import de.puzzles.core.domain.CreditState;
import de.puzzles.webapp.page.LoginPage;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.caching.FilenameWithVersionResourceCachingStrategy;
import org.apache.wicket.request.resource.caching.version.LastModifiedResourceVersion;
import org.apache.wicket.resource.NoOpTextCompressor;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.DoubleConverter;
import org.apache.wicket.util.string.Strings;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 25.01.13
 *         Time: 14:08
 *         To change this template use File | Settings | File Templates.
 */
public class PuzzlesApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        if (RuntimeConfigurationType.DEVELOPMENT.equals(getConfigurationType())) {
            getDebugSettings().setDevelopmentUtilitiesEnabled(true);
            getDebugSettings().setAjaxDebugModeEnabled(true);
            getDebugSettings().setOutputComponentPath(true);
            String resourceFolder = System.getProperty("application.resource.src");
            String webResourceFolder = System.getProperty("application.webresource.src");
            if (!Strings.isEmpty(resourceFolder)) {
                getResourceSettings().getResourceFinders().add(new WebApplicationPath(getServletContext(), resourceFolder));
            }
            if (!Strings.isEmpty(webResourceFolder)) {
                getResourceSettings().getResourceFinders().add(new WebApplicationPath(getServletContext(), webResourceFolder));
            }
        }
        else {
            getResourceSettings().setCachingStrategy(new FilenameWithVersionResourceCachingStrategy(new LastModifiedResourceVersion()));
            getResourceSettings().setJavaScriptCompressor(new NoOpTextCompressor());
            getMarkupSettings().setStripComments(true);
            getMarkupSettings().setCompressWhitespace(true);
        }
        getMarkupSettings().setDefaultMarkupEncoding("utf-8");

    }

    @Override
    protected IConverterLocator newConverterLocator() {
        ConverterLocator locator = new ConverterLocator();
        DoubleConverter converter = new DoubleConverter() {
            @Override
            protected NumberFormat newNumberFormat(Locale locale) {
                NumberFormat format = super.newNumberFormat(locale);
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(2);
                return format;
            }
        };
        locator.set(Double.class, converter);
        locator.set(CreditState.class, new IConverter<CreditState>() {
            @Override
            public CreditState convertToObject(String value, Locale locale) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String convertToString(CreditState value, Locale locale) {
                return new ResourceModel("state."+value.toString(), value.toString()).getObject();
            }
        });
        return locator;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return LoginPage.class;
    }

}