/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Playmatch;

import ContainerPackage.Match;
import ContainerPackage.PlayerPosition;
import ContainerPackage.PositionsTimeSlice;
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
import javafx.util.Duration;

/**
 *
 * @author faris
 */
public class AnimateFootballMatch {
    
    public int MILLISECONDS_PER_SLICE = 245;
        
    // Circles representing the players
    private final Circle playerCircle[] = new Circle[11];
    private final Circle adversaryCircle[] = new Circle[11];
    private Circle ballCircle;
    
    // Event handler, being activated when a frame is done playing
    private EventHandler onFinished;
    
    // Integer holding the elapsed time
    private Integer time = 0;
    
    // The footballMatch which is being animated
    private Match footballMatch;
    
    
    public void playMatch(BorderPane rootLayout, Match footballMatch){
        
        this.footballMatch = footballMatch;
        
        AnchorPane anchorPane;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AnimateFootballMatch.class.getResource("FootballField.fxml"));
            anchorPane = (AnchorPane) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AnimateFootballMatch.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to read FootballField.fxml file");
            return;
        }

        // get positions to draw circles
        PositionsTimeSlice pts = footballMatch.getPositions(0);
        PlayerPosition player1 = pts.getPlayersAlly(0);
        PlayerPosition adversary1 = pts.getPlayersAdversary(0);

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

        // create event handler
        onFinished = new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                if(time < footballMatch.amoutOfSlices() - 1){
                    time++;
                    playTimeSlice();
                    System.out.println("current time: "+time/4.0+"s");
                }
            }
        };
        
        // Play the first TimeSlice, following time slices will be played by the event handler
        if(footballMatch.amoutOfSlices()>1)
            playTimeSlice();
    }
    
    
    /**
     * play a TimeSlice from the footballMatch, based on the current time variable
     */
    private void playTimeSlice(){
        
        System.out.println("playing next scene");
        
        // declare a time line, key frames to later add to the time line and key values to put in the key frames
        Timeline timeline = new Timeline();
        KeyValue kvX, kvY;
        KeyFrame kf = new KeyFrame(Duration.millis(MILLISECONDS_PER_SLICE), onFinished);
        timeline.getKeyFrames().add(kf); // use this keyframe to trigger onFinished once, after the animation is finished
        
        for(int i=0; i<11; i++){
            // get location which circle1 and circle2 should move to
            if(footballMatch.getPositions(time).getPlayersAlly(i) != null && footballMatch.getPositions(time).getPlayersAlly(i).isOnField()){
                playerCircle[i].setVisible(true);
                playerCircle[i].setEffect(new Lighting());

                // get location which playerCircle[i] should move to
                kvX = new KeyValue(playerCircle[i].centerXProperty(), footballMatch.getPositions(time).getPlayersAlly(i).getxPos(), Interpolator.LINEAR);
                kvY = new KeyValue(playerCircle[i].centerYProperty(), footballMatch.getPositions(time).getPlayersAlly(i).getyPos(), Interpolator.LINEAR);
                
                // set the locations to keyframes
                kf = new KeyFrame(Duration.millis(MILLISECONDS_PER_SLICE)/*,onFinished*/,  kvX, kvY);
                
                // add key frame to timeline
                timeline.getKeyFrames().add(kf);
            } else
                playerCircle[i].setVisible(false);

            if(footballMatch.getPositions(time).getPlayersAdversary(i) != null && footballMatch.getPositions(time).getPlayersAdversary(i).isOnField()){
                adversaryCircle[i].setVisible(true);
                adversaryCircle[i].setEffect(new Lighting());
                
                // get location which adversaryCircle[i] should move to
                 kvX = new KeyValue(adversaryCircle[i].centerXProperty(), footballMatch.getPositions(time).getPlayersAdversary(i).getxPos(), Interpolator.LINEAR);
                 kvY = new KeyValue(adversaryCircle[i].centerYProperty(), footballMatch.getPositions(time).getPlayersAdversary(i).getyPos(), Interpolator.LINEAR);
                
                // set the locations to keyframes
                kf = new KeyFrame(Duration.millis(MILLISECONDS_PER_SLICE), kvX, kvY);
                
                // add key frame to timeline
                timeline.getKeyFrames().add(kf);
            } else
                adversaryCircle[i].setVisible(false);
        }
        
        // also animate the ball
        kvX = new KeyValue(ballCircle.centerXProperty(), footballMatch.getPositions(time).getBallPosition().getxPos(), Interpolator.LINEAR);
        kvY = new KeyValue(ballCircle.centerYProperty(), footballMatch.getPositions(time).getBallPosition().getyPos(), Interpolator.LINEAR);
        kf = new KeyFrame(Duration.millis(MILLISECONDS_PER_SLICE), kvX, kvY);
        timeline.getKeyFrames().add(kf);
        
        // play timeline
        timeline.play();
    }
}
