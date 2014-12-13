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
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
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
    private TableColumn<Players, String> columnAvailable;
    @FXML
    private TableColumn<Players, String> columnType;
    
    // this event will be executed when an item in the dropdown list is selected:
    private final EventHandler onSelected = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent t){
            String selectedTeamName = selectTeamBox.getItems().get(selectTeamBox.getSelectionModel().getSelectedIndex()).toString();
            // Add data to the table
            playerTable.setItems(FXCollections.observableArrayList(mainController.getPlayersData(selectedTeamName)));
        }
    };
    
    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize() {
        playerTable.setPlaceholder(new Label("Please select a team in the dropdown list above."));
        
        // Initialize the Players table with the 5 columns
        columnNo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber())); 
        columnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurName())); 
        columnAbility.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAbility())); 
        columnAvailable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isAvailable() ? "Yes" : "No")); 
        columnType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKind())); 
    }

    @Override
    public void setMainController(Main mainController) {
        GameScreenOTHERTEAMSController.mainController = mainController;
        
        // add items to the dropdown list
        ArrayList<String> teamNames = new ArrayList<>();
        Team[] teams = mainController.getCompetition().getTeams();
        for(Team t : teams){
            if(!t.getName().equals(mainController.getChosenTeamName()))
            teamNames.add(t.getName());
        }
        selectTeamBox.setItems(FXCollections.observableArrayList(teamNames));
        
        // set the event for when an item is selected
        selectTeamBox.addEventHandler(ActionEvent.ACTION, onSelected);
    }
    
    @FXML
    private void moreInfoButton(){
        Players selectedPlayer = playerTable.getSelectionModel().selectedItemProperty().get();
        if(selectedPlayer != null && selectedPlayer instanceof Player){
            Main.setCurrentlySelected(selectedPlayer);
            mainController.createPopup("PopupMOREINFOPLAYER", "Player info");
        }
//        Players selectedPlayer = playerTable.getSelectionModel().selectedItemProperty().get();
//        if(selectedPlayer != null){
//            System.out.println("Selected player: " + selectedPlayer.getSurName());
//            if(selectedPlayer instanceof Player)
//                System.out.println( "Attack: " + ((Player)selectedPlayer).getAttack() + "\nDefense: " + ((Player)selectedPlayer).getDefence() + "\nStamina: " + ((Player)selectedPlayer).getStamina());
//            else
//                System.out.println( "StopPower: " + ((Goalkeeper)selectedPlayer).getStopPower()+ "\nPenaltyStopPower: " + ((Goalkeeper)selectedPlayer).getPenaltyStopPower());
//        }
//        else
//            System.out.println("No player selected");
    }
}
