package application;
	
import java.io.IOException;
import java.util.logging.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.*;

/**
 * 
 * @author Jochem
 *
 */

public class Main extends Application {
	
	private Stage primaryStage;
	private Pane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Football Coach");
		
		initializeRootLayout();
		
	}
	
	/**
	 * Method to initialize the starting point of the application.
	 */
    public void initializeRootLayout(){
        try {
            // Load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/fxml/MainMenu.fxml"));
            rootLayout = (Pane) loader.load();
            
            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            // Temporary
            primaryStage.setResizable(false);

            primaryStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to load interface: Pane");
        }
    }
	
	
	/**
	 * 
	 * @param args Command Line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
