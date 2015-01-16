package application;

import application.animation.Container.CalculatedMatch;
import application.animation.Playmatch.AnimateFootballMatch;
import application.model.Competition;
import application.model.Players;
import application.view.GameScreenFootballFieldController;
import application.view.GameScreenChoosePositionsController;
import application.view.GameScreenMenuController;
import application.view.GameScreenTitleController;
import application.view.PopupControllerInterface;
import application.view.ViewControllerInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.PopupControl;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.PopupWindow.AnchorLocation;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * This is the main class which controls the flow of this application and allows
 * different parts of the program to communicate which each other.
 *
 * @author Jochem
 * @author Faris
 */
public class Main extends Application {

    private static String chosenName;
    private static String chosenTeamName;
    private static Competition competition;

    private static Stage primaryStage;
    private static BorderPane rootLayout;

    private static Players selectedPlayer;
    private static GameScreenMenuController menuController;
    private static GameScreenTitleController titleController;

    private static String colorCssStyle = "";
    private static String sizeCssStyle = "";

    private static PopupControl oldPopup;

    /**
     * The actual main method of the project (which will be called by the super
     * class after using launch() in the main method)
     *
     * @param primaryStage The stage which we will use to show our scene
     */
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
        primaryStage.setMinHeight(640);
        primaryStage.setMinWidth(1040);
    }

    /**
     * Method to initialize the starting border pane of the application, which
     * we will load other screens into.
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
//        final KeyCombination ctrlS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

        @Override
        public void handle(javafx.scene.input.KeyEvent event) {
            if (ctrlF.match(event) || event.getCode() == KeyCode.F10 || event.getCode() == KeyCode.F11) {
                if (primaryStage.isFullScreen()) {
                    primaryStage.setFullScreen(false);
                } else {
                    primaryStage.setFullScreen(true);
                }
            }
//            if (ctrlS.match(event)) {
//                System.out.println("Ctrl+S pressed: Save game.");
//            }
        }
    }

// This code to adjust the screen size is based on the code from http://stackoverflow.com/a/16608161
// Some adjustments have been made to improve it and comments have been added.
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

            double scaleFactor = newWidth / newHeight > ratio
                    ? newHeight / initHeight
                    : newWidth / initWidth;

            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);

                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);

                // Adjust the size of the background accordingly (otherwise the css and this changelistener would both scale the picture, scaling it twice too much)
                double backgroundScale = newWidth / 1024 > newHeight / 600 ? newWidth / 1024 * 600 / newHeight : newHeight / 600 * 1024 / newWidth;
                sizeCssStyle = "-fx-background-size:" + 1024 * backgroundScale + "," + 600 * backgroundScale + ";";
                rootLayout.setStyle(sizeCssStyle + colorCssStyle);

            } else {
                contentPane.setPrefWidth(Math.max(initWidth, newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
                sizeCssStyle = "-fx-background-size: 1024, 600;";
                rootLayout.setStyle(sizeCssStyle + colorCssStyle);
            }
        }
    }
//****************************************************************************************************
// End of code to adjust screen size.

    /**
     * this method cleans all sections of the rootLayout (borderpane)
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
        
        if(titleController != null)
                titleController.refreshRound();
        
        viewPath = changeNameToClassPath(viewPath);

        // get the previous margins of the center view
        Pane oldPane = (Pane) rootLayout.getCenter();
        Insets oldInsets = null;
        if(oldPane != null)
            oldInsets = BorderPane.getMargin(oldPane);
        
        // Set the center screen
        Object[] paneAndLoader = loadPane(viewPath);
        Pane pane = (Pane) paneAndLoader[0];
        if (viewPath.contains("GameScreen")) {
            BorderPane.setAlignment(pane, Pos.TOP_LEFT);
            BorderPane.setMargin(pane, new Insets(40, 40, 0, 0));
        }

        // if old center panes position is the same as the new one, set a transition
        FadeTransition fadeout = new FadeTransition(Duration.millis(0));
        if(oldPane != null && oldInsets != null && BorderPane.getMargin(pane).equals(oldInsets)){
            fadeout = new FadeTransition(Duration.millis(300), oldPane);
            fadeout.setFromValue(1);
            fadeout.setToValue(0);
            fadeout.play();
        }
        
        final ViewControllerInterface viewController = ((FXMLLoader) paneAndLoader[1]).getController();

        // when the fade out is finished, load the new center and fade it in
        fadeout.setOnFinished(e -> {
            // draw the new window
            pane.setOpacity(0);
            rootLayout.setCenter(pane);
            
            // let the new view fade in
            FadeTransition fadein = new FadeTransition(Duration.millis(1000), pane);
            fadein.setFromValue(0);
            fadein.setToValue(1);
            fadein.play();
//            // ViewControllerInterface viewController = getViewController(viewPath.split("/")[1]);
//            viewController = ((FXMLLoader) paneAndLoader[1]).getController();

            // Give the view controller a reference to this main controller class
            viewController.setMainController(this);
        });
        // if there is no fade in transition, handle onfinished
        if(fadeout.getDuration() == Duration.ZERO)
            fadeout.getOnFinished().handle(null);
        
        // draw circles if this is the choose positions screen
        // originally intended this would be a seperate stage, so
        // this is a kind of workaround, so it can be loaded into
        // the center
        if(viewController instanceof GameScreenChoosePositionsController){
            GameScreenChoosePositionsController controller = (GameScreenChoosePositionsController) viewController;
            controller.drawCircles(primaryStage, pane, competition.getTeamByName(chosenTeamName));
        }
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
        Pane pane = (Pane) paneAndLoader[0];
         rootLayout.setTop(pane);
        
        // let the new view fade in
        pane.setOpacity(0);
        FadeTransition fadein = new FadeTransition(Duration.millis(1000), pane);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();

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
        
        // let the new view fade in
        pane.setOpacity(0);
        FadeTransition fadein = new FadeTransition(Duration.millis(1000), pane);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();

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
     */
    public void createPopup(String viewPath, String popupTitle) {
        createPopup(viewPath, popupTitle, null);
    }

    /**
     * Create a pop-up window with an image.
     *
     * @param viewPath name of the .fxml file containing the pop-up window
     * @param popupTitle the title of the pop-up window
     * @param event the event to execute when the 'ok' button get clicked
     */
    public void createPopup(String viewPath, String popupTitle, EventHandler event) {

        // load the pane
        Object[] paneAndLoader = loadPane(changeNameToClassPath(viewPath));
        Pane pane = (Pane) paneAndLoader[0];

        PopupControl popup;
        AnchorLocation location = null;
        if (oldPopup != null && oldPopup.isShowing()) {
            // overwrite old popup window
            popup = oldPopup;
            popup.getScene().setRoot(pane);
            popup.sizeToScene();
            location = oldPopup.getAnchorLocation();
//            oldPopup.hide();
        } else {
            // create new popup window
            popup = new PopupControl();
            popup.getScene().setRoot(pane);
            popup.show(primaryStage);
            oldPopup = popup;
        }
        // create new popup window
//            popup = new PopupControl();
//            popup.getScene().setRoot(pane);
//            popup.show(primaryStage);
//            oldPopup = popup;
        if (location != null) {
            popup.setAnchorLocation(location);
        }
        makeDragable(popup);

        // get the pop-up's controller class
        PopupControllerInterface popupController = ((FXMLLoader) paneAndLoader[1]).getController();
        popupController.setPopupStage(popup);

        // if the EventHandler event (methods parameter) is not null, trigger it when the 'ok' button is clicked
        if (event != null) {
            popup.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    oldPopup = null;
                    if (popupController.isOkClicked()) {
                        event.handle(e);
                    }
                }
            });
        }

    }

    /**
     * This method makes the window root dragable. This method is based on the
     * makeDragable method from this project:
     * https://gist.github.com/jewelsea/3388637
     *
     * @param window the window to make dragable
     */
    public static void makeDragable(final Window window) {

        // class to store relative x and y coordinates
        class Delta {

            double x, y;
        }

        final Delta dragDelta = new Delta();
        Node node = window.getScene().getRoot();

        // when clickingset the position of the window relative to the mouse
        // and change the mouse cursor to 'move'
        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = window.getX() - mouseEvent.getScreenX();
                dragDelta.y = window.getY() - mouseEvent.getScreenY();
                node.setCursor(Cursor.MOVE);
            }
        });

        // when releasing the mouse button, change the cursor to a hand
        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setCursor(Cursor.HAND);
            }
        });

        // move the window when dragging with the mouse (relative to where the mouse is)
        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                window.setX(mouseEvent.getScreenX() + dragDelta.x);
                window.setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });

        // change cursor to a hand when entering the window
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    node.setCursor(Cursor.HAND);
                }
            }
        });

        // change to default cursor when leaving the window
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    node.setCursor(Cursor.DEFAULT);
                }
            }
        });
    }

    /**
     * Get the name of the chosen football club
     *
     * @return the name of the chosen team
     */
    public static String getChosenTeamName() {
        return chosenTeamName;
    }

    /**
     * Set the name of the chosen football club
     *
     * @param name the name of the chosen team
     */
    public static void SetChosenTeamName(String name) {
        chosenTeamName = name;
    }

    /**
     * Get the name of the player
     *
     * @return the players name
     */
    public static String getChosenName() {
        return chosenName;
    }

    /**
     * Set the name of the player
     *
     * @param chosenName the players name
     */
    public static void setChosenName(String chosenName) {
        Main.chosenName = chosenName;
    }

    /**
     * Get the current competition
     *
     * @return the current competition
     */
    public static Competition getCompetition() {
        return competition;
    }

    /**
     * Set the current competition
     *
     * @param competition the current competition
     */
    public static void setCompetition(Competition competition) {
        Main.competition = competition;
    }

    /**
     * Get the currently selected player (used by popup screens to communicate)
     *
     * @return the selected player
     */
    public static Players getSelectedPlayer() {
        return selectedPlayer;
    }

    /**
     * Set the currently selected player (used by popup screens to communicate)
     *
     * @param selectedPlayer the selected player
     */
    public static void setSelectedPlayer(Players selectedPlayer) {
        Main.selectedPlayer = selectedPlayer;
    }

    /**
     * Get the main border pane which contains all of the scenes (except for
     * popups)
     *
     * @return the main border pane
     */
    public static BorderPane getRootLayout() {
        return rootLayout;
    }

    /**
     * Get the main stage (which contains the main border pane)
     *
     * @return the main stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Get the main menu's controller (to check which tab is being showed)
     *
     * @return the main menu's controller
     */
    public static GameScreenMenuController getMenuController() {
        return menuController;
    }

    /**
     * Set the main menu's controller (to check which tab is being showed)
     *
     * @param menuController the main menu's controller
     */
    public static void setMenuController(GameScreenMenuController menuController) {
        Main.menuController = menuController;
    }

    /**
     * Get the title bar's controller (to check which tab is being showed)
     *
     * @return the title bar's controller
     */
    public static GameScreenTitleController getTitleController() {
        return titleController;
    }

    /**
     * Set the title bar's controller (to check which tab is being showed)
     *
     * @param titleController the title bar's controller
     */
    public static void setTitleController(GameScreenTitleController titleController) {
        Main.titleController = titleController;
    }

    /**
     * Get the current color settings
     *
     * @return string of the css which defines the current colors
     */
    public static String getColorCssStyle() {
        return colorCssStyle;
    }

    /**
     * Set the current color settings
     *
     * @param colorCssStyle string of the css which defines the current colors
     */
    public static void setColorCssStyle(String colorCssStyle) {
        Main.colorCssStyle = colorCssStyle;
    }

    /**
     * Get the current size settings
     *
     * @return string of the css which defines the current background image size
     */
    public static String getSizeCssStyle() {
        return sizeCssStyle;
    }

    /**
     * Get the instance of the previous popup's controller
     *
     * @return the previous popup's controller
     */
    public static PopupControl getOldPopup() {
        return oldPopup;
    }

    /**
     * The main method which launches our application
     *
     * @param args Command Line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    public void playMatch(CalculatedMatch match){
        // load the view
        GameScreenFootballFieldController viewController;
        Pane pane;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/FootballField.fxml"));
            pane = (Pane) loader.load();
            viewController = (GameScreenFootballFieldController) loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(AnimateFootballMatch.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to read FootballField.fxml file");
            return;
        }

        // set position
        BorderPane.setAlignment(pane, Pos.TOP_LEFT);
        BorderPane.setMargin(pane, new Insets(40, 40, 0, 0));
        
        FadeTransition fadeout = new FadeTransition(Duration.millis(300), (Pane) rootLayout.getCenter());
        fadeout.setFromValue(1);
        fadeout.setToValue(0);
        fadeout.play();

        // when the fade out is finished, load the new center and fade it in
        fadeout.setOnFinished(e -> {
            // draw the new window
            pane.setOpacity(0);
            rootLayout.setCenter(pane);
            
            // let the new view fade in
            FadeTransition fadein = new FadeTransition(Duration.millis(1000), pane);
            fadein.setFromValue(0);
            fadein.setToValue(1);
            fadein.play();
        });

        
        // animate the football match
        AnimateFootballMatch.playMatch(match, viewController, pane);
        
        // reset animation variables
        AnimateFootballMatch.reset();
    }
}
