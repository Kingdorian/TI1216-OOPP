/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;

/**
 * This interface has to be implemented by all view controllers (except for
 * popups), so they can be cast to this interface in the main class and accessed
 * the same way.
 *
 * @author Faris
 */
public interface ViewControllerInterface {

    /**
     * Prototype of the method setPopupStage, which assigns instance of the Main
     * to the view controller, so the view controller can reference the Main
     * class
     *
     * @param mainController the Main of the application
     */
    public void setMainController(Main mainController);
}
