package unb.cs3035.individualproject;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class Controller {
    public Controller() {
        addEventHandlers();
    }

    private void addEventHandlers()
    {
        // Set actions for menu items
        Main.view.openMenuItem.setOnAction(event-> Main.view.showAlertDialog());
        Main.view.saveMenuItem.setOnAction(event-> Main.view.showAlertDialog());
        Main.view.exitMenuItem.setOnAction(event -> Main.primaryStage.close());
        Main.view.organizerViewMenuItem.setOnAction(event->Main.primaryStage.setScene(Main.mainScene));
        Main.view.statisticalViewMenuItem.setOnAction(event -> Main.primaryStage.setScene(Main.view.sceneForStatView));
        Main.view.helpScreenMenuItem.setOnAction(event -> Main.view.showHelpScreen());
        Main.view.aboutMenuItem.setOnAction(event -> Main.view.showAboutDialog());

        Main.view.statisticalView.openMenuItem.setOnAction(event-> Main.view.showAlertDialog());
        Main.view.statisticalView.saveMenuItem.setOnAction(event-> Main.view.showAlertDialog());
        Main.view.statisticalView.exitMenuItem.setOnAction(event -> Main.primaryStage.close());
        Main.view.statisticalView.organizerViewMenuItem.setOnAction(event->Main.primaryStage.setScene(Main.mainScene));
        Main.view.statisticalView.statisticalViewMenuItem.setOnAction(event -> Main.primaryStage.setScene(Main.view.sceneForStatView));
        Main.view.statisticalView.helpScreenMenuItem.setOnAction(event -> Main.view.showHelpScreen());
        Main.view.statisticalView.aboutMenuItem.setOnAction(event -> Main.view.showAboutDialog());


        Main.view.changeView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.primaryStage.setScene(Main.view.sceneForStatView);
            }
        });

        Main.view.statisticalView.changeView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.primaryStage.setScene(Main.mainScene);
            }
        });

        Main.view.nextActionListWidget.vBox.getChildren().addListener((ListChangeListener<? super javafx.scene.Node>) change -> {//NAL
            while (change.next()) {
                if (change.wasAdded()) {
                    for (javafx.scene.Node addedNode : change.getAddedSubList()) {
                        if (addedNode instanceof HBox) {
                            TextField textField = (TextField) ((HBox)addedNode).getChildren().get(1);
                            Main.nextActionListModel.addAction(textField.getText());

                            ((TextField)((HBox)addedNode).getChildren().get(1)).textProperty().addListener(new ChangeListener<String>() {
                                @Override
                                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                    System.out.println("Updated item in NAL - Old Value: " + oldValue + " New Value: " + newValue);
                                    Main.nextActionListModel.updateListProperty(oldValue, newValue);
                                }
                            });
                            System.out.println("Next Action List: " + Main.nextActionListModel.actionListProperty().get());
                        }
                    }
                }
                if (change.wasRemoved()) {
                    for (javafx.scene.Node removedNode : change.getRemoved()) {
                        if (removedNode instanceof HBox) {
                            TextField textField = (TextField) ((HBox)removedNode).getChildren().get(1);
                            Main.nextActionListModel.deleteAction(textField.getText());
                        }
                    }
                }
            }
            System.out.println("Last state of Next Action List: " + Main.nextActionListModel.actionListProperty().get());
        });

        Main.view.calendarWidget.vBox.getChildren().addListener((ListChangeListener<? super javafx.scene.Node>) change -> {//Calendar
            while (change.next()) {
                if (change.wasAdded()) {
                    for (javafx.scene.Node addedNode : change.getAddedSubList()) {
                        if (addedNode instanceof HBox) {

                            HBox hbox = (HBox) addedNode;

                            // TextField
                            TextField textField = (TextField) hbox.getChildren().get(3);
                            Main.calendarModel.addAction(textField.getText());
                            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                                System.out.println("Updated item in Calendar - Old Value: " + oldValue + " New Value: " + newValue);
                                Main.calendarModel.updateListProperty(oldValue, newValue);
                            });

                            //  DatePicker
                            DatePicker datePicker = (DatePicker) hbox.getChildren().get(1);
                            Main.calendarModel.addLocalDate(datePicker.getValue());
                            datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                                Main.calendarModel.updateLocalDate(oldValue, newValue);
                                System.out.println("Selected date changed from: " + oldValue + " to " + Main.calendarModel.localDateList.get(Main.calendarModel.localDateList.indexOf(newValue)));
                            });

                            //  ComboBox
                            ComboBox<String> time = (ComboBox<String>) hbox.getChildren().get(2);
                            Main.calendarModel.addTime(time.getValue());
                            time.valueProperty().addListener((observable, oldValue, newValue) -> {
                                Main.calendarModel.updateTime(oldValue, newValue);
                            });

                            TextField location = (TextField) hbox.getChildren().get(4);
                            Main.calendarModel.addLocation(location.getText());
                            location.textProperty().addListener((observable, oldValue, newValue) -> {
                                System.out.println("Updated location in Calendar - Old Value: " + oldValue + " New Value: " + newValue);
                                Main.calendarModel.updateLocation(oldValue, newValue);
                            });
                        }
                    }
                }
                if (change.wasRemoved()) {
                    for (javafx.scene.Node removedNode : change.getRemoved()) {
                        if (removedNode instanceof HBox) {
                            TextField textField = (TextField) ((HBox)removedNode).getChildren().get(3);
                            Main.calendarModel.deleteAction(textField.getText());
                            DatePicker datePicker = (DatePicker)((HBox)removedNode).getChildren().get(1);
                            Main.calendarModel.deleteLocalDate(datePicker.getValue());
                            TextField location = (TextField) ((HBox)removedNode).getChildren().get(4);
                            Main.calendarModel.deleteLocation(location.getText());
                        }
                    }
                }
            }
            System.out.println("Last state of Calendar: " + Main.calendarModel.actionListProperty().get());
        });

        Main.view.projectListWidget.vBox.getChildren().addListener((ListChangeListener<? super javafx.scene.Node>) change -> {//Project list
            while (change.next()) {
                if (change.wasAdded()) {
                    for (javafx.scene.Node addedNode : change.getAddedSubList()) {
                        if (addedNode instanceof HBox) {
                            TextField textField = (TextField) ((HBox)addedNode).getChildren().get(1);
                            Main.projectModel.addAction(textField.getText());

                            ((TextField)((HBox)addedNode).getChildren().get(1)).textProperty().addListener(new ChangeListener<String>() {
                                @Override
                                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                    System.out.println("Updated item in Project - Old Value: " + oldValue + " New Value: " + newValue);
                                    Main.projectModel.updateListProperty(oldValue, newValue);
                                }
                            });
                            System.out.println("Project List: " + Main.projectModel.actionListProperty().get());
                        }
                    }
                }
                if (change.wasRemoved()) {
                    for (javafx.scene.Node removedNode : change.getRemoved()) {
                        if (removedNode instanceof HBox) {
                            TextField textField = (TextField) ((HBox)removedNode).getChildren().get(1);
                            Main.projectModel.deleteAction(textField.getText());
                            System.out.println("Project List: " + Main.projectModel.actionListProperty().get());
                        }
                    }
                }
            }
            System.out.println("Last state of Project List: " + Main.projectModel.actionListProperty().get());
        });

        Main.view.somedayMaybeWidget.vBox.getChildren().addListener((ListChangeListener<? super javafx.scene.Node>) change -> {//Someday/Maybe list
            while (change.next()) {
                if (change.wasAdded()) {
                    for (javafx.scene.Node addedNode : change.getAddedSubList()) {
                        if (addedNode instanceof HBox) {
                            TextField textField = (TextField) ((HBox)addedNode).getChildren().get(0);
                            Main.somedayMaybeModel.addAction(textField.getText());
                            ((TextField)((HBox)addedNode).getChildren().get(0)).textProperty().addListener(new ChangeListener<String>() {
                                @Override
                                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                    System.out.println("Updated item in Someday/Maybe List - Old Value: " + oldValue + " New Value: " + newValue);
                                    Main.somedayMaybeModel.updateListProperty(oldValue, newValue);
                                }
                            });
                            System.out.println("Someday/Maybe List: " + Main.somedayMaybeModel.actionListProperty().get());
                        }
                    }
                }
                if (change.wasRemoved()) {
                    for (javafx.scene.Node removedNode : change.getRemoved()) {
                        if (removedNode instanceof HBox) {
                            TextField textField = (TextField) ((HBox)removedNode).getChildren().get(0);
                            Main.somedayMaybeModel.deleteAction(textField.getText());
                            System.out.println("Someday/Maybe List: " + Main.somedayMaybeModel.actionListProperty().get());
                        }
                    }
                }
            }
            System.out.println("Last state of Someday/Maybe List: " + Main.somedayMaybeModel.actionListProperty().get());
        });


        Main.view.doneListWidget.vBox.getChildren().addListener((ListChangeListener<? super javafx.scene.Node>) change -> {//Done list
            while (change.next()) {
                if (change.wasAdded()) {
                    for (javafx.scene.Node addedNode : change.getAddedSubList()) {
                        if (addedNode instanceof HBox) {
                            TextField textField = (TextField) ((HBox)addedNode).getChildren().get(0);
                            Main.doneListModel.addAction(textField.getText());
                            ((TextField)((HBox)addedNode).getChildren().get(0)).textProperty().addListener(new ChangeListener<String>() {
                                @Override
                                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                    System.out.println("Updated item in Done List - Old Value: " + oldValue + " New Value: " + newValue);
                                    Main.doneListModel.updateListProperty(oldValue, newValue);
                                }
                            });
                            System.out.println("Done List: " + Main.doneListModel.actionListProperty().get());
                        }
                    }
                }
                if (change.wasRemoved()) {
                    for (javafx.scene.Node removedNode : change.getRemoved()) {
                        if (removedNode instanceof HBox) {
                            TextField textField = (TextField) ((HBox)removedNode).getChildren().get(0);
                            Main.doneListModel.deleteAction(textField.getText());
                            System.out.println("Done List: " + Main.doneListModel.actionListProperty().get());
                        }
                    }
                }
            }
            System.out.println("Last state of Done List: " + Main.doneListModel.actionListProperty().get());
        });
    }

}
