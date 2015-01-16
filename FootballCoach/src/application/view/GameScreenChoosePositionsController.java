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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author Faris
 */
public class GameScreenChoosePositionsController implements ViewControllerInterface {

    private static final Circle playerCircle[] = new Circle[11];
    private static final Text playerText[] = new Text[11]; // texts above the circles
    private Stage stage;
//    private Team team;
//    private static ArrayList<Player> fieldPlayerList;
//    private static ArrayList<Goalkeeper> keeperList;
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
            dragDelta.x = circle.getParent().getLayoutX() - mouseEvent.getScreenX();
            dragDelta.y = circle.getParent().getLayoutY() - mouseEvent.getScreenY();
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
            
            //make sure the circle can't be outside of the preset bounds
            if (mouseEvent.getSceneX() - fieldImageGroup.getLayoutX() - fieldImageGroup.getParent().getLayoutX() - 20 - circle.getRadius()/2 < relativeX(115)) {
                circle.getParent().setLayoutX(relativeX(115) - circle.getParent().getLayoutBounds().getMinX());
            } else if (mouseEvent.getSceneX() - fieldImageGroup.getLayoutX() - fieldImageGroup.getParent().getLayoutX() - 20 + circle.getRadius()/2 > relativeX(845)) {
                circle.getParent().setLayoutX(relativeX(845) - circle.getParent().getLayoutBounds().getMinX());
            } else {
                circle.getParent().setLayoutX(mouseEvent.getScreenX() + dragDelta.x);
            }
            
            if (mouseEvent.getSceneY() - fieldImageGroup.getLayoutY() - fieldImageGroup.getParent().getLayoutY() + circle.getRadius()/2 < relativeY(110)) {
                circle.getParent().setLayoutY(relativeY(110) - circle.getParent().getLayoutBounds().getMaxY());
            } else if (mouseEvent.getSceneY() - fieldImageGroup.getLayoutY() - fieldImageGroup.getParent().getLayoutY() - circle.getRadius()/2 > relativeY(660)) {
                circle.getParent().setLayoutY(relativeY(660) + 8 - circle.getParent().getLayoutBounds().getMaxY());
            } else {
                circle.getParent().setLayoutY(mouseEvent.getScreenY() + dragDelta.y);
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
        for(int i=0; i<11; i++){
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


    private void setSelectBox() {

        // correctly display the players surname in the button area of the combo box
        // and set the color of the player according to it's type
        selectPlayerBox.setButtonCell(new ListCell<Players>() {
            @Override
            protected void updateItem(Players player, boolean empty) {
                super.updateItem(player, empty);
                if (player != null) {
                    setText(player.getSurName());

                    //decide the color based on the kind of player
                    switch (player.getKind()) {
                        case "Forward":
                            setTextFill(Color.RED);
                            break;
                        case "Defender":
                            setTextFill(Color.DARKGREEN);
                            break;
                        case "Allrounder":
                        case "Midfielder":
                            setTextFill(Color.ORANGE);
                            break;
                        default:
                            setTextFill(Color.BLACK);
                            break;
                    }
//                            setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
                    setFont(Font.font(13));
                }
            }
        });

        // correctly display the players surname in the select area of the combo box
        // and set the color of the player according to it's type
        selectPlayerBox.setCellFactory(selectListFactory);
        selectPlayerBox.addEventHandler(ActionEvent.ACTION, onSelected);
    }

    private static Callback<ListView<Players>, ListCell<Players>> selectListFactory = new Callback<ListView<Players>, ListCell<Players>>() {
        @Override
        public ListCell<Players> call(ListView<Players> p) {
            return new ListCell<Players>() {
                @Override
                protected void updateItem(Players player, boolean empty) {

                    super.updateItem(player, empty);

                    if (player != null) {
                        setText(player.getSurName());

                        //decide the color based on the kind of player
                        switch (player.getKind()) {
                            case "Forward":
                                setTextFill(Color.RED);
                                break;
                            case "Defender":
                                setTextFill(Color.DARKGREEN);
                                break;
                            case "Allrounder":
                            case "Midfielder":
                                setTextFill(Color.ORANGE);
                                break;
                            default:
                                setTextFill(Color.BLACK);
                                break;
                        }

                        if (teamPositions.getPlayers().contains(player)) {
                            setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, null, null)));
                        } else {
                            setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                        }

                        setFont(Font.font(13));
                    }
                }
            };
        }
    };

    @FXML
    private void startMatchButton() {
        if (teamPositions.checkValid()) {
            teamPositions.TESSTST_PRINT();
            PlayAnimation.playMatches(teamPositions, mainController);
//            int pointsLeft = result.getHomeTeam().getName().equals(nameLeft.getText()) ? result.getPointsHomeTeam() : result.getPointsVisitorTeam();
//            int pointsRight = result.getHomeTeam().getName().equals(nameLeft.getText()) ? result.getPointsVisitorTeam() : result.getPointsHomeTeam();
//            score.setText(pointsLeft + " - " + pointsRight);
        } else {
            Dialogs.create()
                    .title("Invalid selection")
                    .masthead("Not all players are selected")
                    .message("Please select a player for each circle.")
                    .owner(stage)
                    .showWarning();
        }
    }

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

    public static Circle[] getPlayerCircle() {
        return playerCircle;
    }

    public ComboBox<Players> getSelectPlayerBox() {
        return selectPlayerBox;
    }

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
    
    
    private double relativeX(double x){
        return x * 466.0 / 1020.0;
    }
    
    public static double actualX(double x){
        return x  * 1020.0 / 466.0;
    }
    
    private double relativeY(double y){
        return y * 350.0 / 765.0;
    }
    
    public static double actualY(double y){
        return y * 765.0 / 350.0;
    }
}
