/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.Playmatch;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author faris
 */
public class ChoosePositionsController {
private static final Circle playerCircle[] = new Circle[10];

    /**
     * this class implements EventHandler and can 
     * be added to a circle to make it dragable when
     * the cursor moves over the circle (because we
     * can't add the event handlers directly when 
     * loading, since the circles won't be referrenced
     * by the fxml file yet)
     */
    private static class CircleDragHandler implements EventHandler{
        private final Circle c;
        
        /**
         * Constructor
         * @param c Circle to add event handler to
         */
        public CircleDragHandler(final Circle c){
            this.c = c;
        }
        
        /**
         * Change the x and y coordinate of the circle when the circle is dragged
         * and change cursors look when mouse events occur
         * @param event and event (nothing will happen if this isn't a mouse event)
         */
        @Override
        public void handle(Event event){
            //make the circle dragable when any mouse event occurs
            c.getScene().getRoot().setCursor(Cursor.HAND);
            makeDragable(c);
            
            //remove this event handler, so it will only get triggered once
            c.removeEventHandler(MouseEvent.ANY, this);
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
    }    
    

    /**
     * This method makes the group of the circle dragable.
     * This method is based on the makeDragable method from 
     * this project:
     * https://gist.github.com/jewelsea/3388637
     *
     * @param circle  the circle who's group should be made dragable
     */
    public static void makeDragable(Circle circle){

        // class to store relative x and y coordinates
        class Delta {
            double x, y;
        }
        
        final Delta dragDelta = new Delta();
        Node node = circle.getScene().getRoot();
        
        // when clicking set the position of the group relative to the mouse
        // and change the mouse cursor to 'move'
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = circle.getParent().getLayoutX() - mouseEvent.getScreenX();
                dragDelta.y = circle.getParent().getLayoutY() - mouseEvent.getScreenY();
                node.setCursor(Cursor.MOVE);
                
                // reset all circles colors
                for(int i=0; i<10; i++)
                    playerCircle[i].setFill(Color.BLUE);
                
                // set this circles color to a different color, so it seems
                // as if it is selected
                circle.setFill(Color.AQUA);
            }
        });
        
        // when releasing the mouse button, change the cursor to a hand
        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setCursor(Cursor.HAND);
            }
        });
        
        // move the group when dragging with the mouse (relative to where the mouse is)
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                circle.getParent().setLayoutX(mouseEvent.getScreenX() + dragDelta.x);
                circle.getParent().setLayoutY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });
        
        // change cursor to a hand when entering the group
        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    node.setCursor(Cursor.HAND);
                }
            }
        });
        
        // change to default cursor when leaving the group
        circle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    node.setCursor(Cursor.DEFAULT);
                }
            }
        });
    }
    
    
    /**
     * Add the player circles to the scene
     * @param anchorPane the anchor pane containing the scene
     */
    public static void drawCircles(AnchorPane anchorPane){
        
        // create the circles
        playerCircle[0] = new Circle(330, 160, 13, Color.BLUE);
        playerCircle[1] = new Circle(288, 290, 13, Color.BLUE);
        playerCircle[2] = new Circle(288, 467, 13, Color.BLUE);
        playerCircle[3] = new Circle(330, 605, 13, Color.BLUE);
        playerCircle[4] = new Circle(550, 250, 13, Color.BLUE);
        playerCircle[5] = new Circle(450, 385, 13, Color.BLUE);
        playerCircle[6] = new Circle(550, 513, 13, Color.BLUE);
        playerCircle[7] = new Circle(713, 259, 13, Color.BLUE);
        playerCircle[8] = new Circle(785, 385, 13, Color.BLUE);
        playerCircle[9] = new Circle(719, 494, 13, Color.BLUE);

        // add players name to the circles + set a lighting effect
        // and add the circle+text to the scene
        for(int i=0; i<10; i++){
            // add player name
            Text text = new Text();
            text.setText("Player " + i);
            text.setX(playerCircle[i].getCenterX() - 25);
            text.setY(playerCircle[i].getCenterY() - 18);
            text.setFill(Color.WHITE);
            text.setFont(new Font("Verdana bold", 14));
            
            // add lighting effect
            playerCircle[i].setEffect(new Lighting());
            
            // group circle and name togheter and add them to the scene
            Group group = new Group(playerCircle[i], text);
            anchorPane.getChildren().add(group);
        }

        // make all circles dragable (indirectly via an event handler)
        playerCircle[0].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[0]));
        playerCircle[1].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[1]));
        playerCircle[2].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[2]));
        playerCircle[3].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[3]));
        playerCircle[4].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[4]));
        playerCircle[5].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[5]));
        playerCircle[6].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[6]));
        playerCircle[7].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[7]));
        playerCircle[8].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[8]));
        playerCircle[9].addEventHandler(MouseEvent.ANY, new CircleDragHandler(playerCircle[9]));
    }
}
