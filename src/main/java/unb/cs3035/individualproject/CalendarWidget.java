package unb.cs3035.individualproject;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CalendarWidget extends Pane {
    VBox vBox;
    GraphicsContext gc;
    private final double widgetWidth;
    private final double widgetHeight;
    private final double borderWidth = 5;

    CalendarWidget(double widgetWidth, double widgetHeight)
    {
        this.widgetWidth = widgetWidth;
        this.widgetHeight = widgetHeight;
        Canvas canvas = new Canvas(widgetWidth, widgetHeight);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        drawCalendarList();
    }

    private void drawCalendarList()
    {
        //FRAME
        gc.setFill(Color.INDIANRED);//INDIANRED
        gc.fillRoundRect(0, 0, widgetWidth, widgetHeight,20,20);
        gc.setFill(Color.INDIANRED);
        gc.fillRect(borderWidth,borderWidth, widgetWidth -borderWidth*2, widgetHeight -borderWidth*2);

        gc.fillRect(0, 30, widgetWidth, borderWidth);
        gc.setFill(Color.DARKRED);
        gc.fillRoundRect(borderWidth,borderWidth,widgetWidth-borderWidth*2,25,20,20);

        // Header
        gc.setFont(javafx.scene.text.Font.font(26));
        gc.setFill(Color.GHOSTWHITE);
        gc.fillText("Calendar", widgetWidth/2-45, 25);

        //Header button
        Image addImage = new Image(getClass().getClassLoader().getResourceAsStream("New Document.png"));
        ImageView imageView = new ImageView(addImage);
        imageView.setFitWidth(16); // Set the preferred width
        imageView.setFitHeight(19); // Set the preferred height
        Button addButton = new Button("", imageView);
        addButton.setTooltip(new Tooltip("Add Calendar"));
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

        //Scroller
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);

        scrollPane.setPrefViewportHeight(widgetHeight-54);
        scrollPane.setPrefViewportWidth(widgetWidth- 25);
        scrollPane.setLayoutX(borderWidth);
        scrollPane.setLayoutY(35);

        getChildren().addAll(scrollPane);
        scrollPane.setOnScroll(this::handleScroll);
    }

    //TODO maybe we should add text item to model and this widget gets updated depending on whats inside the model
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
        textField.setTooltip(new Tooltip("Action"));
        textField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(textField.getText().equals("New Action"))
                    textField.clear();
            }
        });

        Image deleteImage = new Image(getClass().getClassLoader().getResourceAsStream("Minus Red Button.png"));
        ImageView imageView2 = new ImageView(deleteImage);
        imageView2.setFitWidth(16); // Set the preferred width
        imageView2.setFitHeight(19); // Set the preferred height
        Button deleteButton = new Button("", imageView2);
        deleteButton.setTooltip(new Tooltip("Delete action"));

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(110);
        datePicker.setTooltip(new Tooltip("Date"));

        ComboBox<String> timeComboBox = createTimeComboBox();
        timeComboBox.setMinWidth(75);
        timeComboBox.setTooltip(new Tooltip("Time"));

        TextField locationField = new TextField("Location");
        locationField.setPrefHeight(20);
        locationField.setTooltip(new Tooltip("Location"));
        locationField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(locationField.getText().equals("Location")) locationField.clear();
            }
        });

        HBox itemBox = new HBox(doneButton, datePicker, timeComboBox, textField, locationField, deleteButton );
        itemBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(itemBox);

        doneButton.setOnAction(e -> completeItem(itemBox, textField.getText()));
        textField.setOnMouseClicked(e -> modifyItem(textField));
        deleteButton.setOnAction(e -> deleteItem(itemBox));
    }

    private ComboBox<String> createTimeComboBox() {
        ComboBox<String> timeComboBox = new ComboBox<>();
        for (int hour = 0; hour <= 23; hour++) {
            for (int minute = 0; minute <= 59; minute += 15) {
                String formattedTime = String.format("%02d:%02d", hour, minute);
                timeComboBox.getItems().add(formattedTime);
            }
        }
        timeComboBox.setValue("12:00"); // Default value
        return timeComboBox;
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
