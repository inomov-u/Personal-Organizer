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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.Objects;


public class ProjectListWidget extends Pane {
    VBox vBox;
    GraphicsContext gc;
    private final double widgetWidth;
    private final double widgetHeight;

    ProjectListWidget(double widgetWidth, double widgetHeight)
    {
        this.widgetWidth = widgetWidth;
        this.widgetHeight = widgetHeight;

        Canvas canvas = new Canvas(widgetWidth, widgetHeight);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        drawNextActionList();
    }
    private void drawNextActionList()
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
        gc.fillText("Project", widgetWidth/2-50, 25);

        //Header button
        Image addImage = new Image(getClass().getClassLoader().getResourceAsStream("New Document.png"));
        ImageView imageView = new ImageView(addImage);
        imageView.setFitWidth(16); // Set the preferred width
        imageView.setFitHeight(19); // Set the preferred height
        Button addButton = new Button("", imageView);
        addButton.setTooltip(new Tooltip("Add Project"));
        this.getChildren().add(addButton);
        addButton.setLayoutX(widgetWidth- borderWidth *2 - 26);
        addButton.setLayoutY(borderWidth -1);
        addButton.setOnAction(e -> addItem("Project Name"));

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
    public void addItem(String projectName) {
        Image doneImage = new Image(getClass().getClassLoader().getResourceAsStream("Menu.png"));
        ImageView imageView = new ImageView(doneImage);
        imageView.setFitWidth(16); // Set the preferred width
        imageView.setFitHeight(19); // Set the preferred height
        Button doneButton = new Button("", imageView);
        doneButton.setTooltip(new Tooltip("Break project into smaller actions"));

        TextField textField = new TextField(projectName);
        HBox.setHgrow(textField, Priority.ALWAYS);
        textField.setPrefHeight(20);
        textField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(textField.getText().equals("Project Name")) textField.clear();
            }
        });

        Image deleteImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Minus Red Button.png")));
        ImageView imageView2 = new ImageView(deleteImage);
        imageView2.setFitWidth(16); // Set the preferred width
        imageView2.setFitHeight(19); // Set the preferred height
        Button deleteButton = new Button("", imageView2);
        deleteButton.setTooltip(new Tooltip("Delete project"));

        HBox itemBox = new HBox(doneButton, textField, deleteButton);
        itemBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(itemBox);

        doneButton.setOnAction(e -> breakDownProject(itemBox,textField.getText()));
        textField.setOnMouseClicked(e -> modifyItem(textField));
        deleteButton.setOnAction(e -> deleteItem(itemBox));
    }

    private void breakDownProject(HBox itemBox, String text) {
        Stage newStage = new Stage();
        ProjectBreakerWidget projectBreakerWidget = new ProjectBreakerWidget(text);

        newStage.setScene(new Scene(projectBreakerWidget));
        newStage.setTitle("Break down Project");
        newStage.show();

        String out = "Created action plan for project: " + text;
        Main.view.doneListWidget.addItem(out);
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