package de.puzzles.webapp.components.provider;

import com.vaynberg.wicket.select2.Response;
import com.vaynberg.wicket.select2.TextChoiceProvider;
import de.puzzles.core.DatabaseConnector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Patrick Groß-Holtwick
 *         Date: 09.03.13
 */
public class CarCostChoiceProvider extends TextChoiceProvider<Double> {

    private Map<String, Double> data = new HashMap<String, Double>();

    public CarCostChoiceProvider() {
        data = DatabaseConnector.getInstance().getCarCostTypes();
    }

    @Override
    protected String getDisplayText(Double choice) {
        for (String s : data.keySet()) {
            if (data.get(s).equals(choice)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Object getId(Double choice) {
        return choice;
    }

    @Override
    public void query(String term, int page, Response<Double> response) {
        for (String s : data.keySet()) {
            if (s.toLowerCase().contains(term.toLowerCase()) || s.toLowerCase().equals(term.toLowerCase())) {
                response.add(data.get(s));
            }
        }
    }

    @Override
    public Collection<Double> toChoices(Collection<String> ids) {
        List<Double> results = new ArrayList<Double>();
        for (String id : ids) {
            Double term = Double.valueOf(id);
            if (term != null) {
                results.add(term);
            }
        }
        return results;
    }
}
