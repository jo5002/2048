/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.game2048;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GUIController implements Initializable 
{
    @FXML
    private HBox hbox;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        GridPane gp;
        VBox vb;
        try 
        {
            hbox.setPadding(new Insets(15.0, 15.0, 15.0, 15.0));
            gp = FXMLLoader.load(getClass().getResource("Raster.fxml"));
            hbox.getChildren().add(gp);
        } 
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        }
    }    
    
}
