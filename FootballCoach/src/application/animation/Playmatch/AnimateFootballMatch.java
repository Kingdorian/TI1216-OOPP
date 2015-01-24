package application.animation.Playmatch;

import application.animation.Container.CalculatedMatch;
import application.animation.Container.PositionFrame;
import application.view.GameScreenFootballFieldController;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * AnimaFootballMatch plays a football match, based on positions saved in a
 * Match object.
 *
 * @author Faris
 */
public class AnimateFootballMatch {

    // The amount of slides to play per second. Change this to change the playing speed.
    private static final int MILLISECONDS_PER_FRAME = 245; // base speed (at x1 speed)
    private static int speed = MILLISECONDS_PER_FRAME; // speed to play at

    // Circles representing the players
    private static final Circle playerCircle[] = new Circle[11];
    private static final Circle adversaryCircle[] = new Circle[11];
    private static Circle ballCircle;
    private static Timeline timeline;

    // Integer holding the elapsed time
    private static int time = 0;

    // The footballMatch which is being animated
    private static CalculatedMatch footballMatch;

    private static int playerPause = 0; // if this is 1, there is a pause, if this is 2, there was a pause last slice, if it is 3, still setting up new positions, if this is 0, there is no pause
    private static GameScreenFootballFieldController viewController;

    // stop playing if this is true
    private static boolean pause = false;

    // Event handler, will be activated when a frame is done playing
    private final static EventHandler onFinished = (EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            if(footballMatch != null)
                if (time < footballMatch.amoutOfFrames() - 1 && !pause) {
                    time++;
                    playTimeSlice();
                } else{
                    // update the score
                    viewController.setScore("Score: " + footballMatch.getPosition(time).getScoreLeft() + " - " + footballMatch.getPosition(time).getScoreRight());

                    // update the time
                    viewController.setTime(time);
                }
        }
    };

    /**
     * Play the match defined in the Match parameter
     *
     * @param footballMatch Match containing a full match to play
     * @param viewController the controller of the view
     * @param pane The pane of the animation
     */
    public static void playMatch(CalculatedMatch footballMatch, GameScreenFootballFieldController viewController, Pane pane) {
        AnimateFootballMatch.viewController = viewController;
        AnimateFootballMatch.footballMatch = footballMatch;

        // get starting positions and increase time variable
        PositionFrame startPositions = footballMatch.getPosition(time++);

        // draw the circles
        for (int i = 0; i < 11; i++) {
            // if player position is defined, set the position and set the circle to visible, else set it to not-visible and don't specify a position
            if (startPositions.getPlayerAlly(i) != null) {
                playerCircle[i] = new Circle(startPositions.getPlayerAlly(i).getxAnimation(), startPositions.getPlayerAlly(i).getyAnimation(), 8, Color.BLUE);
                playerCircle[i].setEffect(new Lighting());
                playerCircle[i].setVisible(true);
            } else {
                playerCircle[i] = new Circle(8, Color.BLUE);
                playerCircle[i].setEffect(new Lighting());
                playerCircle[i].setVisible(false);
            }

            // if player position is defined, set the position and set the circle to visible, else set it to not-visible and don't specify a position
            if (startPositions.getPlayerAdversary(i) != null) {
                adversaryCircle[i] = new Circle(startPositions.getPlayerAdversary(i).getxAnimation(), startPositions.getPlayerAdversary(i).getyAnimation(), 8, Color.RED);
                adversaryCircle[i].setEffect(new Lighting());
                adversaryCircle[i].setVisible(true);
            } else {
                adversaryCircle[i] = new Circle(8, Color.RED);
                adversaryCircle[i].setEffect(new Lighting());
                adversaryCircle[i].setVisible(false);
            }

            // add circles to the group of the field image
            viewController.getFieldGroup().getChildren().addAll(playerCircle[i], adversaryCircle[i]);
        }

        // draw the ball
        if (startPositions.getBallPosition() != null) {
            ballCircle = new Circle(startPositions.getBallPosition().getxAnimation(), startPositions.getBallPosition().getyAnimation(), 5, Color.WHITE);
            ballCircle.setEffect(new Lighting());
            ballCircle.setVisible(true);
        } else {
            ballCircle = new Circle(5, Color.WHITE);
            ballCircle.setEffect(new Lighting());
            ballCircle.setVisible(false);
        }
        // add the ball to the scene
       viewController.getFieldGroup().getChildren().add(ballCircle);

        // Play the first TimeSlice, following time slices will be played by the event handler
        if (footballMatch.amoutOfFrames() > 1) {
            playTimeSlice();
        }
    }

    /**
     * Play a TimeSlice from the footballMatch, based on the current time
     * variable
     */
    private static void playTimeSlice() {

        // declare a time line, key frames to later add to the time line and key values to put in the key frames
        timeline = new Timeline();
        KeyValue kvX, kvY;
        KeyFrame kf;
        if (footballMatch.getPosition(time).isPause()) {
            kf = new KeyFrame(Duration.millis(1000/*speed * 4*/), onFinished);
            playerPause = 1;
        } else if (playerPause == 1) {
            kf = new KeyFrame(Duration.millis(0), onFinished);
            playerPause = 2;
        } else {
            playerPause = playerPause == 2 ? 3 : 0;
            kf = new KeyFrame(Duration.millis(speed), onFinished);
        }

        timeline.getKeyFrames().add(kf); // use this keyframe to trigger onFinished once, after the animation is finished

        for (int i = 0; i < 11; i++) {
            // get location which circle1 and circle2 should move to
            if (footballMatch.getPosition(time).getPlayerAlly(i) != null && footballMatch.getPosition(time).getPlayerAlly(i).isOnField()) {
                playerCircle[i].setVisible(playerPause < 2); // only visible if there wasn't a pause before this slice
                playerCircle[i].setEffect(new Lighting());

                // get location which playerCircle[i] should move to
                kvX = new KeyValue(playerCircle[i].centerXProperty(), footballMatch.getPosition(time).getPlayerAlly(i).getxAnimation(), Interpolator.LINEAR);
                kvY = new KeyValue(playerCircle[i].centerYProperty(), footballMatch.getPosition(time).getPlayerAlly(i).getyAnimation(), Interpolator.LINEAR);

                // set the locations to keyframes
                kf = new KeyFrame(Duration.millis(speed)/*,onFinished*/, kvX, kvY);

                // add key frame to timeline
                timeline.getKeyFrames().add(kf);
            } else {
                playerCircle[i].setVisible(false);
            }

            if (footballMatch.getPosition(time).getPlayerAdversary(i) != null && footballMatch.getPosition(time).getPlayerAdversary(i).isOnField()) {
                adversaryCircle[i].setVisible(playerPause < 2);
                adversaryCircle[i].setEffect(new Lighting());

                // get location which adversaryCircle[i] should move to
                kvX = new KeyValue(adversaryCircle[i].centerXProperty(), footballMatch.getPosition(time).getPlayerAdversary(i).getxAnimation(), Interpolator.LINEAR);
                kvY = new KeyValue(adversaryCircle[i].centerYProperty(), footballMatch.getPosition(time).getPlayerAdversary(i).getyAnimation(), Interpolator.LINEAR);

                // set the locations to keyframes
                kf = new KeyFrame(Duration.millis(speed), kvX, kvY);

                // add key frame to timeline
                timeline.getKeyFrames().add(kf);
            } else {
                adversaryCircle[i].setVisible(false);
            }
        }

        // also animate the ball
        ballCircle.setVisible(!footballMatch.getPosition(time).isPause() && playerPause < 2);
        kvX = new KeyValue(ballCircle.centerXProperty(), footballMatch.getPosition(time).getBallPosition().getxAnimation(), Interpolator.LINEAR);
        kvY = new KeyValue(ballCircle.centerYProperty(), footballMatch.getPosition(time).getBallPosition().getyAnimation(), Interpolator.LINEAR);
        kf = new KeyFrame(Duration.millis(speed), kvX, kvY);
        timeline.getKeyFrames().add(kf);

        // set the score
        viewController.setScore("Score: " + footballMatch.getPosition(time).getScoreLeft() + " - " + footballMatch.getPosition(time).getScoreRight());

        // set the time
        viewController.setTime(time);

        // play timeline
        timeline.play();
    }

    /**
     * Change the speed to play at. 1 is the default, 2 is twice the speed, etc.
     *
     * @param factor int: the factor to the speed
     */
    public static void setSpeed(double factor) {
        speed = (int) (MILLISECONDS_PER_FRAME / factor);
    }

    /**
     * Set the current time (can be changed while playing)
     *
     * @param time the time to change to
     */
    public static void setTime(int time) {
        AnimateFootballMatch.time = time;
    }

    /**
     * Toggle pause
     */
    public static void togglePause() {
        pause = !pause;
        if (!pause) {
            onFinished.handle(null);
        } else {
            if (timeline != null) {
                timeline.stop();
            }
            time--;
        }
    }

    /**
     * If the animation is paused
     *
     * @return boolean: if the animation is paused
     */
    public static boolean isPause() {
        return pause;
    }

    /**
     * Stop the animation
     */
    public static void stopAnimation() {
        timeline.stop();
        reset();
    }

    /**
     * Reset all static variables
     */
    private static void reset() {
        for (Circle c : adversaryCircle) {
            c = null;
        }
        for (Circle c : playerCircle) {
            c = null;
        }
        ballCircle = null;
        footballMatch = null;
        time = 0;
        timeline = null;
        viewController = null;
        pause = false;
        playerPause = 0;
    }
}
