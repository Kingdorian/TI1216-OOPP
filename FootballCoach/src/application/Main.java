package application;

import application.view.PopupControllerInterface;
import application.view.ViewControllerInterface;
import java.io.IOException;
import java.util.logging.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

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
            primaryStage.getIcons().add(new Image("/application/img/icon.png"));

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
        Object[] paneAndLoader = loadPane(viewPath);
        Pane pane = (Pane) paneAndLoader[0];
        if(viewPath.contains("GameScreen")){
            BorderPane.setAlignment(pane, Pos.TOP_LEFT);
            BorderPane.setMargin(pane, new Insets(40,40,0,0));
        }
        rootLayout.setCenter(pane);

        //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
        ViewControllerInterface viewController = ((FXMLLoader)paneAndLoader[1]).getController();

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
        
        // Set the top screen
        Object[] paneAndLoader = loadPane(viewPath);
        rootLayout.setTop((Pane) paneAndLoader[0]);

        //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
        ViewControllerInterface viewController = ((FXMLLoader)paneAndLoader[1]).getController();

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

        // Set the left screen
        Object[] paneAndLoader = loadPane(viewPath);
        Pane pane = (Pane) paneAndLoader[0];
        BorderPane.setAlignment(pane, Pos.TOP_LEFT);
        BorderPane.setMargin(pane, new Insets(40,40,0,40));
        rootLayout.setLeft(pane);

        //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
        ViewControllerInterface viewController = ((FXMLLoader)paneAndLoader[1]).getController();

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
     * @return          the requirested Pane and the used loader in an Object array (first pane[0], then loader[1])
     */
    private Object[] loadPane(String viewPath){
        try {
            Object[] result = new Object[2];
            // Load startup screen
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(viewPath));
            result[0] = (Pane) loader.load();
            result[1] = loader;
            return result;
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to load interface: Pane");
        }
        System.exit(1);
        return null;
    }
    
    
    /**
     * Create a pop-up window without an image.
     * @param viewPath      name of the .fxml file containing the pop-up window
     * @param popupTitle    the title of the pop-up window
     * @return              true if the OK button has been clicked, else: false
     */
    public boolean createPopup(String viewPath, String popupTitle){
        return createPopup(viewPath, popupTitle, null);
    }
    
    
    /**
     * Create a pop-up window with an image.
     * @param viewPath      name of the .fxml file containing the pop-up window
     * @param popupTitle    the title of the pop-up window
     * @param imagePath     a path to the image to use for the pop-up window
     * @return              true if the OK button has been clicked, else: false
     */
    public boolean createPopup(String viewPath, String popupTitle, String imagePath){
        
        // load the pane
        Object[] paneAndLoader = loadPane(changeNameToClassPath(viewPath));
        Pane pane = (Pane) paneAndLoader[0];
        // Pane pane = loadPane(changeNameToClassPath(viewPath));
        
        // create popup window
        Stage popupStage = new Stage();
        popupStage.setTitle(popupTitle);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(primaryStage);
        if(imagePath != null)
            popupStage.getIcons().add(new Image(imagePath));
        Scene scene = new Scene(pane);
        popupStage.setScene(scene);
        popupStage.setResizable(false);            

        
        // get the pop-up's controller class
        
        PopupControllerInterface popupController = ((FXMLLoader)paneAndLoader[1]).getController();
        popupController.setPopupStage(popupStage);
        
        // show the popup and wait until the user closes it
        popupStage.showAndWait();
        
        return popupController.isOkClicked();
    }
    
    
    /**
     *
     * @param args Command Line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
