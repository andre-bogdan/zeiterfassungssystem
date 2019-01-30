package zeiterfassungssystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.lang.reflect.Array;

import static javafx.scene.input.KeyCode.O;

public class Controller {
    //Variablen
    Datenbank db = new Datenbank();
    @FXML
    Pane rootPane, einstellungen, mitAnlegen, mitBearbeiten, mitLoeschen, pswErneuern, zeitenErfassen;
    @FXML
    Label messageEinstellungen, messageAnlegen, messageBearbeiten, messageLoeschen, messagePswErneuern, messageZeitenErfassen;
    @FXML
    TextField dbHost, dbPort, dbName, dbUsername, dbPasswort, mailSmtp, mailUser, mailPasswort, mailPort, mailAbsName, mailAbsEmail;
    @FXML
    ComboBox bundeslaender;

    ObservableList<String> laender = FXCollections.observableArrayList("Baden-Württemberg","Bayern","Berlin","Brandenburg","Bremen","Hamburg","Hessen","Mecklenburg-Vorpommern","Niedersachsen","Nordrhein-Westfalen","Rheinland-Pfalz","Saarland,Sachsen","Sachsen-Anhalt","Schleswig-Holstein","Thüringen");

    //Initialisierung der Oberflaeche
    public void initialize(){
        rootPane.getChildren().clear();
    }

    //Datei
    //------------------------------------------------------------------------------------------------------------------
    //Einstellungen
    public void einstellungen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(einstellungen);
        String[] daten = db.readIni();
        dbHost.setText(daten[0]);
        dbPort.setText(daten[1]);
        dbName.setText(daten[2]);
        dbUsername.setText(daten[3]);
        dbPasswort.setText(daten[4]);
        mailSmtp.setText(daten[5]);
        mailUser.setText(daten[6]);
        mailPasswort.setText(daten[7]);
        messageEinstellungen.setText("");
    }
    //Einstellungen speichern
    public void einstellungenSpeichern(){
        String[] daten = new String[8];
        daten[0] = dbHost.getText();
        daten[1] = dbPort.getText();
        daten[2] = dbName.getText();
        daten[3] = dbUsername.getText();
        daten[4] = dbPasswort.getText();
        daten[5] = mailSmtp.getText();
        daten[6] = mailUser.getText();
        daten[7] = mailPasswort.getText();

        try {
            for (int i = 0; i < daten.length; i++){
                if(daten[i] == null){
                    daten[i] = "";
                }
            }
            db.db_update(daten[0],daten[1],daten[2],daten[3],daten[4],daten[5],daten[6],daten[7]);
            messageEinstellungen.setText("Daten wurden gespeichert!");
        }catch(NullPointerException e){
            messageEinstellungen.setText("Es müssen alle Felder ausgefüllt sein!");
        }

    }
    //Beenden
    public void progBeenden(){
        System.exit(0);
    }

    //Bearbeiten
    //------------------------------------------------------------------------------------------------------------------
    //Mitarbeiter Anlegen
    public void mitarbeiterAnlegen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitAnlegen);
        bundeslaender.setItems(laender);
        messageAnlegen.setText("");
    }
    //Mitarbeiter Bearbeiten
    public void mitarbeiterBearbeiten(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitBearbeiten);
        bundeslaender.setItems(laender);
        messageBearbeiten.setText("");
    }
    //Mitarbeiter Loeschen
    public void mitarbeiterLoeschen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitLoeschen);
        messageLoeschen.setText("");
    }
    //Passwort erneuern
    public void passwortErneuern(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(pswErneuern);
        messagePswErneuern.setText("");
    }
    //Zeiten manuell erfassen
    public void zeitenErfassen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(zeitenErfassen);
        messageZeitenErfassen.setText("");
    }
    //Zeiten erfassen speichern
    public void zeitenErfassenSpeichern(){

        messageZeitenErfassen.setText("Eingaben gespeichert!");
    }

    //Auswerten
    //------------------------------------------------------------------------------------------------------------------

    //Hilfe
    //------------------------------------------------------------------------------------------------------------------
}
