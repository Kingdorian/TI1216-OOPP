/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.model.Goalkeeper;
import application.model.Player;
import application.model.Players;
import application.model.Team;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Jochem
 */
public class GameScreenMARKETController implements ViewControllerInterface {

    private static Main mainController;
    private Players selectedPlayer;

    @FXML
    private TableView<Players> playerTable;
    @FXML
    private TableColumn<Players, Number> columnNo;
    @FXML
    private TableColumn<Players, String> columnName;
    @FXML
    private TableColumn<Players, String> columnClubName;
    @FXML
    private TableColumn<Players, String> columnType;
    @FXML
    private TableColumn<Players, String> columnAbility;
    @FXML
    private TableColumn<Players, String> columnEstimatedValue;
    @FXML
    private TableColumn<Players, String> columnPrice;

    @FXML
    private Button buyButton;
    @FXML
    private Button removeButton;

    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize() {
        playerTable.setPlaceholder(new Label("There are no players for sale at the moment"));

        // Initialize the Players table with the 5 columns
        columnNo.setCellValueFactory(cellData -> new SimpleIntegerProperty(Main.getCompetition().getMarket().getPlayersForSale().indexOf(cellData.getValue()) + 1));
        columnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurName()));
        columnClubName.setCellValueFactory(cellData -> new SimpleStringProperty(Main.getCompetition().getPlayersTeam(cellData.getValue()).getName()));
        columnAbility.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAbilityStr()));
        columnType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKind()));
        columnEstimatedValue.setCellValueFactory(cellData -> new SimpleStringProperty(formatPrice(cellData.getValue().getPrice())));
        columnPrice.setCellValueFactory(cellData -> new SimpleStringProperty(formatPrice(Main.getCompetition().getMarket().getPlayersPrice(cellData.getValue()))));

        // add selection listener: disable buy button when player of own team is selected, otherwise disable remove button
        playerTable.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> change) {
                if(playerTable.getSelectionModel().selectedItemProperty().get() != null){
                    selectedPlayer = playerTable.getSelectionModel().selectedItemProperty().get();
                    if (Main.getCompetition().getTeamByName(Main.getChosenTeamName()).getPlayers().contains(selectedPlayer)) {
                        buyButton.setDisable(true);
                        removeButton.setDisable(false);
                    } else {
                        buyButton.setDisable(false);
                        removeButton.setDisable(true);
                    }
                }
            }
        });
    }

    /**
     * Format a large number to make it more readable
     *
     * @param number the number to format
     * @return a string containing the formatted number
     */
    private String formatPrice(int number) {
        String estimatedPriceString = Integer.toString(number);
        String formattedEstimatedPrice = "";
        int j = 0;
        for (int i = estimatedPriceString.length() - 1; i >= 0; i--, j++) {
            if (j % 3 == 0 && j != 0) {
                formattedEstimatedPrice = "," + formattedEstimatedPrice;
            }
            formattedEstimatedPrice = estimatedPriceString.charAt(i) + formattedEstimatedPrice;
        }
        return "$ " + formattedEstimatedPrice;
    }

    @Override
    public void setMainController(Main mainController) {
        GameScreenMARKETController.mainController = mainController;

        reloadTable();
    }

    @FXML
    private void moreInfoButton() {
        if (selectedPlayer != null && selectedPlayer instanceof Player) {
            Main.setSelectedPlayer(selectedPlayer);
            mainController.createPopup("PopupMOREINFOPLAYER", "Player info");
        } else if (selectedPlayer instanceof Goalkeeper) {
            Main.setSelectedPlayer(selectedPlayer);
            mainController.createPopup("PopupMOREINFOGOALKEEPER", "Player info");
        }
    }

    @FXML
    private void removePlayerButton() {
        Team myTeam = Main.getCompetition().getTeamByName(Main.getChosenTeamName());
        if (myTeam.getPlayers().contains(selectedPlayer)) {

            // remove player from market
            Main.getCompetition().getMarket().removePlayer(selectedPlayer);
            
            Dialogs.create()
                            .title("Player Market")
                            .masthead("Removed player.")
                            .message("Removed " + selectedPlayer.getName() + " " + selectedPlayer.getSurName() + " from the player market.")
                            .showInformation();
            
            // reload the table
            reloadTable();
        }
    }
    
    @FXML
    private void buyPlayerButton(){
        int price = Main.getCompetition().getMarket().getPlayersPrice(selectedPlayer);
        if(Main.getCompetition().getPlayersTeam(selectedPlayer).transferTo(selectedPlayer, Main.getCompetition().getTeamByName(Main.getChosenTeamName()), price)){
            // transfer succesful
            Dialogs.create()
                            .title("Player Market")
                            .masthead("The player transferred to your team succesfully.")
                            .message("You have spend " + formatPrice(price) + " to buy player: " + selectedPlayer.getName() + " " + selectedPlayer.getSurName())
                            .showInformation();
            // reload table, clear selection and set buy and remove buttons to disabled
            reloadTable();
        } else
            // transfer not succesful
            Dialogs.create()
                            .title("Transfer failed")
                            .masthead("Transfer couldn't be made.")
                            .message("You do not have enough money.")
                            .showWarning();
    }
    
    private void reloadTable(){
            playerTable.setItems(FXCollections.observableArrayList(Main.getCompetition().getMarket().getPlayersForSale()));
            columnNo.setSortType(TableColumn.SortType.ASCENDING);
//            playerTable.getSortOrder().clear();
//            playerTable.getSortOrder().add(columnNo);
            
            playerTable.getSelectionModel().clearSelection();
            buyButton.setDisable(true);
            removeButton.setDisable(true);
            selectedPlayer = null;
    }
}
