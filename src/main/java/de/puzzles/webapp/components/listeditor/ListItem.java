package de.puzzles.webapp.components.listeditor;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * Wicket ListEditor Component
 * Source: http://wicketinaction.com/2008/10/building-a-listeditor-form-component/
 */
public class ListItem<T> extends Item<T> {

    public ListItem(String id, int index) {
        super(id, index);
        setModel(new ListItemModel());
    }

    private class ListItemModel extends AbstractReadOnlyModel<T> {

        @SuppressWarnings("unchecked")
        @Override
        public T getObject() {
            return ((ListEditor<T>) ListItem.this.getParent()).items.get(getIndex());
        }
    }
}
