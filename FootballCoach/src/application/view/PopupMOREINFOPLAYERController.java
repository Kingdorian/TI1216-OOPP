/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.model.Player;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author faris
 */
public class PopupMOREINFOPLAYERController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static Stage popupStage;
    
    @FXML
    Text firstName;
    @FXML
    Text lastName;
    @FXML
    Text playerNumber;
    @FXML
    Text playerType;
    @FXML
    Text attackPower;
    @FXML
    Text defensePower;
    @FXML
    Text stamina;
    @FXML
    Text timeNotAvailable;
    @FXML
    Text kindOfCard;
    @FXML
    Text kindOfInjury;
    
    
    @Override
    public boolean isOkClicked() {
        return isOkClicked;
    }

    @Override
        public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }
        
    @FXML
    private void initialize(){
        Player selectedPlayer = (Player) Main.getCurrentlySelected();
        
        firstName.setText(selectedPlayer.getName());
        lastName.setText(selectedPlayer.getSurName());
        playerNumber.setText(Integer.toString(selectedPlayer.getNumber()));
        playerType.setText(selectedPlayer.getKind());
        attackPower.setText(Integer.toString(selectedPlayer.getAttack()));
        defensePower.setText(Integer.toString(selectedPlayer.getDefence()));
        stamina.setText(Integer.toString(selectedPlayer.getStamina()));
        timeNotAvailable.setText(selectedPlayer.getTimeNotAvailable() == 0 ? "None" : Integer.toString(selectedPlayer.getTimeNotAvailable()) + " days");
        kindOfCard.setText("Not implemented yet");
        kindOfInjury.setText(selectedPlayer.getReason().toString());
    }
    
    @FXML
    private void buttonOK() {
        isOkClicked = true;
        popupStage.close();
    }
}
