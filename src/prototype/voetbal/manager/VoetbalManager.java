/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.voetbal.manager;


import prototype.voetbal.manager.view.ViewControllerInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author faris
 */
public class VoetbalManager extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Voetbal manager app");
        
        initializeRootLayout();
        //showStartupScreen();
        switchView("StartView");
//        showFootball();
    }

    public void initializeRootLayout(){
        try {
            // Load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(VoetbalManager.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            //primaryStage.setResizable(false);

            primaryStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(VoetbalManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to load interface: BorderPane");
        }
    }
    
    public void switchView(String viewPath){
        // Change class name to file path
        if(!(viewPath.contains("view/")))
            viewPath = "view/" + viewPath;
        if(!(viewPath.contains(".fxml")))
            viewPath += ".fxml";
        
        try{
            // Load startup screen
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(VoetbalManager.class.getResource(viewPath));
            AnchorPane startupScreen = (AnchorPane) loader.load();
            
            // Set startup screen
            rootLayout.setCenter(startupScreen);
            
            //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
            ViewControllerInterface viewController = getViewController(viewPath);
            
            // Give the view controller a reference to this main controller class
            viewController.setMainController(this);
            
        } catch (IOException ex) {
            Logger.getLogger(VoetbalManager.class.getName()).log(Level.SEVERE, null, ex);
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
            classStr = "prototype.voetbal.manager.view." + classStr.split(".fxml")[0] + "Controller";
        
        // Return the specified by the string, if possible
        try {
            Class cls = Class.forName(classStr);
            return (ViewControllerInterface) cls.newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VoetbalManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error: unknown view reference: "+ classStr);
        } catch (InstantiationException ex) {
            Logger.getLogger(VoetbalManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error: " +classStr+ " doesn't implement view interface");
        } catch (IllegalAccessException ex) {
            Logger.getLogger(VoetbalManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error: no acces to: " + classStr);
        }
        return null;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
