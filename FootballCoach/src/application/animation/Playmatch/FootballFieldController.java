/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.Playmatch;

import application.animation.CalculateMatch.MainAIController;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

/**
 *
 * @author faris
 */
public class FootballFieldController {

    private static final int SLIDER_PRECISION = 9000;
    private boolean isDragging = false;
    private static final int AMOUNT_OF_SLICES = MainAIController.AMOUNT_OF_SLICES; // 21600

    @FXML
    private Text score;
    @FXML
    private TextField timeField;
    @FXML
    private ComboBox speedBox;
    @FXML
    private Slider timeSlider;
    @FXML
    private Text team1;
    @FXML
    private Text team2;
    @FXML
    private Text vs;
    @FXML
    private HBox teamsBox;
    @FXML
    private ImageView fieldPicture;
    @FXML
    private Button pauseButton;

    private final ArrayList<String> speedList = new ArrayList<>();

    // this event will be executed when a speed in the dropdown list is selected:
    private final EventHandler onSelected = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            int id = speedBox.getSelectionModel().getSelectedIndex();
            String speed = speedList.get(id);
            AnimateFootballMatch.setSpeed(Double.parseDouble(speed.replace('x', '0')));//do stuff
        }
    };

    private final ChangeListener<Number> sliderListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
            if (isDragging) {
                AnimateFootballMatch.setTime(newValue.intValue() * AMOUNT_OF_SLICES / SLIDER_PRECISION);
//                System.out.println("Slided: " + newValue);
            }
        }
    };

    private final EventHandler<KeyEvent> keyEventHandler
            = new EventHandler<KeyEvent>() {
                @Override
                public void handle(final KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.LEFT) {
                        keyLeftClickedOnSlider();
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                        keyRightClickedOnSlider();
                    }
                }
            };

    /**
     * Class to convert a slider value to the corresponding time
     */
    private class LabelFormatter extends StringConverter<Double> {

        @Override
        public String toString(Double num) {
            int minutes = num.intValue() * 90 / SLIDER_PRECISION;
            return minutes + ":" + "00";
        }

        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string.split(":")[0]) * 100;
        }

    }

    @FXML
    private void initialize() {
        // add speed options to dropdown list.
        speedList.add("x0.25");
        speedList.add("x0.5");
        speedList.add("x1");
        speedList.add("x1.5");
        speedList.add("x3");
        speedList.add("x5");
        speedList.add("x10");
        speedList.add("x25");
        speedBox.setItems(FXCollections.observableArrayList(speedList));

        // set the event for when an item is selected
        speedBox.addEventHandler(ActionEvent.ACTION, onSelected);

        // define time slider 
        timeSlider.setMin(0);
        timeSlider.setMax(SLIDER_PRECISION);
        timeSlider.setValue(0);
        timeSlider.setShowTickLabels(true);
        timeSlider.setShowTickMarks(true);
        timeSlider.setMajorTickUnit(SLIDER_PRECISION / 9);
        timeSlider.setMinorTickCount(1);
        timeSlider.setBlockIncrement(SLIDER_PRECISION / 45);

        // add change listener to the slides
        timeSlider.valueProperty().addListener(sliderListener);

        // set a formatter for the labels, so they dispaly the right times
        timeSlider.labelFormatterProperty().set(new LabelFormatter());

        // add key pressed listener, to support arrow keys
        timeSlider.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);

        // set the names of the two competing teams and set center their textfields at the middle line of the field
        team1.setText("PSV");
        team1.setEffect(new Lighting());
        team2.setText("Ajax");
        team2.setEffect(new Lighting());
        teamsBox.setLayoutX(fieldPicture.getLayoutBounds().getWidth() / 2
                - (team1.getLayoutBounds().getWidth() + teamsBox.getSpacing()
                + vs.getLayoutBounds().getWidth() / 2)); //set the box halfway past the field, and then move it, the size of the first teams name + the spacing + half of the size of the "vs." text, to the left
    }

    @FXML
    private void startedDragging() {
        isDragging = true;
    }

    @FXML
    private void stoppedDragging() {
        isDragging = false;
    }

    /**
     * set the score
     *
     * @param newScore a string containing the new score
     */
    public void setScore(String newScore) {
        if (!score.getText().equals(newScore)) {
            score.setText(newScore);
        }
    }

    public void setTime(int time) {
        // if the user is not altering the slider, add time to slider
        if (!isDragging) {
            timeSlider.setValue(time * SLIDER_PRECISION / AMOUNT_OF_SLICES);
        }

        time /= 4; // 0.25 seconds per time unit
        int minutes = time / 60;
        int seconds = time % 60;
        String secondsStr, minutesStr;

        if (seconds < 10) {
            secondsStr = "0" + seconds;
        } else {
            secondsStr = Integer.toString(seconds);
        }

        if (minutes < 10) {
            minutesStr = "0" + minutes;
        } else {
            minutesStr = Integer.toString(minutes);
        }

        timeField.setText(minutesStr + ":" + secondsStr + " / 90:00");
    }

    @FXML
    private void mouseClickedOnSlider() {
        AnimateFootballMatch.setTime((int) timeSlider.getValue() * AMOUNT_OF_SLICES / SLIDER_PRECISION);
//        System.out.println("Clicked: " + (int)timeSlider.getValue());
    }

    private void keyLeftClickedOnSlider() {
        if (timeSlider.getValue() > SLIDER_PRECISION / 180) {
            AnimateFootballMatch.setTime((int) (timeSlider.getValue() - SLIDER_PRECISION / 180) * AMOUNT_OF_SLICES / SLIDER_PRECISION);
        } else {
            AnimateFootballMatch.setTime(0); // go to start
        }
    }

    private void keyRightClickedOnSlider() {
//        System.out.println("Arrow key: " + timeSlider.getValue());
        if (timeSlider.getValue() < SLIDER_PRECISION - SLIDER_PRECISION / 180) {
            AnimateFootballMatch.setTime((int) (timeSlider.getValue() + SLIDER_PRECISION / 180) * AMOUNT_OF_SLICES / SLIDER_PRECISION); // go forward 3 minutes
        } else {
            AnimateFootballMatch.setTime(AMOUNT_OF_SLICES); // go to end
        }
    }

    @FXML
    private void togglePauseButton() {
        AnimateFootballMatch.togglePause();
        if (AnimateFootballMatch.isPause()) {
            pauseButton.setText("P"); // P for play -- REPLACE WITH PUCTURE
        } else {
            pauseButton.setText("S"); // S for stop -- REPLACE WITH PUCTURE
        }
    }

    @FXML
    private void continueButton() {
        AnimateFootballMatch.stopAnimation();
    }
}
