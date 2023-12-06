package unb.cs3035.individualproject;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Objects;

public class SomedayMaybeWidget extends Pane {


    VBox vBox;
    GraphicsContext gc;
    private final double widgetWidth;
    private final double widgetHeight;
    SomedayMaybeWidget(double widgetWidth, double widgetHeight)
    {
        this.widgetWidth = widgetWidth;
        this.widgetHeight = widgetHeight;
        Canvas canvas = new Canvas(widgetWidth, widgetHeight);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        drawSomedayMaybe();
    }

    private void drawSomedayMaybe()
    {
        double borderWidth = 5;

        //FRAME
        gc.setFill(Color.ORANGE);
        gc.fillRoundRect(0, 0, widgetWidth, widgetHeight,20,20);
        gc.setFill(Color.ORANGE);
        gc.fillRect(borderWidth, borderWidth, widgetWidth - borderWidth *2, widgetHeight - borderWidth *2);

        gc.fillRect(0, 30, widgetWidth, borderWidth);
        gc.setFill(Color.DARKORANGE);
        gc.fillRoundRect(borderWidth, borderWidth,widgetWidth- borderWidth *2,25,20,20);

        // Header
        gc.setFont(javafx.scene.text.Font.font(26));
        gc.setFill(Color.GHOSTWHITE);
        gc.fillText("Someday/Maybe", widgetWidth/2-95, 25);

        //Header button
        Image addImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("New Document.png")));
        ImageView imageView = new ImageView(addImage);
        imageView.setFitWidth(16); // Set the preferred width
        imageView.setFitHeight(19); // Set the preferred height
        Button addButton = new Button("", imageView);
        addButton.setTooltip(new Tooltip("Add Someday/Maybe Action or Project"));
        this.getChildren().add(addButton);
        addButton.setLayoutX(widgetWidth- borderWidth *2 - 26);
        addButton.setLayoutY(borderWidth -1);
        addButton.setOnAction(e -> addItem("Someday/Maybe Action/Project"));

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

    public void addItem(String text) {

        TextField textField = new TextField(text);
        HBox.setHgrow(textField, Priority.ALWAYS);
        textField.setPrefHeight(20);
        textField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(textField.getText().equals("Someday/Maybe Action/Project")) textField.clear();
            }
        });

        Button addToNAL = new Button("Next Action List");
        addToNAL.setTooltip(new Tooltip("Add to NAL"));

        Button addToCalendar = new Button("Calendar");
        addToCalendar.setTooltip(new Tooltip("Add Action to Calendar"));

        Button addToProject = new Button("Project");
        addToProject.setTooltip(new Tooltip("Add to Project List"));

        Image deleteImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Minus Red Button.png")));
        ImageView imageView2 = new ImageView(deleteImage);
        imageView2.setFitWidth(16); // Set the preferred width
        imageView2.setFitHeight(19); // Set the preferred height
        Button deleteButton = new Button("", imageView2);
        deleteButton.setTooltip(new Tooltip("Delete action"));

        HBox itemBox = new HBox( textField, addToNAL, addToCalendar, addToProject, deleteButton);
        itemBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(itemBox);

        addToNAL.setOnAction(e -> addToNAL(itemBox, textField.getText()));
        addToCalendar.setOnAction(e -> addToCalendar(itemBox, textField.getText()));
        addToProject.setOnAction(e -> addToProject(itemBox, textField.getText()));
        textField.setOnMouseClicked(e -> modifyItem(textField));
        deleteButton.setOnAction(e -> deleteItem(itemBox));
    }
    private void addToNAL(HBox itemBox, String text) {
        Main.view.nextActionListWidget.addItem(text);
        vBox.getChildren().remove(itemBox);
    }
    private void addToCalendar(HBox itemBox, String text) {
        Main.view.calendarWidget.addItem(text);
        vBox.getChildren().remove(itemBox);
    }
    private void addToProject(HBox itemBox, String pName) {
        Main.view.projectListWidget.addItem(pName);
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
