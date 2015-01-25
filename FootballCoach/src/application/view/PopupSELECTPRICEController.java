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

/**
 * This is the controller class of the SELECTPRICE popup
 *
 * @author Faris
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
     * Sets the stage (PopupControl) of this popup.
     *
     * @param popupControl the popups stage (PopupControl)
     */
    @Override
    public void setPopupStage(PopupControl popupControl) {
        this.popupControl = popupControl;
    }

    /**
     * Will return true if the OK button has been clicked, otherwise will return
     * false
     *
     * @return boolean: if the ok button has been clicked
     */
    @Override
    public boolean isOkClicked() {
        return isOkClicked;
    }

    /**
     * This code is executed when the view is loaded. It sets the main texts of
     * this popup.
     */
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

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will check if all input is correct and
     * close the popup
     */
    @FXML
    private void buttonOK() {
        if (new Scanner(priceText.getText().trim()).hasNextInt()) {
            price = Integer.parseInt(priceText.getText().trim());
            isOkClicked = true;

            // put player on the market
            Main.getCompetition().getMarket().addPlayer(Main.getSelectedPlayer(), price);

            popupControl.hide();
        } else {
            Main.createModal("Invalid Price!", "The price is invalid.", "Please insert a valid price in the price text field.");
        }
    }

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will close the popup
     */
    @FXML
    private void buttonCancel() {
        popupControl.hide();
    }

    /**
     * Gives the price entered int the price field
     *
     * @return the entered price
     */
    public int getPrice() {
        return price;
    }
}
