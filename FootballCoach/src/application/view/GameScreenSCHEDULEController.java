/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.model.Match;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 * This is the controller class of the SCHEDULE screen of the main view of the
 * game screen.
 *
 * @author Faris
 * @author Jochem
 */
public class GameScreenSCHEDULEController implements ViewControllerInterface {

    private static Main mainController;

    @FXML
    private ComboBox selectRoundBox;
    @FXML
    private TableView<Match> scheduleTable;
    @FXML
    private TableColumn<Match, Number> columnHomeRank;
    @FXML
    private TableColumn<Match, String> columnHomeName;
    @FXML
    private TableColumn<Match, String> columnVS;
    @FXML
    private TableColumn<Match, Number> columnAwayRank;
    @FXML
    private TableColumn<Match, String> columnAwayName;
    @FXML
    private TableColumn<Match, String> columnResult;

    /**
     * This is an event handler class definition, which will be triggered when
     * an items in the dropdown list is selected.
     */
    private final EventHandler onSelected = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            // Add data to the table
            scheduleTable.setItems(FXCollections.observableArrayList(Main.getCompetition().getRound(selectRoundBox.getSelectionModel().getSelectedIndex())));
        }
    };

    /**
     * This code is executed when the view is loaded. It sets the main texts of
     * this view.
     */
    @FXML
    private void initialize() {
        //set round selection box options
        ArrayList<String> roundList = new ArrayList<>();
        for (int i = 1; i < 35; i++) {
            roundList.add("Round " + i);
        }
        selectRoundBox.setItems(FXCollections.observableArrayList(roundList));

        //set selection box onselect listener
        selectRoundBox.addEventHandler(ActionEvent.ACTION, onSelected);

        //initialize the table and columns
        columnHomeRank.setCellValueFactory(cellData -> new SimpleIntegerProperty(Main.getCompetition().getRank(cellData.getValue().getHomeTeam())));
        columnHomeName.setCellValueFactory(cellData -> {
            String teamname = cellData.getValue().getHomeTeam().getName();
            if (teamname.equals(Main.getChosenTeamName())) {
                changeOwnTeamsBackground();
            }
            return new SimpleStringProperty(teamname);
        });
        columnVS.setCellValueFactory(cellData -> new SimpleStringProperty(" - "));
        columnAwayRank.setCellValueFactory(cellData -> new SimpleIntegerProperty(Main.getCompetition().getRank(cellData.getValue().getVisitorTeam())));
        columnAwayName.setCellValueFactory(cellData -> {
            String teamname = cellData.getValue().getVisitorTeam().getName();
            if (teamname.equals(Main.getChosenTeamName())) {
                changeOwnTeamsBackground();
            }
            return new SimpleStringProperty(teamname);
        });
        columnResult.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPointsHomeTeam() + " - " + cellData.getValue().getPointsVisitorTeam()));

        int currentRound = Main.getCompetition().getRound() - 1;
        if (currentRound >= 0) {
            selectRoundBox.getSelectionModel().select(currentRound);
        }
    }

    /**
     * This method applies a different background collor to your own team so you
     * can easilly distinguish it from the other teams
     */
    private void changeOwnTeamsBackground() {
        // Apply a different background color to your own team
        int i = 0;
        for (Node n : scheduleTable.lookupAll("TableRow")) {
            if (n instanceof TableRow) {
                TableRow row = (TableRow) n;
                if (scheduleTable.getItems().get(i).getHomeTeam().getName().equals(Main.getChosenTeamName()) || scheduleTable.getItems().get(i).getVisitorTeam().getName().equals(Main.getChosenTeamName())) {
                    row.setStyle("-fx-background-color: ladder(-fx-backgroundcolor, lightcoral 49%, darkred 51%);");
                } else {
                    row.setStyle(null);
                }
                i++;
                if (i == scheduleTable.getItems().size()) {
                    break;
                }
            }
        }
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

}
