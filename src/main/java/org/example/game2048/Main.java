package org.example.game2048;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 *
 * @author josef
 */
public class Main extends Application 
{
     Scene scene = null;
     
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        
        RasterController rc = new RasterController();
        
        scene = new Scene(root);
        
        scene.setOnKeyPressed(evt -> 
        {
            KeyCode kc = evt.getCode();
            
            if(kc == KeyCode.UP)
            {
                rc.mache(kc);
            }
            
            if (kc == KeyCode.DOWN) 
            {
                rc.mache(kc);

            }
            
            if (kc == KeyCode.LEFT) 
            {
                rc.mache(kc);

            }
            
            if (kc == KeyCode.RIGHT) 
            {
                rc.mache(kc);

            }
        });
        
        stage.setScene(scene);
        stage.show();
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void reagiereNichtAufEingaben()
    {
        scene.setOnKeyPressed(null);
    }
}
