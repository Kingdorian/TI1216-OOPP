package application.animation.Playmatch;


import application.animation.ContainerPackage.Match;
import application.animation.ContainerPackage.PositionsTimeSlice;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * AnimaFootballMatch plays a football match, based on positions saved in a Match object.
 * @author faris
 */
public class AnimateFootballMatch {
    
    // The amount of slides to play per second. Change this to change the playing speed.
    private static final int MILLISECONDS_PER_SLICE = 245; // base speed (at x1 speed)
    private static int speed = 245; // speed to play at
        
    // Circles representing the players
    private static final Circle playerCircle[] = new Circle[11];
    private static final Circle adversaryCircle[] = new Circle[11];
    private static Circle ballCircle;
    private static Timeline timeline;

    // Integer holding the elapsed time
    private static int time = 0;
    
    // The footballMatch which is being animated
    private static Match footballMatch;
    
    private static int playerPause = 0; // if this is 1, there is a pause, if this is 2, there was a pause last slice, if it is 3, still setting up new positions, if this is 0, there is no pause
    private static FootballFieldController viewController;
    
    // stop playing if this is true
    private static boolean pause;
    
    
    // Event handler, will be activated when a frame is done playing
    private static EventHandler onFinished = new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                if(time < footballMatch.amoutOfSlices() - 1 && !pause){
                    time++;
                    playTimeSlice();
                }
            }
        };
    
    /**
     * play a match defined in the Match parameter
     * @param rootLayout        a BorderPane, which center should contain the animated match
     * @param footballMatch     Match containing a full match to play
     * @param stage
     */
    public void playMatch(BorderPane rootLayout, Match footballMatch, Stage stage){
        
        this.footballMatch = footballMatch;
     
        // load the view
        AnchorPane anchorPane;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AnimateFootballMatch.class.getResource("FootballField.fxml"));
            anchorPane = (AnchorPane) loader.load();
            viewController = (FootballFieldController) loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(AnimateFootballMatch.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to read FootballField.fxml file");
            return;
        }

        // get starting positions and increase time variable
        PositionsTimeSlice startPositions = footballMatch.getPositions(time++);
        
        // draw the circles
        for(int i=0; i<11; i++){
            // if player position is defined, set the position and set the circle to visible, else set it to not-visible and don't specify a position
            if(startPositions.getPlayersAlly(i) != null){
            playerCircle[i] = new Circle(startPositions.getPlayersAlly(i).getxPos(), startPositions.getPlayersAlly(i).getyPos(), 13, Color.BLUE);
            playerCircle[i].setEffect(new Lighting());
            playerCircle[i].setVisible(true);
            } else{
                playerCircle[i] = new Circle(13, Color.BLUE);
                playerCircle[i].setEffect(new Lighting());
                playerCircle[i].setVisible(false);
            }
            
            // if player position is defined, set the position and set the circle to visible, else set it to not-visible and don't specify a position
            if(startPositions.getPlayersAdversary(i) != null){
            adversaryCircle[i] = new Circle(startPositions.getPlayersAdversary(i).getxPos(), startPositions.getPlayersAdversary(i).getyPos(), 13, Color.RED);
            adversaryCircle[i].setEffect(new Lighting());
            adversaryCircle[i].setVisible(true);
            } else{
                adversaryCircle[i] = new Circle(13, Color.RED);
                adversaryCircle[i].setEffect(new Lighting());
                adversaryCircle[i].setVisible(false);
            }
            
            // add circles to the scene and show the scene
            anchorPane.getChildren().addAll(playerCircle[i], adversaryCircle[i]);
        }
        
        // draw the ball
        if(startPositions.getBallPosition() != null){
            ballCircle = new Circle(startPositions.getBallPosition().getxPos(), startPositions.getBallPosition().getyPos(), 7, Color.WHITE);
            ballCircle.setEffect(new Lighting());
            ballCircle.setVisible(true);
        } else{
            ballCircle = new Circle(7, Color.WHITE);
            ballCircle.setEffect(new Lighting());
            ballCircle.setVisible(false);
        }
        // add the ball to the scene
        anchorPane.getChildren().add(ballCircle);
        
        // draw the scene
        rootLayout.setCenter(anchorPane);
        stage.sizeToScene(); // make sure there are no white borders
        
        // Play the first TimeSlice, following time slices will be played by the event handler
        if(footballMatch.amoutOfSlices()>1)
            playTimeSlice();
    }
    
    
    /**
     * play a TimeSlice from the footballMatch, based on the current time variable
     */
    private static void playTimeSlice(){

        // declare a time line, key frames to later add to the time line and key values to put in the key frames
        timeline = new Timeline();
        KeyValue kvX, kvY;
        KeyFrame kf;
        if(footballMatch.getPositions(time).isPause()){
            kf = new KeyFrame(Duration.millis(1000/*speed * 4*/), onFinished);
            playerPause = 1;
        } else if(playerPause == 1){
            kf = new KeyFrame(Duration.millis(0), onFinished);
            playerPause = 2;
        } else{
            playerPause = playerPause == 2 ? 3 : 0;
            kf = new KeyFrame(Duration.millis(speed), onFinished);
        }
        
        timeline.getKeyFrames().add(kf); // use this keyframe to trigger onFinished once, after the animation is finished
        
        for(int i=0; i<11; i++){
            // get location which circle1 and circle2 should move to
            if(footballMatch.getPositions(time).getPlayersAlly(i) != null && footballMatch.getPositions(time).getPlayersAlly(i).isOnField()){
                playerCircle[i].setVisible(playerPause < 2); // only visible if there wasn't a pause before this slice
                playerCircle[i].setEffect(new Lighting());

                // get location which playerCircle[i] should move to
                kvX = new KeyValue(playerCircle[i].centerXProperty(), footballMatch.getPositions(time).getPlayersAlly(i).getxPos(), Interpolator.LINEAR);
                kvY = new KeyValue(playerCircle[i].centerYProperty(), footballMatch.getPositions(time).getPlayersAlly(i).getyPos(), Interpolator.LINEAR);
                
                // set the locations to keyframes
                kf = new KeyFrame(Duration.millis(speed)/*,onFinished*/,  kvX, kvY);
                
                // add key frame to timeline
                timeline.getKeyFrames().add(kf);
            } else
                playerCircle[i].setVisible(false);

            if(footballMatch.getPositions(time).getPlayersAdversary(i) != null && footballMatch.getPositions(time).getPlayersAdversary(i).isOnField()){
                adversaryCircle[i].setVisible(playerPause < 2);
                adversaryCircle[i].setEffect(new Lighting());
                
                // get location which adversaryCircle[i] should move to
                 kvX = new KeyValue(adversaryCircle[i].centerXProperty(), footballMatch.getPositions(time).getPlayersAdversary(i).getxPos(), Interpolator.LINEAR);
                 kvY = new KeyValue(adversaryCircle[i].centerYProperty(), footballMatch.getPositions(time).getPlayersAdversary(i).getyPos(), Interpolator.LINEAR);
                
                // set the locations to keyframes
                kf = new KeyFrame(Duration.millis(speed), kvX, kvY);
                
                // add key frame to timeline
                timeline.getKeyFrames().add(kf);
            } else
                adversaryCircle[i].setVisible(false);
        }
        
        // also animate the ball
        ballCircle.setVisible(!footballMatch.getPositions(time).isPause() && playerPause < 2);
        kvX = new KeyValue(ballCircle.centerXProperty(), footballMatch.getPositions(time).getBallPosition().getxPos(), Interpolator.LINEAR);
        kvY = new KeyValue(ballCircle.centerYProperty(), footballMatch.getPositions(time).getBallPosition().getyPos(), Interpolator.LINEAR);
        kf = new KeyFrame(Duration.millis(speed), kvX, kvY);
        timeline.getKeyFrames().add(kf);
        
        // set the score
        viewController.setScore("Score: " + footballMatch.getPositions(time).getScoreLeft() + " - " + footballMatch.getPositions(time).getScoreRight());
        
        // set the time
        viewController.setTime(time);
        
        // play timeline
        timeline.play();
    }
    
    /**
     * Change the speed to play at. 1 is the default, 2 is twice the speed, etc.
     * @param factor    int: the factor to the speed
     */
    public static void setSpeed(double factor){   
        speed = (int) (MILLISECONDS_PER_SLICE/factor);
    }

    public static void setTime(int time) {
        AnimateFootballMatch.time = time;
    }

    public static void togglePause() {
            pause = !pause;
            if(!pause){
                onFinished.handle(null);
            } else{
                timeline.stop();
                time--;
            }
    }

    public static boolean isPause() {
        return pause;
    }
    
    
    
}
