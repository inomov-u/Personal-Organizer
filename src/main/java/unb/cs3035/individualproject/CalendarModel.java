package unb.cs3035.individualproject;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarModel {

    private SimpleListProperty<String> actionListProperty;
    public List<LocalDate> localDateList;
    public List<String> timeList;
    public List<String> locationList;

    CalendarModel()
    {
        ArrayList<String> list = new ArrayList<String>();
        ObservableList<String> observableList = (ObservableList<String>) FXCollections.observableArrayList(list);
        actionListProperty = new SimpleListProperty<String>(observableList);

        localDateList = new ArrayList<>();
        timeList = new ArrayList<>();
        locationList = new ArrayList<>();
    }
    public void addLocalDate(LocalDate localDate) {
        localDateList.add(localDate);
    }
    public void updateLocalDate(LocalDate oldVal, LocalDate newVal)
    {
        if (localDateList.contains(oldVal)) {
            int index = localDateList.indexOf(oldVal);
            localDateList.set(index, newVal);
        }
    }
    public void deleteLocalDate(LocalDate localDate)
    {
        localDateList.remove(localDate);
    }
    public void addTime(String time)
    {
        timeList.add(time);
    }
    public void updateTime(String oldVal, String newVal)
    {
        if (timeList.contains(oldVal)) {
            int index = timeList.indexOf(oldVal);
            timeList.set(index, newVal);
        }
    }
    public void deleteTime(String time)
    {
        timeList.remove(time);
    }
    public SimpleListProperty<String> actionListProperty(){
        return actionListProperty;
    }
    public void updateListProperty(String oldValue, String newValue) {
        // Find the index of oldValue in the list property
        int index = actionListProperty.indexOf(oldValue);

        if (index != -1) {
            // Replace oldValue with newValue
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
    public void addLocation(String location)
    {
        locationList.add(location);
    }
    public void updateLocation(String oldLocation, String newLocation)
    {
        if (timeList.contains(oldLocation)) {
            int index = timeList.indexOf(oldLocation);
            timeList.set(index, newLocation);
        }
    }
    public void deleteLocation(String location)
    {
        locationList.remove(location);
    }
}
