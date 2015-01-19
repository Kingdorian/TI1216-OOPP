/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.model.Card;
import application.model.Goalkeeper;
import application.model.Player;
import application.model.Players;
import application.model.Team;
import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * This is the controller class of the OTHERTEAMS screen of the main view of the
 * game screen.
 *
 * @author Faris
 * @author Jochem
 */
public class GameScreenOTHERTEAMSController implements ViewControllerInterface {

    private static Main mainController;

    @FXML
    private ComboBox selectTeamBox;
    @FXML
    private TableView<Players> playerTable;
    @FXML
    private TableColumn<Players, Number> columnNo;
    @FXML
    private TableColumn<Players, String> columnName;
    @FXML
    private TableColumn<Players, String> columnAbility;
    @FXML
    private TableColumn<Players, Boolean> columnAvailable;
    @FXML
    private TableColumn<Players, String> columnType;
    @FXML
    private TableColumn<Players, Players> columnCard;

    /**
     * This is an event handler class definition, which will be triggered when
     * an items in the dropdown list is selected.
     */
    private final EventHandler onSelected = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t){
            String selectedTeamName = selectTeamBox.getSelectionModel().getSelectedItem().toString();
            // Add data to the table and sort number column
            playerTable.setItems(FXCollections.observableArrayList(Main.getCompetition().getTeamByName(selectedTeamName).getPlayers()));
            columnNo.setSortType(TableColumn.SortType.ASCENDING);
            playerTable.getSortOrder().clear();
            playerTable.getSortOrder().add(columnNo);
        }
    };

    /**
     * This code is executed when the view is loaded. It sets the main texts of
     * this view.
     */
    @FXML
    private void initialize() {
        playerTable.setPlaceholder(new Label("Please select a team in the dropdown list above."));

        // Initialize the Players table with the 5 columns
        columnNo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber()));
        columnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurName()));
        columnAbility.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAbilityStr()));
        
        //set color of the ability according to the ability (very good = green, very bad = red
        columnAbility.setCellFactory(new Callback<TableColumn<Players, String>, TableCell<Players, String>>() {
            @Override
            public TableCell<Players, String> call(TableColumn<Players, String> param) {
                return new TableCell<Players, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            double ability = Double.parseDouble(item);
                            double green = ability * 20;
                            this.setStyle("-fx-text-fill: hsb(" + green + ",100%,80%);");
                            setText(item);
                        }
                    }
                };
            }
        });
        
        columnAvailable.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isAvailable()));
        columnAvailable.setCellFactory(new Callback<TableColumn<Players, Boolean>, TableCell<Players, Boolean>>() {
            @Override
            public TableCell<Players, Boolean> call(TableColumn<Players, Boolean> param) {
                return new TableCell<Players, Boolean>() {
                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            if(item)
                                this.setStyle("-fx-text-fill: rgb(255,255,255);");
                            else
                                this.setStyle("-fx-text-fill: rgb(215,59,59);");
                            setText(item ? "Yes" : "No");
                        }
                    }
                };
            }
        });
        
        columnType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKind()));
        
        //set color of text according to the type
        columnType.setCellFactory(new Callback<TableColumn<Players, String>, TableCell<Players, String>>() {
            @Override
            public TableCell<Players, String> call(TableColumn<Players, String> param) {
                return new TableCell<Players, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            switch (item) {
                                case "Allrounder":
                                case "Midfielder":
                                    this.setStyle("-fx-text-fill: rgb(232,123,16);");
                                    break;
                                case "Forward":
                                    this.setStyle("-fx-text-fill: rgb(215,59,59);");
                                    break;
                                case "Defender":
                                    this.setStyle("-fx-text-fill: rgb(63,212,25);");
                                    break;
                                case "Goalkeeper":
                                    this.setStyle("-fx-text-fill: rgb(255,255,255);");
                                    break;
                            }
                            setText(item);
                        }
                    }
                };
            }
        });

        columnCard.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue()));

        //get the image of the kind of card the player in the table cell has
        columnCard.setCellFactory(new Callback<TableColumn<Players, Players>, TableCell<Players, Players>>() {
            @Override
            public TableCell<Players, Players> call(TableColumn<Players, Players> param) {
                return new TableCell<Players, Players>() {
                    ImageView imgVw;

                    {
                        imgVw = new ImageView();
                        imgVw.setFitHeight(20);
                        imgVw.setFitWidth(200.0 / 13.0);
                        setGraphic(imgVw);
                    }

                    @Override
                    public void updateItem(Players item, boolean empty) {
                        if (item != null) {
                            Card card = item.getCard();
                            if (card == Card.YELLOW) {
                                imgVw.setImage(new Image(Main.class.getResource("img/yellow_card.png").toString(), 200.0 / 13.0, 20, true, false));
                            } else if (card == Card.RED) {
                                imgVw.setImage(new Image(Main.class.getResource("img/red_card.png").toString(), 200.0 / 13.0, 20, true, false));
                            } else {
                                imgVw.setImage(null);
                            }
                        }
                    }
                };
            }
        });
    }

    /**
     * This gives this class a reference to the main class
     *
     * @param mainController the main class
     */
    @Override
    public void setMainController(Main mainController) {
        GameScreenOTHERTEAMSController.mainController = mainController;

        // add items to the dropdown list
        ArrayList<String> teamNames = new ArrayList<>();
        Team[] teams = mainController.getCompetition().getTeams();
        for (Team t : teams) {
            if (!t.getName().equals(mainController.getChosenTeamName())) {
                teamNames.add(t.getName());
            }
        }
        selectTeamBox.setItems(FXCollections.observableArrayList(teamNames));

        // set the event for when an item is selected
        selectTeamBox.addEventHandler(ActionEvent.ACTION, onSelected);
    }

    /**
     * This method is triggered when the more info button is clicked (event
     * handler assigned in the .fxml) and it creates a popup containing more
     * info about the selected player.
     */
    @FXML
    private void moreInfoButton() {
        Players selectedPlayer = playerTable.getSelectionModel().selectedItemProperty().get();
        if (selectedPlayer != null && selectedPlayer instanceof Player) {
            Main.setSelectedPlayer(selectedPlayer);
            mainController.createPopup("PopupMOREINFOPLAYER", "Player info");
        } else if (selectedPlayer instanceof Goalkeeper) {
            Main.setSelectedPlayer(selectedPlayer);
            mainController.createPopup("PopupMOREINFOGOALKEEPER", "Player info");
        }
    }
}
