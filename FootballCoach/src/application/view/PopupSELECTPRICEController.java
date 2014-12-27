/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.model.Players;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextField;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author faris
 */
public class PopupSELECTPRICEController implements PopupControllerInterface {
    
    private boolean isOkClicked = false;
    private static PopupControl popupControl;
    private int price;

    @FXML
    private Label playerName;
    @FXML
    private Label estimatedPrice;
    @FXML
    private TextField priceText;

    /**
     * Sets the stage of this dialog.
     *
     * @param main
     * @param popupStage
     */
    @Override
    public void setPopupStage(PopupControl popupControl) {
        this.popupControl = popupControl;
    }

    @Override
    public boolean isOkClicked() {
        return isOkClicked;
    }

    @FXML
    private void initialize() {
        isOkClicked = false;
        Players selectedPlayer = Main.getSelectedPlayer();
        playerName.setText(selectedPlayer.getName() + " " + selectedPlayer.getSurName());
        estimatedPrice.setText(formatPrice(selectedPlayer.getPrice()));
        priceText.setText(Integer.toString(selectedPlayer.getPrice()));
    }
    
    /**
     * Format a large number to make it more readable
     * @param number    the number to format
     * @return          a string containing the formatted number
     */
    private String formatPrice(int number){
        String estimatedPriceString = Integer.toString(number);
        String formattedEstimatedPrice = "";
        int j =0;
        for(int i=estimatedPriceString.length()-1; i>=0; i--, j++){
            if(j%3 == 0 && j != 0)
                formattedEstimatedPrice = "," + formattedEstimatedPrice;
            formattedEstimatedPrice = estimatedPriceString.charAt(i) + formattedEstimatedPrice;
        }
        return "$ " + formattedEstimatedPrice;
    }

    @FXML
    private void buttonOK() {
        if(new Scanner(priceText.getText().trim()).hasNextInt()){
            price = Integer.parseInt(priceText.getText().trim());
            isOkClicked = true;
            
            // put player on the market
            Main.getCompetition().getMarket().addPlayer(Main.getSelectedPlayer(), price);
            
            popupControl.hide();
        } else {
            Dialogs.create()
                            .title("Invalid Price!")
                            .masthead("The price is invalid.")
                            .message("Please insert a valid price in the price text field.")
                            .owner(Main.getOldPopup())
                            .showWarning();
        }
    }

    @FXML
    private void buttonCancel() {
        popupControl.hide();
    }
    
    public int getPrice(){
        return price;
    }
}
