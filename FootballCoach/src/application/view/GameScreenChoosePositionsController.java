/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.PlayAnimation;
import application.animation.Container.DefaultPos;
import application.animation.Container.TeamPositions;
import application.model.Goalkeeper;
import application.model.Player;
import application.model.Players;
import application.model.Team;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Faris
 */
public class GameScreenChoosePositionsController implements ViewControllerInterface {

    private static final Circle playerCircle[] = new Circle[11];
    private static final Text playerText[] = new Text[11]; // texts above the circles
    private Stage stage;
    private static TeamPositions teamPositions;
    private static int selectedCircleID;
    private static GameScreenChoosePositionsController thisController;

    private static Main mainController;

    @FXML
    private ComboBox<Players> selectPlayerBox;
    @FXML
    private Text defenseText;
    @FXML
    private Text staminaText;
    @FXML
    private Text attackText;
    @FXML
    private Text stopPower;
    @FXML
    private Text penaltyStopPower;
    @FXML
    private Group fieldImageGroup;
    @FXML
    private Button startButton;

    /**
     * this class implements EventHandler and can be added to a circle to make
     * it dragable when the cursor moves over the circle (because we can't add
     * the event handlers directly when loading, since the circles won't be
     * referrenced by the fxml file yet)
     */
    private class CircleDragHandler implements EventHandler {

        private final Circle c;
        private final boolean isKeeper;

        /**
         * Constructor
         *
         * @param c Circle to add event handler to
         */
        public CircleDragHandler(final Circle c, boolean isKeeper) {
            this.c = c;
            this.isKeeper = isKeeper;
        }

        /**
         * Change the x and y coordinate of the circle when the circle is
         * dragged and change cursors look when mouse events occur
         *
         * @param event and event (nothing will happen if this isn't a mouse
         * event)
         */
        @Override
        public void handle(Event event) {
            if (!isKeeper) {
                //make the circle dragable when any mouse event occurs
                c.getScene().getRoot().setCursor(Cursor.HAND);
                makeDragable(c);
            } else {
                //make the circle dragable when any mouse event occurs
                c.getScene().getRoot().setCursor(Cursor.HAND);
                setKeeperEvents(c);
            }
            //remove this event handler, so it will only get triggered once
            c.removeEventHandler(MouseEvent.ANY, this);
        }
    }

    /**
     * Handle the event when a player is selected
     */
    private final EventHandler onSelected = (Event e) -> {
        Players player = selectPlayerBox.getSelectionModel().getSelectedItem();

        // if no one is selected, return
        if (selectPlayerBox.getSelectionModel().isEmpty()) {
            return; //if nothing is selected, return
        }

        // add player to the clicked circle
        if (selectedCircleID != -1) {
            teamPositions.addPlayer(player, playerCircle[selectedCircleID], selectedCircleID, true);
            resetCircleText();

            // reset cell factory each time, so the text color will be changed
            selectPlayerBox.setCellFactory(null);
            selectPlayerBox.setCellFactory(selectListFactory);
        }

        if (player instanceof Player) {
            Player p = (Player) player;
            defenseText.setText(Integer.toString(p.getDefence()));
            staminaText.setText(Integer.toString(p.getStamina()));
            attackText.setText(Integer.toString(p.getAttack()));
            stopPower.setText("-");
            penaltyStopPower.setText("-");
        } else {
            Goalkeeper k = (Goalkeeper) player;
            defenseText.setText("-");
            staminaText.setText("-");
            attackText.setText("-");
            stopPower.setText(Integer.toString(k.getStopPower()));
            penaltyStopPower.setText(Integer.toString(k.getPenaltyStopPower()));
        }
    };

    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
    }

    /**
     * This method makes the group of the circle dragable. This method is based
     * on the makeDragable method from this project:
     * https://gist.github.com/jewelsea/3388637
     *
     * @param circle the circle who's group should be made dragable
     */
    public void makeDragable(Circle circle) {

        // class to store relative x and y coordinates
        class Delta {

            double x, y;
        }

        final Delta dragDelta = new Delta();
        Node node = circle.getScene().getRoot();

        // when clicking set the position of the group relative to the mouse
        // and change the mouse cursor to 'move'
        circle.setOnMousePressed((MouseEvent mouseEvent) -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = circle.getParent().getLayoutBounds().getMinX() + fieldImageGroup.getParent().getLayoutX() + fieldImageGroup.getLayoutX() + 25;
            dragDelta.y = circle.getParent().getLayoutBounds().getMaxY() + fieldImageGroup.getParent().getLayoutY() + fieldImageGroup.getLayoutY() - circle.getRadius();
            node.setCursor(Cursor.MOVE);
            // reset all circles colors
            for (int i = 0; i < 11; i++) {
                playerCircle[i].setFill(Color.BLUE);
                if (playerCircle[i].equals(circle)) {
                    selectedCircleID = i;
                }
            }

            //set the selection list items to field players
            thisController.getSelectPlayerBox().setItems(FXCollections.observableArrayList(teamPositions.getFieldPlayerList()));

            // select the player that corresponds with the clicked circle
            Players player = teamPositions.getPlayer(selectedCircleID);
            if (player != null) {
                thisController.getSelectPlayerBox().getSelectionModel().select(player);
            } else {
                thisController.getSelectPlayerBox().getSelectionModel().select(-1);
            }

            // set this circles color to a different color, so it seems
            // as if it is selected
            circle.setFill(Color.AQUA);
        });

        // when releasing the mouse button, change the cursor to a hand
        circle.setOnMouseReleased((MouseEvent mouseEvent) -> {
            node.setCursor(Cursor.HAND);
        });

        // move the group when dragging with the mouse (relative to where the mouse is)
        circle.setOnMouseDragged((MouseEvent mouseEvent) -> {

            double screenScaleFactor = Main.getScreenScaleFactor();

            //make sure the circle can't be outside of the preset bounds
            if (mouseEvent.getSceneX() / screenScaleFactor - fieldImageGroup.getLayoutX() - fieldImageGroup.getParent().getLayoutX() - 20 - circle.getRadius() / 2 < relativeX(115)) {
                circle.getParent().setLayoutX(relativeX(115) - circle.getParent().getLayoutBounds().getMinX());
            } else if (mouseEvent.getSceneX() / screenScaleFactor - fieldImageGroup.getLayoutX() - fieldImageGroup.getParent().getLayoutX() - 20 + circle.getRadius() / 2 > relativeX(860)) {
                circle.getParent().setLayoutX(relativeX(845) - circle.getParent().getLayoutBounds().getMinX());
            } else {
                circle.getParent().setLayoutX(mouseEvent.getSceneX() / screenScaleFactor - dragDelta.x);
            }

            if (mouseEvent.getSceneY() / screenScaleFactor - fieldImageGroup.getLayoutY() - fieldImageGroup.getParent().getLayoutY() + circle.getRadius() / 2 < relativeY(110)) {
                circle.getParent().setLayoutY(relativeY(120) - circle.getParent().getLayoutBounds().getMaxY());
            } else if (mouseEvent.getSceneY() / screenScaleFactor - fieldImageGroup.getLayoutY() - fieldImageGroup.getParent().getLayoutY() - circle.getRadius() / 2 > relativeY(660)) {
                circle.getParent().setLayoutY(relativeY(670) + 8 - circle.getParent().getLayoutBounds().getMaxY());
            } else {
                circle.getParent().setLayoutY(mouseEvent.getSceneY() / screenScaleFactor - dragDelta.y);
            }
        });

        // change cursor to a hand when entering the circle
        circle.setOnMouseEntered((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                node.setCursor(Cursor.HAND);
            }
        });

        // change to default cursor when leaving the circle
        circle.setOnMouseExited((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                node.setCursor(Cursor.DEFAULT);
            }
        });
    }

    /**
     * Apply functionality to the circle of the keeper
     *
     * @param circle the circle of the keeper
     */
    public static void setKeeperEvents(Circle circle) {

        Node node = circle.getScene().getRoot();

        // when clicked, select this circle
        circle.setOnMousePressed(e -> {

            // reset all circles colors
            for (int i = 0; i < 11; i++) {
                playerCircle[i].setFill(Color.BLUE);
            }

            selectedCircleID = 0;

            //set the selection list items to field players
            thisController.getSelectPlayerBox().setItems(FXCollections.observableArrayList(teamPositions.getKeeperList()));

            // select the player that corresponds with the clicked circle
            Players player = teamPositions.getPlayer(selectedCircleID);
            if (player != null) {
                thisController.getSelectPlayerBox().getSelectionModel().select(player);
            } else {
                thisController.getSelectPlayerBox().getSelectionModel().select(-1);
            }

            // set this circles color to a different color, so it seems
            // as if it is selected
            circle.setFill(Color.AQUA);
        });

        // change cursor to a hand when entering the group
        circle.setOnMouseEntered((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                node.setCursor(Cursor.HAND);
            }
        });

        // change to default cursor when leaving the circle
        circle.setOnMouseExited((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                node.setCursor(Cursor.DEFAULT);
            }
        });

        // change to default cursor when leaving the circle
        circle.setOnMouseDragExited((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                node.setCursor(Cursor.DEFAULT);
            }
        });
    }

    /**
     * Add the player circles to the scene
     *
     * @param stage the stage
     * @param team the players team
     */
    public void drawCircles(Stage stage, Team team) {
        this.stage = stage;

        this.selectedCircleID = -1;
        this.thisController = this;
        this.teamPositions = new TeamPositions(team);

        setSelectBox();

        // create the player circles
        for (int i = 0; i < 11; i++) {
            //make sure the enum value won't be change
            final Circle c = DefaultPos.Loop.getL(i).circle;
            playerCircle[i] = new Circle(c.getCenterX(), c.getCenterY(), 8, Color.BLUE);
        }

        for (Circle circle : playerCircle) {
            circle.setCenterX(relativeX(circle.getCenterX()));
            circle.setCenterY(relativeY(circle.getCenterY()));
        }

        // add players name to the circles + set a lighting effect
        // and add the circle+text to the scene
        for (int i = 0; i < 11; i++) {
            // add player name
            Text text = new Text();
            text.setText("...");
            text.setX(playerCircle[i].getCenterX() - 25);
            text.setY(playerCircle[i].getCenterY() - 18);
            text.setFill(Color.WHITE);
            text.setFont(new Font("Verdana bold", 14));
            playerText[i] = text;

            // add lighting effect
            playerCircle[i].setEffect(new Lighting());

            // group circle and name togheter and add them to the scene
            Group group = new Group(playerCircle[i], text);
            fieldImageGroup.getChildren().add(group);
        }

        // make all circles dragable (indirectly via an event handler)
        playerCircle[0].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[0], true)); // keeper circle which shouldn't be dragable
        playerCircle[1].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[1], false));
        playerCircle[2].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[2], false));
        playerCircle[3].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[3], false));
        playerCircle[4].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[4], false));
        playerCircle[5].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[5], false));
        playerCircle[6].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[6], false));
        playerCircle[7].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[7], false));
        playerCircle[8].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[8], false));
        playerCircle[9].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[9], false));
        playerCircle[10].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[10], false));

        teamPositions.setDefaultLeftPlayers();
        resetCircleText();
    }

    /**
     * Set the colors of the players according to their type (in the button
     * area)
     */
    private void setSelectBox() {

        // correctly display the players surname in the button area of the combo box
        // and set the color of the player according to it's type
        selectPlayerBox.setButtonCell(new ListCell<Players>() {
            @Override
            protected void updateItem(Players player, boolean empty) {

                super.updateItem(player, empty);

                if (player != null) {
                    setText(player.getSurName());

                    String style;
                    //decide the color based on the kind of player
                    switch (player.getKind()) {
                        case "Forward":
                            style = "-fx-text-fill: -fx-red;";
                            break;
                        case "Defender":
                            style = "-fx-text-fill: -fx-green;";
                            break;
                        case "Allrounder":
                        case "Midfielder":
                            style = "-fx-text-fill: -fx-orange;";
                            break;
                        default:
                            style = "-fx-text-fill: rgb(255,255,255);";
                            break;
                    }

                    if (teamPositions.getPlayers().contains(player)) {
                        if (!(player instanceof Goalkeeper)) {
                            setStyle("-fx-background-color: -fx-base;" + style);
                        } else {
                            setStyle("-fx-background-color: darkred;" + style);
                        }
                    } else {
                        setStyle(style);
                    }
                }
            }
        });

        // correctly display the players surname in the select area of the combo box
        // and set the color of the player according to it's type
        selectPlayerBox.setCellFactory(selectListFactory);
        selectPlayerBox.addEventHandler(ActionEvent.ACTION, onSelected);
    }

    /**
     * Set the colors of the players according to their type (in the list)
     */
    private final static Callback<ListView<Players>, ListCell<Players>> selectListFactory = new Callback<ListView<Players>, ListCell<Players>>() {
        @Override
        public ListCell<Players> call(ListView<Players> p) {
            return new ListCell<Players>() {
                @Override
                protected void updateItem(Players player, boolean empty) {

                    super.updateItem(player, empty);

                    if (player != null) {
                        setText(player.getSurName());

                        String style;
                        //decide the color based on the kind of player
                        switch (player.getKind()) {
	                        case "Forward":
	                            style = "-fx-text-fill: -fx-red;";
	                            break;
	                        case "Defender":
	                            style = "-fx-text-fill: -fx-green;";
	                            break;
	                        case "Allrounder":
	                        case "Midfielder":
	                            style = "-fx-text-fill: -fx-orange;";
	                            break;
	                        default:
	                            style = "-fx-text-fill: rgb(255,255,255);";
	                            break;
                        }

                        if (teamPositions.getPlayers().contains(player)) {
                            if (!(player instanceof Goalkeeper)) {
                                setStyle("-fx-background-color: -fx-base;" + style);
                            } else {
                                setStyle("-fx-background-color: darkred;" + style);
                            }
                        } else {
                            setStyle(style);
                        }
                    }
                }
            };
        }
    };

    /**
     * Handle the event when a player clicks on the start match button
     */
    @FXML
    private void startMatchButton() {
        if (teamPositions.checkValid()) {
            startButton.setDisable(true);
            teamPositions.TESSTST_PRINT();
            PlayAnimation.playMatches(teamPositions, mainController);
        } else {
            Main.createModal("Invalid selection", "Not all players are selected", "Please select a player for each circle.");
        }
    }

    /**
     * Handle the event when a player clicks on the cancel button
     */
    @FXML
    private void cancelButton() {
        Main.getMenuController().getCurrentMenuField().setText("Play Match");
        mainController.setCenterView("GameScreenPLAYMATCH");
    }

    /**
     * Move all circle groups to their original positions
     */
    @FXML
    private void resetButton() {
        for (int i = 0; i < playerCircle.length; i++) {
            playerCircle[i].getParent().setLayoutX(0);
            playerCircle[i].getParent().setLayoutY(0);
        }
    }

    /**
     * Get all circles of the players
     *
     * @return the circles of all players
     */
    public static Circle[] getPlayerCircle() {
        return playerCircle;
    }

    /**
     * Get the selected player's circle
     *
     * @return the selected player's circle
     */
    public ComboBox<Players> getSelectPlayerBox() {
        return selectPlayerBox;
    }

    /**
     * Reset the circles' texts
     */
    private void resetCircleText() {
        for (int i = 0; i < playerText.length; i++) {
            Players player = teamPositions.getPlayer(i);
            if (player == null) {
                playerText[i].setText("...");
            } else {
                playerText[i].setText(player.getSurName());
            }
        }
    }

    /**
     * Get the positions of the palyers
     *
     * @return the positions of the players
     */
    public static TeamPositions getTeamPositions() {
        return teamPositions;
    }

    /**
     * This gives this class a reference to the main class
     *
     * @param mainController the main class
     */
    @Override
    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }

    /**
     * Calculate the relative x-coordinate, from an actual field coordinate
     *
     * @param x the actual x-coordinate
     * @return the relative x-coordinate
     */
    private double relativeX(double x) {
        return x * 466.0 / 1020.0;
    }

    /**
     * Caculate the actual x-coordinate on a field, from a relative x-coordinate
     *
     * @param x the relative x-coordinate
     * @return the actual x-coordinate
     */
    public static double actualX(double x) {
        return x * 1020.0 / 466.0;
    }

    /**
     * Calculate the relative y-coordinate, from an actual field coordinate
     *
     * @param y the y-coordinate
     * @return the relative y-coordinate
     */
    private double relativeY(double y) {
        return y * 350.0 / 765.0;
    }

    /**
     * Caculate the actual y-coordinate on a field, from a relative y-coordinate
     *
     * @param y the relative y-coordinate
     * @return the actual y-coordinate
     */
    public static double actualY(double y) {
        return y * 765.0 / 350.0;
    }
}
