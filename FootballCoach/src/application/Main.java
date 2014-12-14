package application;

import application.controller.XMLHandler;
import application.model.Competition;
import application.model.Players;
import application.view.PopupControllerInterface;
import application.view.ViewControllerInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;

/**
 *
 * @author Jochem
 * @author Faris
 */
public class Main extends Application {

    private static String chosenName;
    private static String chosenTeamName = "";
    private static Competition competition;

    private static Stage primaryStage;
    private static BorderPane rootLayout;

    private static Players selectedPlayer;

    @Override
    public void start(Stage primaryStage) {
        // load and show the start screen
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Football Coach");

        initializeRootLayout();
        setCenterView("MainMenu");

        primaryStage.show();

        // Add a scale handler to the scene
        scaleToScreenSize(rootLayout);

//        primaryStage.setIconified(true);
//        primaryStage.setFullScreen(true);
        // load the competition
        try {
            competition = XMLHandler.readCompetition("XML/teams.xml", "XML/competition.xml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Couldn't open one of the following files: \"XML/Matches.xml\" or \"XML/Competition.xml\"");
        }
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

            // Set the scene and add an application icon
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image("/application/img/icon.png"));

            // Disable escape turning off full screen mode (so we won't get a message when going to full screen mode)
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

            // Add a shortcut handler to our scene
            rootLayout.getScene().addEventHandler(javafx.scene.input.KeyEvent.KEY_RELEASED, new ShortcutEventHandler());

        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to load interface: Pane");
        }
    }

    /**
     * This class handles shortcuts (like ctrl+f for full screen)
     */
    private static class ShortcutEventHandler implements EventHandler<javafx.scene.input.KeyEvent> {

        final KeyCombination ctrlF = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
        final KeyCombination ctrlS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

        @Override
        public void handle(javafx.scene.input.KeyEvent event) {
            if (ctrlF.match(event)) {
                if (primaryStage.isFullScreen()) {
                    primaryStage.setFullScreen(false);
                } else {
                    primaryStage.setFullScreen(true);
                }
                System.out.println("Ctrl+F pressed: toggle full screen.");
            }
            if (ctrlS.match(event)) {
                System.out.println("Ctrl+S pressed: Save game.");
            }
        }
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

            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                contentPane.setPrefWidth(Math.max(initWidth, newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }
//****************************************************************************************************
// End of coppied code.

    /**
     * this method cleans all sections of the rootLayout's borderpane
     */
    public void cleanRootLayout() {
        rootLayout.setTop(null);
        rootLayout.setLeft(null);
        rootLayout.setBottom(null);
        rootLayout.setRight(null);
        rootLayout.setCenter(null);
    }

    /**
     * With this methode you can change the center view. Give the name of the
     * fxml file as parameter (the "view/" and ".fxml" parts are not required,
     * but may be added). Make sure all .fxml files got a controller file called
     * the same as the .fxml file, but with "Controller" added to it (and of
     * course with .java instead of .fxml). Those controller files should
     * implement the ViewControllerInterface to function properly.
     *
     * @param viewPath the name of the .fxml file (for example: test.fxml ->
     * "test")
     */
    public void setCenterView(String viewPath) {
        viewPath = changeNameToClassPath(viewPath);

        // Set the center screen
        Object[] paneAndLoader = loadPane(viewPath);
        Pane pane = (Pane) paneAndLoader[0];
        if (viewPath.contains("GameScreen")) {
            BorderPane.setAlignment(pane, Pos.TOP_LEFT);
            BorderPane.setMargin(pane, new Insets(40, 40, 0, 0));
        }
        rootLayout.setCenter(pane);

        //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
        ViewControllerInterface viewController = ((FXMLLoader) paneAndLoader[1]).getController();

        // Give the view controller a reference to this main controller class
        viewController.setMainController(this);
    }

    /**
     * With this methode you can change the top view. Give the name of the fxml
     * file as parameter (the "view/" and ".fxml" parts are not required, but
     * may be added). Make sure all .fxml files got a controller file called the
     * same as the .fxml file, but with "Controller" added to it (and of course
     * with .java instead of .fxml). Those controller files should implement the
     * ViewControllerInterface to function properly.
     *
     * @param viewPath the name of the .fxml file (for example: test.fxml ->
     * "test")
     */
    public void setTopView(String viewPath) {
        viewPath = changeNameToClassPath(viewPath);

        // Set the top screen
        Object[] paneAndLoader = loadPane(viewPath);
        rootLayout.setTop((Pane) paneAndLoader[0]);

        //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
        ViewControllerInterface viewController = ((FXMLLoader) paneAndLoader[1]).getController();

        // Give the view controller a reference to this main controller class
        viewController.setMainController(this);
    }

    /**
     * With this methode you can change the left view. Give the name of the fxml
     * file as parameter (the "view/" and ".fxml" parts are not required, but
     * may be added). Make sure all .fxml files got a controller file called the
     * same as the .fxml file, but with "Controller" added to it (and of course
     * with .java instead of .fxml). Those controller files should implement the
     * ViewControllerInterface to function properly.
     *
     * @param viewPath the name of the .fxml file (for example: test.fxml ->
     * "test")
     */
    public void setLeftView(String viewPath) {
        viewPath = changeNameToClassPath(viewPath);

        // Set the left screen
        Object[] paneAndLoader = loadPane(viewPath);
        Pane pane = (Pane) paneAndLoader[0];
        BorderPane.setAlignment(pane, Pos.TOP_LEFT);
        BorderPane.setMargin(pane, new Insets(40, 40, 0, 40));
        rootLayout.setLeft(pane);

        //ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
        ViewControllerInterface viewController = ((FXMLLoader) paneAndLoader[1]).getController();

        // Give the view controller a reference to this main controller class
        viewController.setMainController(this);
    }

    /**
     * Strip a string, so it can be used as a class path
     *
     * @param viewPath a string containing a class' name
     * @return a path to the class
     */
    private String changeNameToClassPath(String viewPath) {
        // Change class name to file path
        if (!(viewPath.contains("view/"))) {
            viewPath = "view/" + viewPath;
        }
        if (!(viewPath.contains(".fxml"))) {
            viewPath += ".fxml";
        }
        return viewPath;
    }

    /**
     * loads the requested Pane
     *
     * @param viewPath the name of the .fxml file containing the Pane
     * @return the requirested Pane and the used loader in an Object array
     * (first pane[0], then loader[1])
     */
    private Object[] loadPane(String viewPath) {
        try {
            Object[] result = new Object[2];
            // Load startup screen
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(viewPath));
            result[0] = (Pane) loader.load();
            result[1] = loader;
            return result;
        } catch (IOException ex) {
            Logger.getLogger(Main.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "Failed to load interface: Pane");
        }
        System.exit(1);
        return null;
    }

    /**
     * Create a pop-up window without an image.
     *
     * @param viewPath name of the .fxml file containing the pop-up window
     * @param popupTitle the title of the pop-up window
     * @return true if the OK button has been clicked, else: false
     */
    public boolean createPopup(String viewPath, String popupTitle) {
        return createPopup(viewPath, popupTitle, null);
    }

    /**
     * Create a pop-up window with an image.
     *
     * @param viewPath name of the .fxml file containing the pop-up window
     * @param popupTitle the title of the pop-up window
     * @param imagePath a path to the image to use for the pop-up window
     * @return true if the OK button has been clicked, else: false
     */
    public boolean createPopup(String viewPath, String popupTitle, String imagePath) {

        // load the pane
        Object[] paneAndLoader = loadPane(changeNameToClassPath(viewPath));
        Pane pane = (Pane) paneAndLoader[0];
        // Pane pane = loadPane(changeNameToClassPath(viewPath));

        // create popup window
        Stage popupStage = new Stage();
        popupStage.setTitle(popupTitle);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(primaryStage);
        if (imagePath != null) {
            popupStage.getIcons().add(new Image(imagePath));
        }
        Scene scene = new Scene(pane);
        popupStage.setScene(scene);
        popupStage.sizeToScene();
        popupStage.setResizable(false);

        // get the pop-up's controller class
        PopupControllerInterface popupController = ((FXMLLoader) paneAndLoader[1]).getController();
        popupController.setPopupStage(popupStage);

        // show the popup and wait until the user closes it
        popupStage.showAndWait();

        return popupController.isOkClicked();
    }

    public static ArrayList<Players> getPlayersData(String teamName) {
        return competition.getTeamByName(teamName).getPlayers();
    }

    public static String getChosenTeamName() {
        return chosenTeamName;
    }
    
    public static void SetChosenTeamName(String name){
        chosenTeamName = name;
    }

    public static String getChosenName() {
        return chosenName;
    }

    public static void setChosenName(String chosenName) {
        Main.chosenName = chosenName;
    }

    public static Competition getCompetition() {
        return competition;
    }

    public static Players getSelectedPlayer() {
        return selectedPlayer;
    }

    public static void setSelectedPlayer(Players selectedPlayer) {
        Main.selectedPlayer = selectedPlayer;
    }
    
    

    /**
     *
     * @param args Command Line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
