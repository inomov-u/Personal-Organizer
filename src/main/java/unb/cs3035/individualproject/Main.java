package unb.cs3035.individualproject;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;

        primaryStage.setTitle("Personal Organizer");
        mainScene = new Scene(view,bounds.getWidth(),bounds.getHeight());
        primaryStage.setScene(mainScene);

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
