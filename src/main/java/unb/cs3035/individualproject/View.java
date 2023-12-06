package unb.cs3035.individualproject;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;


public class View extends Pane {
    StatisticalView statisticalView;
    Scene sceneForStatView;
    NextActionListWidget nextActionListWidget;
    CalendarWidget calendarWidget;
    ProjectListWidget projectListWidget;
    DoneListWidget doneListWidget;
    Button changeView;
    SomedayMaybeWidget somedayMaybeWidget;

    //Menu Items:
    MenuItem openMenuItem;
    MenuItem saveMenuItem;
    MenuItem exitMenuItem;
    MenuItem organizerViewMenuItem;
    MenuItem statisticalViewMenuItem;
    MenuItem helpScreenMenuItem;
    MenuItem aboutMenuItem;


    public View() {
        this.setWidth(Main.bounds.getWidth());
        this.setHeight(Main.bounds.getHeight());

        statisticalView= new StatisticalView(getWidth(),getHeight());
        sceneForStatView = new Scene(statisticalView,getWidth(),getHeight());

        initializeView();
    }

    private void initializeView() {


        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));

        //adding widgets
        nextActionListWidget = new NextActionListWidget(this.getWidth() * 0.25,this.getHeight()*0.45);//500,500

        calendarWidget = new CalendarWidget(this.getWidth() * 0.3,this.getHeight()*0.45);

        ClockWidget clockWidget = new ClockWidget(this.getWidth() * 0.4,this.getHeight()*0.2);

        doneListWidget = new DoneListWidget(this.getWidth() * 0.25,this.getHeight()*0.2);

        projectListWidget = new ProjectListWidget(this.getWidth() * 0.25,this.getHeight()*0.2);

        somedayMaybeWidget = new SomedayMaybeWidget(this.getWidth() * 0.25,this.getHeight()*0.2);

        changeView = new Button("Statistical View");
        changeView.setStyle(
                "-fx-background-color: #f2f2f2;" + // Light grey background
                        "-fx-border-color: #404040;"      // Dark grey border
        );

        this.getChildren().addAll(clockWidget, nextActionListWidget, calendarWidget, doneListWidget, projectListWidget, somedayMaybeWidget, changeView);

        //Positions
        clockWidget.setLayoutX(getWidth()*0.25);
        clockWidget.setLayoutY(5);

        nextActionListWidget.setLayoutX(this.getWidth() * 0.03);//3% screen gap       0.28
        nextActionListWidget.setLayoutY(this.getHeight()*0.2);

        calendarWidget.setLayoutX(this.getWidth() * 0.31);//0.31+0.3 = 0.61
        calendarWidget.setLayoutY(this.getHeight()*0.2);

        doneListWidget.setLayoutX(this.getWidth()*0.155); //start of arrow
        doneListWidget.setLayoutY(this.getHeight()*0.73);

        projectListWidget.setLayoutX(this.getWidth() * 0.64);//calendar ends at 0.61  + gap 0.03 = 0.64////  +0.25 ProjectListWidget = 0.89
        projectListWidget.setLayoutY(this.getHeight()*0.2);

        somedayMaybeWidget.setLayoutX(this.getWidth() * 0.64);
        somedayMaybeWidget.setLayoutY(this.getHeight()*0.45);

        changeView.setLayoutX(getWidth()*0.9);
        changeView.setLayoutY(getHeight()*0.04);

        //Menu
        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(getWidth());

        Menu fileMenu = new Menu("File");
        Menu viewMenu = new Menu("View");
        Menu helpMenu = new Menu("Help");

        openMenuItem = new MenuItem("Open");
        saveMenuItem = new MenuItem("Save");
        exitMenuItem = new MenuItem("Exit");

        organizerViewMenuItem = new MenuItem("Personal Organizer View");
        statisticalViewMenuItem = new MenuItem("Statistical View");

        helpScreenMenuItem = new MenuItem("Help Screen");
        aboutMenuItem = new MenuItem("About");

        fileMenu.getItems().addAll(openMenuItem, saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
        viewMenu.getItems().addAll(organizerViewMenuItem, statisticalViewMenuItem);
        helpMenu.getItems().addAll(helpScreenMenuItem, aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);

        getChildren().add(menuBar);

    }
    public void showHelpScreen() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help Screen");
        alert.setHeaderText("Helpful Information");

        alert.setContentText("- If you are not sure what a button does you can hold your cursor on it and tooltip will appear.\n" +
                "- Next Action List: this is where you store all of the actions that can be done at any point of time.\n" +
                "- Calendar List: this is where you store any task that requires specific time.\n" +
                "- Project List: some tasks cannot be achieved by one action such as Learning new language. Therefore you cannot list those actions in NAL or Calendar. First, Project Widget allows you to store name of your projects, then you can implement your project by breaking it into smaller one actionable steps. If project is too complex you can break it into smaller projects by adding tasks back to Project List and breaking them further.\n" +
                "- Someday/Maybe List: some of the things that you may want to do may come to your mind but currently you don't have time for that or bad circumstances. So you may want to add those ideas to Someday/Maybe list and do them in the future. Those ideas can be projects, one time actions or calendar so you may move tasks accordingly in the future.\n" +
                "- Done List: stores tasks that are already completed. It allows you to see how many things you actually do but do not usually remember. The purpose of this list to motivate you and push you forward to do more.\n");
        alert.showAndWait();
    }

    public void showAboutDialog() {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
        aboutDialog.setTitle("About");
        aboutDialog.setHeaderText("Personal Organizer Application");
        aboutDialog.setContentText("Created by: Usmon Inomov");

        Image companyLogo = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("logo.png")));;
        ImageView imageView = new ImageView(companyLogo);
        aboutDialog.setGraphic(imageView);

        aboutDialog.showAndWait();
    }

    public void showAlertDialog()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("This Feature will be added soon");

        alert.setContentText("Sorry for inconvenience");
        alert.showAndWait();
    }

    public void loadSplashScreen() {


    }
}
