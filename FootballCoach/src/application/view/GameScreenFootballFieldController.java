/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.animation.CalculateMatch.MainAIController;
import application.animation.Playmatch.AnimateFootballMatch;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

/**
 * The controller class of the FootballField.fxml view
 *
 * @author Faris
 */
public class GameScreenFootballFieldController implements ViewControllerInterface {

    private static final int SLIDER_PRECISION = 9000;
    private boolean isDragging = false;
    private static final int AMOUNT_OF_SLICES = MainAIController.AMOUNT_OF_FRAMES; // 21600
    private final ArrayList<String> speedList = new ArrayList<>();

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
    private ImageView pauseButton;
    @FXML
    private Group fieldGroup;
    
    private static Main mainController;

    /**
     * this event will be executed when a speed in the dropdown list is selected
     */
    private final EventHandler onSelected = new EventHandler<ActionEvent>() {

        /**
         * Handle the event: change speed to selected speed
         *
         * @param t the event to handle
         */
        @Override
        public void handle(ActionEvent t) {
            int id = speedBox.getSelectionModel().getSelectedIndex();
            String speed = speedList.get(id);
            AnimateFootballMatch.setSpeed(Double.parseDouble(speed.replace('x', '0')));
        }
    };

    /**
     * this event will be executed when the slider is dragged by the user
     */
    private final ChangeListener<Number> sliderListener = new ChangeListener<Number>() {

        /**
         * Handle event: observed value changes. When it changes and isDragging
         * is true (the slider is being dragged), then change the time in the
         * AnimateFootballMatch class to the selected time.
         *
         * @param ov observable value which changed
         * @param oldValue the old value
         * @param newValue the new value
         */
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
            if (isDragging) {
                AnimateFootballMatch.setTime(newValue.intValue() * AMOUNT_OF_SLICES / SLIDER_PRECISION);
//                System.out.println("Slided: " + newValue);
            }
        }
    };

    /**
     * this event will be executed when the arrow keys are used when the slider
     * is selected
     */
    private final EventHandler<KeyEvent> keyEventHandler
            = new EventHandler<KeyEvent>() {
                /**
                 * Handle the event: when arrow key is clicked while the slider
                 * is selected, change the value of the slider
                 *
                 * @param keyEvent arrow clicked event (other keys are ignored)
                 */
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
     * Class to convert a slider value to the corresponding time in string
     * format
     */
    private class LabelFormatter extends StringConverter<Double> {

        /**
         * Convert slider value to string representation of the time
         *
         * @param num slider value
         * @return string representation of the time
         */
        @Override
        public String toString(Double num) {
            int minutes = num.intValue() * 90 / SLIDER_PRECISION;
            return minutes + ":" + "00";
        }

        /**
         * Convert string representation of the time to slider value
         *
         * @param string string representation of the time
         * @return slider value
         */
        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string.split(":")[0]) * 100;
        }

    }

    /**
     * This code is executed when the view is loaded. It sets the main texts and
     * variables of this view.
     */
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
        timeSlider.setMajorTickUnit(SLIDER_PRECISION / 3);
        timeSlider.setMinorTickCount(1);
        timeSlider.setBlockIncrement(SLIDER_PRECISION / 180);

        // add change listener to the slides
        timeSlider.valueProperty().addListener(sliderListener);

        // set a formatter for the labels, so they dispaly the right times
        timeSlider.labelFormatterProperty().set(new LabelFormatter());

        // add key pressed listener, to support arrow keys
        timeSlider.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);

        // set the names of the two competing teams and set center their textfields at the middle line of the field
        team1.setText(Main.getChosenTeamName());
        team2.setText(GameScreenPLAYMATCHController.getOpponent());
     }

    /**
     * Method that sets the variable isDragging to true when the slider is
     * dragged.
     */
    @FXML
    private void startedDragging() {
        isDragging = true;
    }

    /**
     * Method that sets the variable isDragging to false when the slider is not
     * dragged anymore.
     */
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

    /**
     * Set the time the slider is currently pointing at
     *
     * @param time the time to set the slider to
     */
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

    /**
     * This method is executed when the slider is clicked: it changes the time
     * of the animation
     */
    @FXML
    private void mouseClickedOnSlider() {
        AnimateFootballMatch.setTime((int) timeSlider.getValue() * AMOUNT_OF_SLICES / SLIDER_PRECISION);
//        System.out.println("Clicked: " + (int)timeSlider.getValue());
    }

    /**
     * This method is executed when the left arrow key is clicked while the
     * slider is selected: it changes the time of the animation
     */
    private void keyLeftClickedOnSlider() {
        if (timeSlider.getValue() > SLIDER_PRECISION / 180) {
            AnimateFootballMatch.setTime((int) (timeSlider.getValue() - SLIDER_PRECISION / 180) * AMOUNT_OF_SLICES / SLIDER_PRECISION);
        } else {
            AnimateFootballMatch.setTime(0); // go to start
        }
    }

    /**
     * This method is executed when the right arrow key is clicked while the
     * slider is selected: it changes the time of the animation
     */
    private void keyRightClickedOnSlider() {
//        System.out.println("Arrow key: " + timeSlider.getValue());
        if (timeSlider.getValue() < SLIDER_PRECISION - SLIDER_PRECISION / 180) {
            AnimateFootballMatch.setTime((int) (timeSlider.getValue() + SLIDER_PRECISION / 180) * AMOUNT_OF_SLICES / SLIDER_PRECISION); // go forward 3 minutes
        } else {
            AnimateFootballMatch.setTime(AMOUNT_OF_SLICES); // go to end
        }
    }

    /**
     * This method is executed when the pause utton is clicked: it toggles the
     * pause event of the animation
     */
    @FXML
    private void togglePauseButton() {
        AnimateFootballMatch.togglePause();
        if (AnimateFootballMatch.isPause()) {
            pauseButton.setImage(new Image(getClass().getResourceAsStream("../img/next.png")));
        } else {
            pauseButton.setImage(new Image(getClass().getResourceAsStream("../img/pause.png")));
        }
    }

    /**
     * This method is executed when the Continue button is clicked: it closes
     * the animation window and continues the main application
     */
    @FXML
    private void continueButton() {
        mainController.setCenterView("GameScreenPLAYMATCH");
    }
    
    /**
     * This gives this class a reference to the main class
     *
     * @param mainController the main class
     */
    @Override
    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }     

    public Group getFieldGroup() {
        return fieldGroup;
    }
}
