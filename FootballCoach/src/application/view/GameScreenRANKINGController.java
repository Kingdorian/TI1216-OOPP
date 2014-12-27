/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.model.Team;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 *
 * @author Jochem
 */
public class GameScreenRANKINGController implements ViewControllerInterface {

    private static Main mainController;

    @FXML
    private TableView<Team> rankingTable;
    @FXML
    private TableColumn<Team, Number> columnNo;
    @FXML
    private TableColumn<Team, String> columnName;
    @FXML
    private TableColumn<Team, Number> columnPoints;
    @FXML
    private TableColumn<Team, Number> columnWins;
    @FXML
    private TableColumn<Team, Number> columnDraws;
    @FXML
    private TableColumn<Team, Number> columnLosses;
    @FXML
    private TableColumn<Team, Number> columnGoalDiff;

    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize() {
        columnNo.setCellValueFactory(cellData -> new SimpleIntegerProperty(Main.getCompetition().getRank(cellData.getValue())));
        columnName.setCellValueFactory(cellData -> {
            String name = cellData.getValue().getName();
            if (name.equals(Main.getChosenTeamName())) {
                changeOwnTeamsBackground();
            }
            return new SimpleStringProperty(cellData.getValue().getName());
        });
        columnPoints.setCellValueFactory(cellData -> new SimpleIntegerProperty(Main.getCompetition().getPoints(cellData.getValue())[4]));
        columnWins.setCellValueFactory(cellData -> new SimpleIntegerProperty(Main.getCompetition().getPoints(cellData.getValue())[0]));
        columnDraws.setCellValueFactory(cellData -> new SimpleIntegerProperty(Main.getCompetition().getPoints(cellData.getValue())[1]));
        columnLosses.setCellValueFactory(cellData -> new SimpleIntegerProperty(Main.getCompetition().getPoints(cellData.getValue())[2]));
        columnGoalDiff.setCellValueFactory(cellData -> new SimpleIntegerProperty(Main.getCompetition().getPoints(cellData.getValue())[3]));

        rankingTable.setItems(FXCollections.observableArrayList(Main.getCompetition().getAllRanks()));
    }

    @Override
    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }

    private void changeOwnTeamsBackground() {
        // Apply a different background color to your own team
        int i = 0;
        for (Node n : rankingTable.lookupAll("TableRow")) {
            if (n instanceof TableRow) {
                TableRow row = (TableRow) n;
                if (rankingTable.getItems().get(i).getName().equals(Main.getChosenTeamName())) {
                    row.setStyle("-fx-background-color: ladder(-fx-backgroundcolor, lightcoral 49%, darkred 51%);");
                } else {
                    row.setStyle(null);
                }
                i++;
                if (i == rankingTable.getItems().size()) {
                    break;
                }
            }
        }
    }

}
