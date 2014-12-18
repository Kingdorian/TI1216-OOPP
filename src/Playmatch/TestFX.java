package Playmatch;

import CalculateMatch.MainAIController;
import ContainerPackage.Match;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestFX extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.setResizable(false);
        initializeRootLayout();
        
        Match testMatch = MainAIController.createMatch();
        (new AnimateFootballMatch()).playMatch(rootLayout, testMatch, stage);      
    }

    
    public void initializeRootLayout(){
        try {
            // Load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TestFX.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            //primaryStage.setResizable(false);

            primaryStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(TestFX.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to load interface: BorderPane");
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
