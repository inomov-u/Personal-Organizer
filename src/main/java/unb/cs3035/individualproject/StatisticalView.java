package unb.cs3035.individualproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

import java.util.Calendar;

public class StatisticalView extends Pane {
    private ObservableList<ModelData> modelDataList;
    GraphicsContext gc;
    public Button changeView;
    MenuItem openMenuItem;
    MenuItem saveMenuItem;
    MenuItem exitMenuItem;

    MenuItem organizerViewMenuItem;
    MenuItem statisticalViewMenuItem;

    MenuItem helpScreenMenuItem;
    MenuItem aboutMenuItem;


    public StatisticalView(double width, double height) {
        modelDataList = FXCollections.observableArrayList(
                        new StatisticalView.ModelData("Next Action List", Main.nextActionListModel.actionListProperty().size(), Color.RED),
                        new StatisticalView.ModelData("Calendar", Main.calendarModel.actionListProperty().size(), Color.INDIANRED),
                        new StatisticalView.ModelData("Project", Main.projectModel.actionListProperty().size(), Color.DARKORANGE),
                        new StatisticalView.ModelData("Someday/Maybe", Main.somedayMaybeModel.actionListProperty().size(), Color.ORANGE),
                        new StatisticalView.ModelData("Done", Main.doneListModel.actionListProperty().size(), Color.GREEN)

        );

        this.setWidth(width);
        this.setHeight(height);

        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
        Canvas canvas = new Canvas(getWidth()*0.08, getHeight()*0.18);
        gc = canvas.getGraphicsContext2D();
        canvas.setLayoutX(width/2 - canvas.getWidth()/2);
        canvas.setLayoutY(height*0.05);
        this.getChildren().add(canvas);
        addMenu();
        drawAnimatedClock();

        changeView = new Button("Organizer View");
        changeView.setStyle(
                "-fx-background-color: #f2f2f2;" + // Light grey background
                        "-fx-border-color: #404040;"      // Dark grey border
        );
        changeView.setLayoutX(getWidth()*0.9);
        changeView.setLayoutY(getHeight()*0.04);
        getChildren().add(changeView);

        drawPieChart();

        Main.nextActionListModel.actionListProperty().sizeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                modelDataList.get(0).size = Main.nextActionListModel.actionListProperty().size();
                getChildren().remove(getChildren().size()-1);
                drawPieChart();
            }
        });
        Main.calendarModel.actionListProperty().sizeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                modelDataList.get(1).size = Main.calendarModel.actionListProperty().size();
                getChildren().remove(getChildren().size()-1);
                drawPieChart();
            }
        });
        Main.projectModel.actionListProperty().sizeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                modelDataList.get(2).size = Main.projectModel.actionListProperty().size();
                getChildren().remove(getChildren().size()-1);
                drawPieChart();
            }
        });
        Main.somedayMaybeModel.actionListProperty().sizeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                modelDataList.get(3).size = Main.somedayMaybeModel.actionListProperty().size();
                getChildren().remove(getChildren().size()-1);
                drawPieChart();
            }
        });
        Main.doneListModel.actionListProperty().sizeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                modelDataList.get(4).size = Main.doneListModel.actionListProperty().size();
                getChildren().remove(getChildren().size()-1);
                drawPieChart();
            }
        });
    }

    private void addMenu()
    {
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

    private void drawPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.setData(getPieChartData());

        // Set the layout for the pie chart
        pieChart.setPrefSize(getWidth()*0.60, getHeight()*0.60);
        pieChart.setLayoutX(getWidth()/2 - pieChart.getPrefWidth()/2);
        pieChart.setLayoutY(getHeight()*0.24);
        getChildren().addAll(pieChart);
    }

    private ObservableList<PieChart.Data> getPieChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        double totalSize = modelDataList.stream().mapToDouble(ModelData::getSize).sum();

        for (ModelData modelData : modelDataList) {
            double percentage = (modelData.getSize() / totalSize) * 100.0;
            PieChart.Data data = new PieChart.Data(modelData.getName() + " " + String.format("%.1f%%", percentage), modelData.getSize());
            pieChartData.add(data);
        }

        return pieChartData;
    }

    public static class ModelData {
        private String name;
        private double size;
        private Color color;
        public ModelData(String name, double size, Color color) {
            this.name = name;
            this.size = size;
            this.color = color;
        }
        public String getName() {
            return name;
        }
        public double getSize() {
            return size;
        }
        public Color getColor() {
            return color;
        }
    }

    private void drawAnimatedClock()
    {
        gc.setLineWidth(3);
        gc.setFill(Color.RED);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> drawClock()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void drawClock() {
        double centerX = gc.getCanvas().getWidth() / 2;
        double centerY = gc.getCanvas().getHeight() / 2;

        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        gc.strokeOval(10, 5, gc.getCanvas().getWidth() - 15, gc.getCanvas().getHeight() - 15);

        Calendar calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.SECOND);
        int minutes = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR);

        drawHand( centerX, centerY, seconds * 6, 80);
        drawHand( centerX, centerY, minutes * 6 + seconds / 10.0, 60);
        drawHand( centerX, centerY, hours * 30 + minutes / 2.0, 40);

        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30);
            double x = centerX + 0.8 * (gc.getCanvas().getWidth() / 2) * Math.sin(angle);
            double y = centerY - 0.8 * (gc.getCanvas().getHeight() / 2) * Math.cos(angle);

            gc.fillText(Integer.toString(i), x, y);
        }
    }
    private void drawHand(double x, double y, double angle, double length) {
        double endX = x + length * Math.sin(Math.toRadians(angle));
        double endY = y - length * Math.cos(Math.toRadians(angle));
        gc.strokeLine(x, y, endX, endY);
    }
}
