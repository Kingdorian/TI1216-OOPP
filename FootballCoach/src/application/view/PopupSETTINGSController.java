/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.PopupControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This is the controller class of the SETTINGS popup
 *
 * @author Faris
 */
public class PopupSETTINGSController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static PopupControl popupControl;
    private BorderPane rootLayout;
    private Stage primaryStage;
    private String colorBeforeChanges;
    private Color oldBGColor;
    private Color oldHeaderColor;
    private Color oldTextColor;

    @FXML
    ColorPicker headerColor;
    @FXML
    ColorPicker backgroundColor;
    @FXML
    ColorPicker textColor;
    @FXML
    CheckBox fullScreenCheckBox;

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
        // get the stage and border pane
        rootLayout = Main.getRootLayout();
        primaryStage = Main.getPrimaryStage();

        // remember the style before any changes are made.
        colorBeforeChanges = Main.getColorCssStyle();

        // set the checkbox to the current value (if full screen is on or off)
        if (primaryStage.isFullScreen()) {
            fullScreenCheckBox.setSelected(true);
        } else {
            fullScreenCheckBox.setSelected(false);
        }

        getColorsFromCSS();

        // set default colors
        if (oldBGColor == null || oldHeaderColor == null || oldTextColor == null) {
            headerColor.setValue(new Color(16.0 / 256.0, 16.0 / 256.0, 16.0 / 256.0, .7));
            backgroundColor.setValue(new Color(147.0 / 256.0, 147.0 / 256.0, 147.0 / 256.0, 0.8));
            textColor.setValue(new Color(247.0 / 256.0, 247.0 / 256.0, 247.0 / 256.0, 1.0));
        } else {
            headerColor.setValue(oldHeaderColor);
            backgroundColor.setValue(oldBGColor);
            textColor.setValue(oldTextColor);
        }

        // add event handlers for when a different color gets selected to change the colors
        backgroundColor.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
                setColor();
            }
        });
        textColor.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
                setColor();
            }
        });
        headerColor.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
                setColor();
            }
        });
        setColor();
        isOkClicked = false;
    }

    /**
     * get the colors from the line of css in the Main and convert them to color
     * values
     */
    private void getColorsFromCSS() {
        String colorCSS = Main.getColorCssStyle();
        if (!(colorCSS.contains("-fx-backgroundcolor:") && colorCSS.contains("-fx-textcolor:") && colorCSS.contains("-fx-basecolor:"))) {
            return;
        }

        ArrayList<Double> backgroundColor = null;
        ArrayList<Double> textColor = null;
        ArrayList<Double> baseColor = null;

        Scanner sc = new Scanner(colorCSS);
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();
            if (line.contains("-fx-backgroundcolor:")) {
                backgroundColor = getColorValues(line);
            } else if (line.contains("-fx-textcolor:")) {
                textColor = getColorValues(line);
            } else if (line.contains("-fx-basecolor:")) {
                baseColor = getColorValues(line);
            }
        }

        if (backgroundColor != null && textColor != null && baseColor != null && backgroundColor.size() == 4 && textColor.size() == 3 && baseColor.size() == 4) {
            oldBGColor = new Color(backgroundColor.get(0) / 256, backgroundColor.get(1) / 256, backgroundColor.get(2) / 256, backgroundColor.get(3));
            oldHeaderColor = new Color(baseColor.get(0) / 256, baseColor.get(1) / 256, baseColor.get(2) / 256, baseColor.get(3));
            oldTextColor = new Color(textColor.get(0) / 256, textColor.get(1) / 256, textColor.get(2) / 256, 1.0);
        }
    }

    /**
     * Convert a string with some double values split by commas to an arraylist
     * of those doubles
     *
     * @param cssLine the line of css code containing the doubles
     * @return the doubles from the css line
     */
    private ArrayList<Double> getColorValues(String cssLine) {
        // replace all non-numeric characters
        cssLine = cssLine.replaceAll("[^0-9,.]", "");
        Scanner sc = new Scanner(cssLine);

        // use comma as delimiter
        sc.useDelimiter(",");

        //set locale to US to recognize double values with a "."
        sc.useLocale(Locale.US);

        ArrayList<Double> result = new ArrayList<>();

        while (sc.hasNextDouble()) {
            result.add(sc.nextDouble());
        }

        return result;
    }

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will close the popup
     */
    @FXML
    private void buttonOK() {
        if (primaryStage.isFullScreen() && !fullScreenCheckBox.isSelected()) {
            primaryStage.setFullScreen(false);
        } else if (!primaryStage.isFullScreen() && fullScreenCheckBox.isSelected()) {
            primaryStage.setFullScreen(true);
        }

        isOkClicked = true;
        popupControl.hide();
    }

    /**
     * Set the colors of all view elements to the selected colors in the color
     * pickers
     */
    private void setColor() {
        // get selected background colors (r, b, g, a)
        String background = Double.toString(backgroundColor.getValue().getRed() * 256.0);
        background += ", " + Double.toString(backgroundColor.getValue().getGreen() * 256.0);
        background += ", " + Double.toString(backgroundColor.getValue().getBlue() * 256.0);

        // get selected text colors (r, b, g, a)
        String text = Double.toString(textColor.getValue().getRed() * 256.0);
        text += ", " + Double.toString(textColor.getValue().getGreen() * 256.0);
        text += ", " + Double.toString(textColor.getValue().getBlue() * 256.0);

        // get selected header colors (r, b, g, a)
        String header = Double.toString(headerColor.getValue().getRed() * 256.0);
        header += ", " + Double.toString(headerColor.getValue().getGreen() * 256.0);
        header += ", " + Double.toString(headerColor.getValue().getBlue() * 256.0);

        // set header color
        Main.setColorCssStyle("    -fx-basecolor: rgba(" + header + ", 0.7); \n"
                + "    -fx-backgroundcolor: rgba(" + background + ", 0.8); \n"
                + "    -fx-textcolor: rgb(" + text + "); \n"
                + "    -fx-shadowcolor: rgba(0,0,0,.5); \n"
                + "    -fx-accent: rgba(" + background + ", 0.8); \n");
        rootLayout.setStyle(Main.getSizeCssStyle() + Main.getColorCssStyle());
    }

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will close the popup
     */
    @FXML
    private void buttonCancel() {
        // reset colors and close stage
        rootLayout.setStyle(Main.getSizeCssStyle() + colorBeforeChanges);
        Main.setColorCssStyle(colorBeforeChanges);
        popupControl.hide();
    }

    /**
     * This method is triggered when the reset background button is clicked
     * (event handler assigned in the .fxml) and it will reset the background
     * color to the default value
     */
    @FXML
    private void resetBackgrounButton() {
        backgroundColor.setValue(new Color(147.0 / 256.0, 147.0 / 256.0, 147.0 / 256.0, .8));
        setColor();
    }

    /**
     * This method is triggered when the reset text button is clicked (event
     * handler assigned in the .fxml) and it will reset the text color to the
     * default value
     */
    @FXML
    private void resetTextButton() {
        textColor.setValue(new Color(247.0 / 256.0, 247.0 / 256.0, 247.0 / 256.0, 1.0));
        setColor();
    }

    /**
     * This method is triggered when the reset header button is clicked (event
     * handler assigned in the .fxml) and it will reset the header color to the
     * default value
     */
    @FXML
    private void resetHeaderButton() {
        headerColor.setValue(new Color(16.0 / 256.0, 16.0 / 256.0, 16.0 / 256.0, .7));
        setColor();
    }

}
