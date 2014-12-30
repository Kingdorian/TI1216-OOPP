/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import javafx.scene.control.PopupControl;

/**
 * This interface has to be implemented by all popup controllers, so they can be
 * cast to this interface in the main class and accessed the same way.
 *
 * @author Faris
 */
public interface PopupControllerInterface {

    /**
     * Prototype of the method isOkClicker, which returns true if a popup window
     * is closed by clicking the OK button, or otherwise false
     *
     * @return boolean: if the ok button has been clicked
     */
    public boolean isOkClicked();

    /**
     * Prototype of the method setPopupStage, which assigns the PopupControl
     * (the 'Stage' of the popup) to the popup controller
     *
     * @param popupControl the PopupControl of the scene
     */
    public void setPopupStage(PopupControl popupControl);
}
