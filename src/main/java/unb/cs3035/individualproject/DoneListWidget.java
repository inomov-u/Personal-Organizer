package unb.cs3035.individualproject;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DoneListWidget extends Pane {
    VBox vBox;
    GraphicsContext gc;
    private final double widgetWidth;
    private final double widgetHeight;
    private final double borderWidth = 5;
    DoneListWidget(double widgetWidth, double widgetHeight) {
        this.widgetWidth = widgetWidth;
        this.widgetHeight = widgetHeight;
        Canvas canvas = new Canvas(widgetWidth, widgetHeight);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        drawNextActionList();
    }
    private void drawNextActionList() {
        //FRAME
        gc.setFill(Color.DARKSEAGREEN);
        gc.fillRoundRect(0, 0, widgetWidth, widgetHeight, 20, 20);
        gc.setFill(Color.DARKSEAGREEN);
        gc.fillRect(borderWidth, borderWidth, widgetWidth - borderWidth * 2, widgetHeight - borderWidth * 2);

        gc.fillRect(0, 30, widgetWidth, borderWidth);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(borderWidth, borderWidth, widgetWidth - borderWidth * 2, 25, 20, 20);

        // Header
        gc.setFont(javafx.scene.text.Font.font(26));
        gc.setFill(Color.GHOSTWHITE);
        gc.fillText("Done List", widgetWidth/2-55, 25);

        //container for list
        double startX = 5;
        double startY = 35.0;
        vBox = new VBox();
        vBox.setLayoutX(startX); // Set X coordinate
        vBox.setLayoutY(startY);
        getChildren().add(vBox);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(widgetHeight - 54);
        scrollPane.setPrefViewportWidth(widgetWidth - 25);
        scrollPane.setLayoutX(borderWidth);
        scrollPane.setLayoutY(35);

        getChildren().addAll(scrollPane);
        scrollPane.setOnScroll(this::handleScroll);
    }
    public void addItem(String actionText) {
        TextField textField = new TextField(actionText);
        HBox.setHgrow(textField, Priority.ALWAYS);//WIDTH OF TEXTFIELD
        textField.setPrefHeight(20);

        Image deleteImage = new Image(getClass().getClassLoader().getResourceAsStream("Minus Red Button.png"));
        ImageView imageView2 = new ImageView(deleteImage);
        imageView2.setFitWidth(16); // Set the preferred width
        imageView2.setFitHeight(19); // Set the preferred height
        Button deleteButton = new Button("", imageView2);
        deleteButton.setTooltip(new Tooltip("Delete action"));

        HBox itemBox = new HBox(textField, deleteButton);
        itemBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(itemBox);

        deleteButton.setOnAction(e -> deleteItem(itemBox));
    }
    private void deleteItem(HBox itemBox) {
        vBox.getChildren().remove(itemBox);
    }
    private void handleScroll(ScrollEvent event) {
        double deltaY = event.getDeltaY();
        vBox.setLayoutY(vBox.getLayoutY() + deltaY);
    }
}