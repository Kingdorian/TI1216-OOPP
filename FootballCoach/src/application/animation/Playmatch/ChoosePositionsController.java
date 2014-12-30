/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.Playmatch;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author faris
 */
public class ChoosePositionsController {

    @FXML
    private Circle circle;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {

        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                circle.setCenterX(event.getX());
                circle.setCenterY(event.getY());
            }
        });
    }

    @FXML
    private void draggingCircle() {
        //circle.setCenterX(Cursor.);

    }

}
