package unb.cs3035.individualproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClockWidget extends Pane {
    private GraphicsContext gc;
    ClockWidget(double width, double height)
    {
        Canvas canvas = new Canvas(width, height);

        gc = canvas.getGraphicsContext2D();

        this.getChildren().add(canvas);

        drawDigitalClock();
    }
    private void drawDigitalClock()
    {
        Label clockLabel = createClockLabel();

        clockLabel.setLayoutX(58);
        clockLabel.setLayoutY(80);
        clockLabel.setScaleX(8);
        clockLabel.setScaleY(8);
        clockLabel.setOpacity(0.1);

        getChildren().add(clockLabel);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateClock(clockLabel)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private Label createClockLabel() {
        Label label = new Label();
        label.setStyle("-fx-font-size: 24px;");

        updateClock(label);

        return label;
    }
    private void updateClock(Label label) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        label.setText(currentTime);
    }






}
