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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;

/**
 *
 * @author Faris
 * @author Jochem
 */
public class GameScreenTEAMController implements ViewControllerInterface {

    @FXML
    private TableView<Players> playerTable;
    @FXML
    private TableColumn<Players, Number> columnNo;
    @FXML
    private TableColumn<Players, String> columnName;
    @FXML
    private TableColumn<Players, String> columnAbility;
    @FXML
    private TableColumn<Players, String> columnAvailable;
    @FXML
    private TableColumn<Players, String> columnType;

    private static Main mainController;

    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the Players table with the 5 columns
        columnNo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber())); 
        columnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurName())); 
        columnAbility.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAbilityStr())); 
        columnAvailable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isAvailable() ? "Yes" : "No")); 
        columnType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKind())); 
    }

    @Override
    public void setMainController(Main mainController) {
        this.mainController = mainController;
        
        // Add data to the table and sort number column
        playerTable.setItems(FXCollections.observableArrayList(mainController.getPlayersData(mainController.getChosenTeamName())));
        columnNo.setSortType(SortType.ASCENDING);
        playerTable.getSortOrder().clear();
        playerTable.getSortOrder().add(columnNo);
    }

    @FXML
    private void moreInfoButton(){
        Players selectedPlayer = playerTable.getSelectionModel().selectedItemProperty().get();
        if(selectedPlayer != null && selectedPlayer instanceof Player){
            Main.setSelectedPlayer(selectedPlayer);
            mainController.createPopup("PopupMOREINFOPLAYER", "Player info");
        } else if(selectedPlayer instanceof Goalkeeper){
            Main.setSelectedPlayer(selectedPlayer);
            mainController.createPopup("PopupMOREINFOGOALKEEPER", "Player info");
        }
    }
    
    @FXML
    private void sellPlayerButton(){
        Players selectedPlayer = playerTable.getSelectionModel().selectedItemProperty().get();
        if(selectedPlayer != null){
            Main.setSelectedPlayer(selectedPlayer);
            if(mainController.createPopup("PopupSELECTPRICE", "Sell Player"))
                System.out.println("Put player on market.");
        }
    }
}
