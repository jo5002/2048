package org.example.game2048;

import javafx.scene.control.Label;

public class Farbmodus 
{
    String[] farben = {"#9DFA07", "#9DFA07", "#27FA07", "#07FA68", "#07FAB1", "#077DFA", "#0748FA", "#4407FA", "#9107FA", "#F207FA", "#FA0754", "#FA0707", "#FA6407", "#FAC607", "#FAEA07", "#ffd700", "#ffd700"};

    
    public String getFarbe(int i)
    {
        return farben[i];
    }
    
    public void syncronisiereLabel(Label lbl)
    {      
        int i;
        String text = lbl.getText();
        if(text == "")
        {
            i = 0;
        }
        else
        {
            i = Integer.valueOf(lbl.getText());
        }
        
        if(i == 0)
        {
            lbl.setStyle("-fx-background-color:  #888888");
            lbl.setText("");
            return;
        }
        else
        {
            i = log2(i);
        }
        
        if (i == 7 || i == 8 || i == 6) 
        {
            lbl.setStyle("-fx-text-fill: #ffffff;" + " -fx-background-color: " + farben[i]);
        } 
        else
        {
            lbl.setStyle("-fx-background-color: " + farben[i]);
        }
    }
    
    public int log2(int i)
    {
        int exp = 0;
        int zahl = i;
        while(zahl != 2)
        {
            zahl = zahl/2;
            exp++;
        }
        
        return exp;
    }
    
}
