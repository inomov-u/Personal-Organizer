package unb.cs3035.individualproject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class NextActionListWidget extends Pane {
    VBox vBox;
    GraphicsContext gc;
    private double widgetWidth;
    private double widgetHeight;
    Canvas canvas;
    private final double borderWidth = 5;
    NextActionListWidget(double widgetWidth, double widgetHeight)
    {
        this.widgetWidth = widgetWidth;
        this.widgetHeight = widgetHeight;
        canvas = new Canvas(widgetWidth, widgetHeight);
        gc = canvas.getGraphicsContext2D();

        this.getChildren().add(canvas);
        drawNextActionList();
    }

    private void drawNextActionList()
    {
        //FRAME
        gc.setFill(Color.INDIANRED);
        gc.fillRoundRect(0, 0, widgetWidth, widgetHeight,20,20);
        gc.setFill(Color.INDIANRED);
        gc.fillRect(borderWidth,borderWidth, widgetWidth -borderWidth*2, widgetHeight -borderWidth*2);

        gc.fillRect(0, 30, widgetWidth, borderWidth);
        gc.setFill(Color.DARKRED);
        gc.fillRoundRect(borderWidth,borderWidth,widgetWidth-borderWidth*2,25,20,20);

        // Header
        gc.setFont(javafx.scene.text.Font.font(26));
        gc.setFill(Color.GHOSTWHITE);
        gc.fillText("Next Action List", widgetWidth/2-100, 25);

        //Header button
        Image addImage = new Image(getClass().getClassLoader().getResourceAsStream("New Document.png"));
        ImageView imageView = new ImageView(addImage);
        imageView.setFitWidth(16); // Set the preferred width
        imageView.setFitHeight(19); // Set the preferred height
        Button addButton = new Button("", imageView);
        addButton.setTooltip(new Tooltip("Add Action"));
        this.getChildren().add(addButton);
        addButton.setLayoutX(widgetWidth-borderWidth*2 - 26);
        addButton.setLayoutY(borderWidth-1);
        addButton.setOnAction(e -> addItem("New Action"));

        //container for list
        double startX = 5;
        double startY = 35.0;
        vBox = new VBox();
        vBox.setLayoutX(startX); // Set X coordinate
        vBox.setLayoutY(startY);
        getChildren().add(vBox);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(widgetHeight-54);
        scrollPane.setPrefViewportWidth(widgetWidth- 25);
        scrollPane.setLayoutX(borderWidth);
        scrollPane.setLayoutY(35);

        getChildren().addAll(scrollPane);
        scrollPane.setOnScroll(this::handleScroll);
    }
    public void addItem(String action) {
        Image doneImage = new Image(getClass().getClassLoader().getResourceAsStream("Clear Green Button.png"));
        ImageView imageView = new ImageView(doneImage);
        imageView.setFitWidth(16); // Set the preferred width
        imageView.setFitHeight(19); // Set the preferred height
        Button doneButton = new Button("", imageView);
        doneButton.setTooltip(new Tooltip("Done"));

        TextField textField = new TextField(action);
        HBox.setHgrow(textField, Priority.ALWAYS);//WIDTH OF TEXTFIELD
        textField.setPrefHeight(20);
        textField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(textField.getText().equals("New Action")) textField.clear();
            }
        });

        Image deleteImage = new Image(getClass().getClassLoader().getResourceAsStream("Minus Red Button.png"));
        ImageView imageView2 = new ImageView(deleteImage);
        imageView2.setFitWidth(16); // Set the preferred width
        imageView2.setFitHeight(19); // Set the preferred height
        Button deleteButton = new Button("", imageView2);
        deleteButton.setTooltip(new Tooltip("Delete action"));

        HBox itemBox = new HBox(doneButton, textField, deleteButton);
        itemBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(itemBox);

        doneButton.setOnAction(e -> completeItem(itemBox, textField.getText()));
        textField.setOnMouseClicked(e -> modifyItem(textField));
        deleteButton.setOnAction(e -> deleteItem(itemBox));
    }
    private void completeItem(HBox itemBox, String text) {
        Main.view.doneListWidget.addItem(text);
        vBox.getChildren().remove(itemBox);
    }
    private void modifyItem(TextField actionTextField) {
        actionTextField.setEditable(true);
    }
    private void deleteItem(HBox itemBox) {
        vBox.getChildren().remove(itemBox);
    }
    private void handleScroll(ScrollEvent event) {
        double deltaY = event.getDeltaY();
        vBox.setLayoutY(vBox.getLayoutY() + deltaY);
    }
}
