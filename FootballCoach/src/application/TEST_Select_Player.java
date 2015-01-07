package application;


import application.animation.Playmatch.ChoosePositionsController;
import application.controller.XMLHandler;
import application.model.Competition;
import application.model.Team;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author faris
 */
public class TEST_Select_Player extends Application {
    
    private Stage primaryStage;
    private AnchorPane rootLayout;
    private Team team;
    Competition competition;
    
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.setResizable(false);
        initializeRootLayout();
    }

    
    public void initializeRootLayout(){
        try {
            // Load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEST_Select_Player.class.getResource("animation/Playmatch/ChoosePositions.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            //read a competition
            
            try {
                competition = XMLHandler.readCompetition("XML/Teams.xml", "XML/Matches.xml");
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Couldn't open one of the following files: \"XML/Teams.xml\" or \"XML/Matches.xml\"");
            }
            
            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            
            //set pane to controller
            ((ChoosePositionsController)loader.getController()).drawCircles(primaryStage, rootLayout, competition.getTeams()[0]);

            primaryStage.show();
            
            scaleToScreenSize(rootLayout);
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to load interface: AnchorPane");
        }
    }
    
    /**
     *
     * @param args Command Line arguments.
     */
    public static void main(String[] args){
        launch(args);
    }
    
    
    // This code to adjust the screen size is coppied from http://stackoverflow.com/a/16608161
    // A few minor adjustments have been made to improve it and comments have been added.
    //****************************************************************************************************
    /**
     * Scale the size of the given stage. coppied from: see header above
     *
     * @param contentPane the stage to resize
     */
    private void scaleToScreenSize(final AnchorPane contentPane) {
        Scene scene = contentPane.getScene();
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }

    /**
     * When the user resizes the screen the method changed() in this class will
     * be called coppied from: see header above
     */
    private static class SceneSizeChangeListener implements ChangeListener<Number> {

        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final AnchorPane contentPane;

        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, AnchorPane contentPane) {
            this.scene = scene;
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.contentPane = contentPane;
        }

        /**
         * Listen for size changes and change the scene accordingly coppied
         * from: see header above
         *
         * @param observableValue the value being observed
         * @param oldValue old size
         * @param newValue new size
         */
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();

            double scaleFactor
                    = newWidth / newHeight > ratio
                            ? newHeight / initHeight
                            : newWidth / initWidth;

            //if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
//            } else {
//                contentPane.setPrefWidth(Math.max(initWidth, newWidth));
//                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
//            }
        }
    }
    //****************************************************************************************************
    // End of coppied code.
    
}
