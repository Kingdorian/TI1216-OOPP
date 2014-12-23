package application.animation.Playmatch;

import application.animation.CalculateMatch.MainAIController;
import application.animation.ContainerPackage.Match;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class TestFX extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage stage) {

        primaryStage = stage;
        stage.setResizable(false);
        initializeRootLayout();
        
        // animate play match:
        Match testMatch = MainAIController.createMatch();
        (new AnimateFootballMatch()).playMatch(rootLayout, testMatch, stage);   
        
        
        // test choose positions:
//        AnchorPane anchorPane;
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(AnimateFootballMatch.class.getResource("ChoosePositions.fxml"));
//            anchorPane = (AnchorPane) loader.load();
//            //ChoosePositionsController viewController = (ChoosePositionsController) loader.getController();
//        } catch (IOException ex) {
//            Logger.getLogger(AnimateFootballMatch.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Failed to read FootballField.fxml file");
//            return;
//        }
//        rootLayout.setCenter(anchorPane);
    }

    
    public void initializeRootLayout(){
        try {
            // Load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);

            primaryStage.show();
            
            scaleToScreenSize(rootLayout);
            
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to load interface: BorderPane");
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    
    // This code to adjust the screen size is coppied from http://stackoverflow.com/a/16608161
    // A few minor adjustments have been made to improve it and comments have been added.
    //****************************************************************************************************
    /**
     * Scale the size of the given stage. coppied from: see header above
     *
     * @param contentPane the stage to resize
     */
    private void scaleToScreenSize(final BorderPane contentPane) {
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
        private final BorderPane contentPane;

        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, BorderPane contentPane) {
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
