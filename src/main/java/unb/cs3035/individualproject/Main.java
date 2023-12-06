package unb.cs3035.individualproject;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Objects;

public class Main extends Application {
    public static Screen primaryScreen = Screen.getPrimary();
    public static Rectangle2D bounds = primaryScreen.getVisualBounds();
    public static Model nextActionListModel = new Model();
    public static CalendarModel calendarModel = new CalendarModel();
    public static Model projectModel = new Model();
    public static Model somedayMaybeModel = new Model();
    public static Model doneListModel = new Model();
    public static final View view = new View();
    public static final Controller controller = new Controller();
    public static Stage primaryStage;
    public static Scene mainScene;
    Stage splashStage;
    @Override
    public void start(Stage primaryStage) throws Exception {

        Main.primaryStage = primaryStage;

        primaryStage.setTitle("Personal Organizer");
        mainScene = new Scene(view,bounds.getWidth(),bounds.getHeight());

        //Splash Screen
        Image addImage = new Image(getClass().getClassLoader().getResourceAsStream("mobile.jpg"));
        ImageView imageView = new ImageView(addImage);
        imageView.setFitWidth(bounds.getWidth());
        imageView.setFitHeight(bounds.getHeight());

        StackPane stackPane = new StackPane(imageView);
        stackPane.setStyle("-fx-background-color: #336699;"); // Set background color
        Label label = new Label("My Personal Organizer");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");

        StackPane spacer = new StackPane();
        // Set a width for the left spacer
        spacer.setMinWidth(400);

        HBox hBox = new HBox(label,spacer);
        hBox.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(hBox);

        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);

        // fade effect
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), hBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), hBox);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        fadeIn.play();

        fadeIn.setOnFinished((e) -> fadeOut.play());

        fadeOut.setOnFinished((e) -> {
            Main.primaryStage.setScene(Main.mainScene);

        });

        System.out.println("Your screen Width: " + mainScene.getWidth() + " Height: " + mainScene.getHeight());

        //Example data
        view.nextActionListWidget.addItem("Example 1: Clean my room.");
        view.nextActionListWidget.addItem("Example 2: Read 10 pages. Book name: How to get things done.");
        view.nextActionListWidget.addItem("Example 3: Read from p 11 to page 20. Book name: How to get things done.");
        view.nextActionListWidget.addItem("Example 4: Read from p 21 to page 30. Book name: How to get things done.");
        view.nextActionListWidget.addItem("Example 5: Practice what I learned from book by creating Next Action List.");
        view.calendarWidget.addItem("Example 1: Go to Medical Center.");
        view.calendarWidget.addItem("Example 2: Do final test.");
        view.projectListWidget.addItem("Example 1: Learn Python.");
        view.projectListWidget.addItem("Example 2: Do individual project from BUI course.");
        view.projectListWidget.addItem("Example 3: Finish How to get things done book.");
        view.somedayMaybeWidget.addItem("Example 1: Visit France");
        view.somedayMaybeWidget.addItem("Example 2: Create a meal plan.");
        view.somedayMaybeWidget.addItem("Example 3: Read Machine Learning Book.");
        view.doneListWidget.addItem("Example 1: Buy groceries.");
        view.doneListWidget.addItem("Example 2: Buy a present for my friend.");
        view.doneListWidget.addItem("Example 3: Do assignment 3.");

        primaryStage.setMaximized(true);
        primaryStage.show();

    }
}
