package application;

import application.view.ViewControllerInterface;
import java.io.IOException;
import java.util.logging.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Jochem
 * @author Faris
 */
public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Football Coach");

        initializeRootLayout();
        switchView("MainMenu");
    }

    /**
     * Method to initialize the starting point of the application.
     */
    public void initializeRootLayout() {
        try {
            // Load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

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
     * With this methode you can change the view.
     * Give the name of the fxml file as parameter (the "view/" and ".fxml" parts
     * are not required, but may be added).
     * Make sure all .fxml files got a controller file called the same as the .fxml
     * file, but with "Controller" added to it (and of course with .java instead of
     * .fxml). Those controller files should implement the ViewControllerInterface 
     * to function properly.
     * @param viewPath  the name of the .fxml file (for example: test.fxml -> "test")
     */
    public void switchView(String viewPath){
        // Change class name to file path
        if(!(viewPath.contains("view/")))
            viewPath = "view/" + viewPath;
        if(!(viewPath.contains(".fxml")))
            viewPath += ".fxml";
        
        try {
            // Load startup screen
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(viewPath));
            AnchorPane startupScreen = (AnchorPane) loader.load();
            
            // Set startup screen
            rootLayout.setCenter(startupScreen);
            
            //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
            ViewControllerInterface viewController = getViewController(viewPath);
            
            // Give the view controller a reference to this main controller class
            viewController.setMainController(this);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to load interface: AnchorPane");
        }
    }
    
    
    /*
     * return an instance of the class, which is specified by it's name in string form
     * @param classStr  String containing the class' view (fxml) name, or the class' view controller (java) name
    */
    private ViewControllerInterface getViewController(String classStr){
        // Filter class from class path
        if(classStr.contains("view/"))
            classStr = classStr.split("view/")[1];
        if(classStr.contains(".fxml"))
            classStr = "application.view." + classStr.split(".fxml")[0] + "Controller";
        
        // Return the specified by the string, if possible
        try {
            Class cls = Class.forName(classStr);
            return (ViewControllerInterface) cls.newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error: unknown view reference: "+ classStr);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error: " +classStr+ " doesn't implement view interface");
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error: no acces to: " + classStr);
        }
        return null;
    }

    
    /**
     *
     * @param args Command Line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
