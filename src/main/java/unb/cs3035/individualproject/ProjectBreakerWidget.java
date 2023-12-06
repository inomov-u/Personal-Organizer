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
import javafx.scene.text.Text;

public class ProjectBreakerWidget  extends Pane {
    VBox vBox;
    GraphicsContext gc;
    private final double widgetWidth = 600;
    private final double widgetHeight = 300;
    private final double borderWidth = 5;
    private String projectName;
    ProjectBreakerWidget(String projectName)
    {
        this.projectName = projectName;
        Canvas canvas = new Canvas(widgetWidth, widgetHeight);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        drawNextActionList();
    }

    private void drawNextActionList()
    {
        //FRAME
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0, 0, widgetWidth, widgetHeight);
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(borderWidth,borderWidth, widgetWidth -borderWidth*2, widgetHeight -borderWidth*2);

        gc.fillRect(0, 30, widgetWidth, borderWidth);
        gc.setFill(Color.DARKSLATEGREY);
        gc.fillRoundRect(borderWidth,borderWidth,widgetWidth-borderWidth*2,25,20,20);

        // Header
        gc.setFont(javafx.scene.text.Font.font(23));
        gc.setFill(Color.GHOSTWHITE);
        gc.fillText(" " + projectName, 20, 25);

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
        addButton.setOnAction(e -> addItem());

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

    public void addItem() {

        TextField textField = new TextField("New Action/Calendar/Project/Someday");
        HBox.setHgrow(textField, Priority.ALWAYS);
        textField.setPrefHeight(20);
        textField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(textField.getText().equals("New Action/Calendar/Project/Someday")) textField.clear();
            }
        });

        Button addToNAL = new Button("Next Action List");
        addToNAL.setTooltip(new Tooltip("Add to NAL"));

        Button addToCalendar = new Button("Calendar");
        addToCalendar.setTooltip(new Tooltip("Add Action to Calendar"));

        Button addToProject = new Button("Project");
        addToProject.setTooltip(new Tooltip("Add to Project List"));

        Button addToSomedayMaybe = new Button("Someday/Maybe");
        addToSomedayMaybe.setTooltip(new Tooltip("Add to Someday/Maybe List"));

        Image deleteImage = new Image(getClass().getClassLoader().getResourceAsStream("Minus Red Button.png"));
        ImageView imageView2 = new ImageView(deleteImage);
        imageView2.setFitWidth(16); // Set the preferred width
        imageView2.setFitHeight(19); // Set the preferred height
        Button deleteButton = new Button("", imageView2);
        deleteButton.setTooltip(new Tooltip("Delete action"));

        HBox itemBox = new HBox( textField, addToNAL, addToCalendar, addToProject, addToSomedayMaybe, deleteButton);
        itemBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(itemBox);

        addToNAL.setOnAction(e -> addToNAL(itemBox, textField.getText()));
        addToCalendar.setOnAction(e -> addToCalendar(itemBox, textField.getText()));
        addToProject.setOnAction(e -> addToProject(itemBox, textField.getText()));
        addToSomedayMaybe.setOnAction(e -> addToSomedayMaybe(itemBox, textField.getText()));

        textField.setOnMouseClicked(e -> modifyItem(textField));
        deleteButton.setOnAction(e -> deleteItem(itemBox));
    }
    private void addToNAL(HBox itemBox, String text) {
        Main.view.nextActionListWidget.addItem(text);
        vBox.getChildren().remove(itemBox);
    }
    private void addToCalendar(HBox itemBox,String text) {
        Main.view.calendarWidget.addItem(text);
        vBox.getChildren().remove(itemBox);
    }
    private void addToProject(HBox itemBox, String pName) {
        Main.view.projectListWidget.addItem(pName + " (part of " + projectName + ")");
        vBox.getChildren().remove(itemBox);
    }
    private void addToSomedayMaybe(HBox itemBox, String text) {
        Main.view.somedayMaybeWidget.addItem(text);
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
