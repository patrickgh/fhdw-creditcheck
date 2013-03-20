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
 *         Date: 06.03.13
 */
public class ConsultantChoiceProvider extends TextChoiceProvider<Integer> {

    private Map<String, Integer> data = new HashMap<String, Integer>();

    public ConsultantChoiceProvider() {
        data = DatabaseConnector.getInstance().getConsultantNames();
    }

    @Override
    protected String getDisplayText(Integer choice) {
        for (String s : data.keySet()) {
            if (data.get(s).equals(choice)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Object getId(Integer choice) {
        return choice;
    }

    @Override
    public void query(String term, int page, Response<Integer> response) {
        for (String s : data.keySet()) {
            if (s.toLowerCase().contains(term.toLowerCase()) || s.toLowerCase().equals(term.toLowerCase())) {
                response.add(data.get(s));
            }
        }
    }

    @Override
    public Collection<Integer> toChoices(Collection<String> ids) {
        List<Integer> results = new ArrayList<Integer>();
        for (String id : ids) {
            Integer term = Integer.valueOf(id);
            if (term != null) {
                results.add(term);
            }
        }
        return results;
    }

}
