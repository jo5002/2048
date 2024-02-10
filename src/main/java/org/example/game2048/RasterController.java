package org.example.game2048;

import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class RasterController implements Initializable
{
    @FXML
    public Label lbl1;
    @FXML
    public Label lbl2;
    @FXML
    public Label lbl3;
    @FXML
    public Label lbl4;
    @FXML
    public Label lbl5;
    @FXML
    public Label lbl6;
    @FXML
    public Label lbl7;
    @FXML
    public Label lbl8;
    @FXML
    public Label lbl9;
    @FXML
    public Label lbl10;
    @FXML
    public Label lbl11;
    @FXML
    public Label lbl12;
    @FXML
    public Label lbl13;
    @FXML
    public Label lbl14;
    @FXML
    public Label lbl15;
    @FXML
    public Label lbl16;
    @FXML
    public GridPane gp;

    Farbmodus fm = new Farbmodus();

    static Label[] lbl = new Label[16];
    int[] sp0 = new int[4];
    int[] sp1 = new int[4];
    int[] sp2 = new int[4];
    int[] sp3 = new int[4];
    int summand = 0;
    int[] anfangszahlen = new int[4];
    int[] zahlen = new int[16];
    int[] zahlenKopie = new int[16];
    int anzahl = 0;
    int[] spalteOhneLuecken = new int[4];
    int zaehler = 0;
    int[] leereFelder = null;
    int leereFelderZaehler = 0;
    Random rd = new Random();
    
    boolean alertAngezeigt = false;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        fuelleDenArray();
        erstelleZweiZufaelligeFelder();

    }

    public void fuelleDenArray() {
        lbl[0] = lbl1;
        lbl[1] = lbl2;
        lbl[2] = lbl3;
        lbl[3] = lbl4;
        lbl[4] = lbl5;
        lbl[5] = lbl6;
        lbl[6] = lbl7;
        lbl[7] = lbl8;
        lbl[8] = lbl9;
        lbl[9] = lbl10;
        lbl[10] = lbl11;
        lbl[11] = lbl12;
        lbl[12] = lbl13;
        lbl[13] = lbl14;
        lbl[14] = lbl15;
        lbl[15] = lbl16;
    }
    
    public void mache(KeyCode kc)
    {
        zahlenSichern();
        teileInSpalten(kc);
        schiebe();
        setzeDieSpaltenZusammen();
        syncronisiereDieLabels();
        System.out.println(Arrays.toString(zahlen));
        System.out.println(Arrays.toString(zahlenKopie));
        System.out.println("____________________________________________________________");
        
        erstelleEinNeuesLabel();
    }

    public void erstelleZweiZufaelligeFelder() 
    {

        int zufall1 = rd.nextInt(16);
        int zufall2 = rd.nextInt(16);
        while (zufall1 == zufall2) 
        {
            zufall2 = rd.nextInt(16);
        }

        // 1.
        int wertErmittelung = rd.nextInt(10);
        int wert;

        if (wertErmittelung == 0) {
            wert = 4;
        } else {
            wert = 2;
        }

        lbl[zufall1].setText(String.valueOf(wert));
        fm.syncronisiereLabel(lbl[zufall1]);

        //2
        wertErmittelung = rd.nextInt(10);

        if (wertErmittelung == 0) {
            wert = 4;
        } else {
            wert = 2;
        }

        lbl[zufall2].setText(String.valueOf(wert));
        fm.syncronisiereLabel(lbl[zufall2]);

    }

    public void getInteger() {
        for (int i = 0; i < 16; i++) {
            if (lbl[i].getText().equals("")) {
                zahlen[i] = 0;
            } else {
                zahlen[i] = Integer.parseInt(lbl[i].getText());
            }
        }
    }

    public void teileInSpalten(KeyCode kc)
    {

        if (kc == KeyCode.UP) {
            anfangszahlen[0] = 0;
            anfangszahlen[1] = 1;
            anfangszahlen[2] = 2;
            anfangszahlen[3] = 3;
            summand = 4;
        }

        if (kc == KeyCode.DOWN) {
            anfangszahlen[0] = 12;
            anfangszahlen[1] = 13;
            anfangszahlen[2] = 14;
            anfangszahlen[3] = 15;
            summand = -4;
        }

        if (kc == KeyCode.LEFT) {
            anfangszahlen[0] = 0;
            anfangszahlen[1] = 4;
            anfangszahlen[2] = 8;
            anfangszahlen[3] = 12;
            summand = 1;
        }

        if (kc == KeyCode.RIGHT) {
            anfangszahlen[0] = 3;
            anfangszahlen[1] = 7;
            anfangszahlen[2] = 11;
            anfangszahlen[3] = 15;
            summand = -1;
        }

        for (int i = 0; i < 4; i++) {
            sp0[i] = zahlen[anfangszahlen[0] + summand * i];

        }

        for (int i = 0; i < 4; i++) {
            sp1[i] = zahlen[anfangszahlen[1] + summand * i];
        }

        for (int i = 0; i < 4; i++) {
            sp2[i] = zahlen[anfangszahlen[2] + summand * i];
        }

        for (int i = 0; i < 4; i++) {
            sp3[i] = zahlen[anfangszahlen[3] + summand * i];
        }
    }
    
    public void zahlenSichern()
    {
        getInteger();
        for (int i = 0; i < 16; i++) 
        {
            zahlenKopie[i] = zahlen[i];
        }
    }
    
    public void syncronisiereDieLabels()
    {
        //Anzeigen
        for (int i = 0; i < 16; i++)
        {
            lbl[i].setText(String.valueOf(zahlen[i]));
            fm.syncronisiereLabel(lbl[i]);
        }
    }
    
    public void setzeDieSpaltenZusammen()
    {
        //Spalten zu zahlen[]
        for (int i = 0; i < 4; i++) {
            zahlen[anfangszahlen[0] + summand * i] = sp0[i];
        }

        for (int i = 0; i < 4; i++) {
            zahlen[anfangszahlen[1] + summand * i] = sp1[i];
        }

        for (int i = 0; i < 4; i++) {
            zahlen[anfangszahlen[2] + summand * i] = sp2[i];
        }

        for (int i = 0; i < 4; i++) {
            zahlen[anfangszahlen[3] + summand * i] = sp3[i];
        }
    }
    
    public void schiebe()
    {
        schließeLuecken(sp0);
        schließeLuecken(sp1);
        schließeLuecken(sp2);
        schließeLuecken(sp3);

        addiere(sp0);
        addiere(sp1);
        addiere(sp2);
        addiere(sp3);

        schließeLuecken(sp0);
        schließeLuecken(sp1);
        schließeLuecken(sp2);
        schließeLuecken(sp3);
    }
    
    public void abbrechen() 
    {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Verloren!");
        a.setHeaderText("Herzlichen Glückwunsch! Sie haben verloren!");
        a.showAndWait();

        new Main().reagiereNichtAufEingaben();
        return;
    }
    
    public boolean geaendert()
    {
        boolean geaendert = false;
        
        for (int i = 0; i < 16; i++) 
        {
            if(zahlen[i] != zahlenKopie[i])
            {
                geaendert = true;
            }
        }
        
        return geaendert;
    }
    
    public void erstelleEinNeuesLabel()
    {
        getInteger();
        leereFelderZaehler = 0;
        leereFelder = null;
        
        for (int i = 0; i < 16; i++) 
        {
            if(zahlen[i] == 0)
            {
                leereFelderZaehler++;
            }
        }
        
        if (leereFelderZaehler == 0)
        {
            abbrechen();
        }
        else if(hatDerSpielerGewonnen())
        {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Gewonnen! Als ob du das geschafft hast?!");
            a.setTitle("Gewonnen");
            a.show();
        }
        else
        {
            leereFelder = new int[leereFelderZaehler];
            leereFelderZaehler = 0;
            for (int i = 0; i < 16; i++) {
                if (zahlen[i] == 0) {
                    leereFelder[leereFelderZaehler] = i;
                    leereFelderZaehler++;
                }
            }

            int i = rd.nextInt(leereFelder.length);

            if (rd.nextInt(10) == 0) 
            {
                lbl[leereFelder[i]].setText("4");
            }
            else 
            {
                lbl[leereFelder[i]].setText("2");
            }
                    
            fm.syncronisiereLabel(lbl[leereFelder[i]]);
        }

    }
    
    public boolean hatDerSpielerGewonnen()
    {
        boolean gewonnen = false;
        
        for(int i = 0; i < 16; i++)
        {
            if(zahlen[i] > 2047)
            {
                gewonnen = true;
            }
        }
        
        return gewonnen;
    }

    public void addiere(int[] sp) 
    {
        if (sp[0] == sp[1]) 
        {
            sp[0] = 2 * sp[0];
            sp[1] = 0;
            
            //erst 4,3 dann 1,2
            if (sp[2] == sp[3]) 
            {
                sp[2] = 2 * sp[2];
                sp[3] = 0;
            }
        }
        else
        {
            //2,3 dann 3.4
            if (sp[1] == sp[2]) 
            {
                sp[1] = 2 * sp[1];
                sp[2] = 0;
            }
            else if (sp[2] == sp[3]) 
            {
                sp[2] = 2 * sp[2];
                sp[3] = 0;
            }
        }


    }

    public void schließeLuecken(int[] sp) {
        anzahl = 0;

        for (int i = 0; i < spalteOhneLuecken.length; i++) {
            spalteOhneLuecken[i] = 0;
        }

        for (int i = 0; i < sp.length; i++) {
            if (sp[i] != 0) {
                spalteOhneLuecken[anzahl] = sp[i];
                anzahl++;
            }
        }

        for (int i = 0; i < 4; i++) {
            sp[i] = spalteOhneLuecken[i];
        }
    }

    /**
     * Gibt einen String als Integer aus.
     */
    public int valueOf(String s) {
        if (s.equals("")) {
            return 0;
        } else {
            return Integer.valueOf(s);
        }
    }

    /**
     * Setzt den Text des Label auf den angegeben Integer, wenn letzterer nicht
     * gliech null ist.
     */
    public void setText(Label lbl, Integer i) {
        if (i != 0) {
            lbl.setText(String.valueOf(i));
        }
    }
}
