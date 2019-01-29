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
    Pane rootPane, einstellungen, mitAnlegen, mitBearbeiten, mitLoeschen, pswErneuern;
    @FXML
    Label messageEinstellungen, messageAnlegen, messageBearbeiten, messageLoeschen, messagePswErneuern;
    @FXML
    TextField dbHost, dbPort, dbName, dbUsername, dbPasswort, mailSmtp, mailUser, mailPasswort;
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

        db.db_update(host,port,name,user,pass,smtp,mailuser,mailpass);
        messageEinstellungen.setText(host);
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

    //Auswerten
    //------------------------------------------------------------------------------------------------------------------

    //Hilfe
    //------------------------------------------------------------------------------------------------------------------
}
