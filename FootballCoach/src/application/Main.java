package application;

import application.view.ViewControllerInterface;
import java.io.IOException;
import java.util.logging.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

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
        setCenterView("MainMenu");
        primaryStage.show();
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

        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to load interface: Pane");
        }
    }

    
    /**
     * this method cleans all sections of the rootLayout's borderpane
     */
    public void cleanRootLayout(){
        rootLayout.setTop(null);
        rootLayout.setLeft(null);
        rootLayout.setBottom(null);
        rootLayout.setRight(null);
        rootLayout.setCenter(null);
    }
    
    /**
     * With this methode you can change the center view.
     * Give the name of the fxml file as parameter (the "view/" and ".fxml" parts
     * are not required, but may be added).
     * Make sure all .fxml files got a controller file called the same as the .fxml
     * file, but with "Controller" added to it (and of course with .java instead of
     * .fxml). Those controller files should implement the ViewControllerInterface 
     * to function properly.
     * @param viewPath  the name of the .fxml file (for example: test.fxml -> "test")
     */
    public void setCenterView(String viewPath){
        viewPath = changeNameToClassPath(viewPath);

        // Set the center screen
        Pane pane = loadPane(viewPath);
        if(viewPath.contains("GameScreen")){
            BorderPane.setAlignment(pane, Pos.TOP_LEFT);
            BorderPane.setMargin(pane, new Insets(40,40,0,0));
        }
        rootLayout.setCenter(pane);

        //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
        ViewControllerInterface viewController = getViewController(viewPath);

        // Give the view controller a reference to this main controller class
        viewController.setMainController(this);
    }
    
    
    /**
     * With this methode you can change the top view.
     * Give the name of the fxml file as parameter (the "view/" and ".fxml" parts
     * are not required, but may be added).
     * Make sure all .fxml files got a controller file called the same as the .fxml
     * file, but with "Controller" added to it (and of course with .java instead of
     * .fxml). Those controller files should implement the ViewControllerInterface 
     * to function properly.
     * @param viewPath  the name of the .fxml file (for example: test.fxml -> "test")
     */
    public void setTopView(String viewPath){
        viewPath = changeNameToClassPath(viewPath);
        
        // Set the center screen
        rootLayout.setTop(loadPane(viewPath));

        //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
        ViewControllerInterface viewController = getViewController(viewPath);

        // Give the view controller a reference to this main controller class
        viewController.setMainController(this);
    }
    
    
    /**
     * With this methode you can change the left view.
     * Give the name of the fxml file as parameter (the "view/" and ".fxml" parts
     * are not required, but may be added).
     * Make sure all .fxml files got a controller file called the same as the .fxml
     * file, but with "Controller" added to it (and of course with .java instead of
     * .fxml). Those controller files should implement the ViewControllerInterface 
     * to function properly.
     * @param viewPath  the name of the .fxml file (for example: test.fxml -> "test")
     */
    public void setLeftView(String viewPath){
        viewPath = changeNameToClassPath(viewPath);

        // Set the center screen
        Pane pane = loadPane(viewPath);
        BorderPane.setAlignment(pane, Pos.TOP_LEFT);
        BorderPane.setMargin(pane, new Insets(40,40,0,40));
        rootLayout.setLeft(pane);

        //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
        ViewControllerInterface viewController = getViewController(viewPath);

        // Give the view controller a reference to this main controller class
        viewController.setMainController(this);
    }
    
    
    /**
     * Strip a string, so it can be used as a class path
     * @param viewPath  a string containing a class' name
     * @return          a path to the class
     */
    private String changeNameToClassPath(String viewPath){
        // Change class name to file path
        if(!(viewPath.contains("view/")))
            viewPath = "view/" + viewPath;
        if(!(viewPath.contains(".fxml")))
            viewPath += ".fxml";
        return viewPath;
    }
    
    
    /**
     * loads the requested Pane
     * @param viewPath  the name of the .fxml file containing the Pane
     * @return          the requirested Pane
     */
    private Pane loadPane(String viewPath){
        try {
            // Load startup screen
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(viewPath));
            return (Pane) loader.load();
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to load interface: Pane");
        }
        System.exit(1);
        return null;
    }
    
    
    /*
     * return an instance of the class, which is specified by it's name in string form
     * @param classStr  String containing the class' view (fxml) name, or the class' view controller (java) name
    */
    private ViewControllerInterface getViewController(String classStr){
        // Filter class location from class path
        if(classStr.contains("view/"))
            classStr = classStr.split("view/")[1];
        if(classStr.contains(".fxml"))
            classStr = "application.view." + classStr.split(".fxml")[0] + "Controller";
        else
            classStr = "application.view." + classStr + "Controller";

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
            System.out.println("error: no access to: " + classStr);
        }
        System.exit(1);
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
