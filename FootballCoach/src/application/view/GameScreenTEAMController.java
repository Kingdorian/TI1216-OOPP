/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Faris
 * @author Jochem
 */
public class GameScreenTEAMController implements ViewControllerInterface {
	
	@FXML private TableView table;
	@FXML private TableColumn columnNo;
	@FXML private TableColumn columnName;
	@FXML private TableColumn columnDefense;
	@FXML private TableColumn columnOffense;
	@FXML private TableColumn columnStamina;
	
	
    private static Main mainController;
 
    
    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize(){
        Player test = new Player("test", "test", 10, null, 0, null, 10, 20, 30);
       
        
        
    }
   
    
    @Override
    public void setMainController(Main mainController){
        GameScreenTEAMController.mainController = mainController;
    }

}
