/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Faris
 */
public class PopupSETTINGSController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static Stage popupStage;
    private BorderPane rootLayout;
    private Stage primaryStage;
    private String styleBeforeChanges;
    
    @FXML
    ColorPicker headerColor;
    @FXML
    ColorPicker backgroundColor;
    @FXML
    ColorPicker textColor;
    @FXML
    CheckBox fullScreenCheckBox;

    /**
     * Sets the stage of this dialog.
     *
     * @param popupStage
     */
    @Override
    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
        
        // reset colors if the stage is closed in any other way than by clicking one of the buttons
        popupStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              System.out.println("Stage is closing");
              if(isOkClicked == false)
                  rootLayout.setStyle(styleBeforeChanges);
          }
      });    
    }

    @Override
    public boolean isOkClicked() {
        return isOkClicked;
    }

    @FXML
    private void initialize() {
        // get the stage and border pane
        rootLayout = Main.getRootLayout();
        primaryStage = Main.getPrimaryStage();
        
        // remember the style before any changes are made.
        styleBeforeChanges = rootLayout.getStyle();
        
        // set the checkbox to the current value (if full screen is on or off)
        if(primaryStage.isFullScreen())
            fullScreenCheckBox.setSelected(true);
        else
            fullScreenCheckBox.setSelected(false);

        // set default colors
        headerColor.setValue(new Color(16.0/256.0, 16.0/256.0, 16.0/256.0, .7));
        backgroundColor.setValue(new Color(147.0/256.0, 147.0/256.0, 147.0/256.0, 0.8));
        textColor.setValue(new Color(247.0/256.0, 247.0/256.0, 247.0/256.0, 1.0));
        
        // add event handlers for when a different color gets selected to change the colors
        backgroundColor.setOnAction(new EventHandler() {
            public void handle(Event t) {
                setColor();           
            }
        });
        textColor.setOnAction(new EventHandler() {
            public void handle(Event t) {
                setColor();           
            }
        });
        headerColor.setOnAction(new EventHandler() {
            public void handle(Event t) {
                setColor();           
            }
        });
        
        isOkClicked = false;
    }

    @FXML
    private void buttonOK() {
        if(primaryStage.isFullScreen() && !fullScreenCheckBox.isSelected())
            primaryStage.setFullScreen(false);
        else if(!primaryStage.isFullScreen() && fullScreenCheckBox.isSelected())
            primaryStage.setFullScreen(true);
        
        isOkClicked = true;
        popupStage.close();
    }
    
    private void setColor(){
        // get selected background colors (r, b, g, a)
        String background =  Double.toString(backgroundColor.getValue().getRed()*256.0);
        background += ", " + Double.toString(backgroundColor.getValue().getGreen()*256.0);
        background += ", " + Double.toString(backgroundColor.getValue().getBlue()*256.0);
        
        // get selected text colors (r, b, g, a)
        String text =  Double.toString(textColor.getValue().getRed()*256.0);
        text += ", " + Double.toString(textColor.getValue().getGreen()*256.0);
        text += ", " + Double.toString(textColor.getValue().getBlue()*256.0);
        
        // get selected header colors (r, b, g, a)
        String header =  Double.toString(headerColor.getValue().getRed()*256.0);
        header += ", " + Double.toString(headerColor.getValue().getGreen()*256.0);
        header += ", " + Double.toString(headerColor.getValue().getBlue()*256.0);
        
        // set header color
        rootLayout.setStyle("    -fx-base: rgba(" + header + ", 0.7); \n" +
                            "    -fx-background: rgba(" + background + ", 0.8); \n" +
                            "    -fx-textcolor: rgb(" + text + "); \n" +
                            "    -fx-shadowcolor: rgba(0,0,0,.5); \n" +
                            "    -fx-accent: rgba(" + background + ", 0.8); \n");
    }

    @FXML
    private void buttonCancel() {
        // reset colors and close stage
        rootLayout.setStyle(styleBeforeChanges);
        popupStage.close();
    }
    
    @FXML
    private void resetBackgrounButton(){
        backgroundColor.setValue(new Color(147.0/256.0, 147.0/256.0, 147.0/256.0,.8));
        setColor();
    }
    
    @FXML
    private void resetTextButton(){
        textColor.setValue(new Color(247.0/256.0, 247.0/256.0, 247.0/256.0, 1.0));
        setColor();
    }
    
    @FXML
    private void resetHeaderButton(){
        headerColor.setValue(new Color(16.0/256.0, 16.0/256.0, 16.0/256.0, .7));
        setColor();
    }

}
