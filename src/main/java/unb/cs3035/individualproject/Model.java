package unb.cs3035.individualproject;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Model {
    private SimpleListProperty<String> actionListProperty;

    Model()
    {
        ArrayList<String> list = new ArrayList<String>();
        ObservableList<String> observableList = (ObservableList<String>) FXCollections.observableArrayList(list);
        actionListProperty = new SimpleListProperty<String>(observableList);
    }

    public SimpleListProperty<String> actionListProperty(){
        return actionListProperty;
    }

    public void updateListProperty(String oldValue, String newValue) {
        int index = actionListProperty.indexOf(oldValue);

        if (index != -1) {
            actionListProperty.set(index, newValue);
        }
    }

    public void addAction(String action)
    {
        actionListProperty.add(action);
    }
    public void deleteAction(String action)
    {
        actionListProperty.remove(action);
    }
}
