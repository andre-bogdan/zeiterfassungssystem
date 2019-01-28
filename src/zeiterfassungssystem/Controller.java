package zeiterfassungssystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.lang.reflect.Array;

import static javafx.scene.input.KeyCode.O;

public class Controller {
    //Variablen
    @FXML
    Pane rootPane, einstellungen, mitAnlegen, mitBearbeiten, mitLoeschen, pswErneuern;
    @FXML
    Label messageEinstellungen, messageAnlegen, messageBearbeiten, messageLoeschen, messagePswErneuern;
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
        bundeslaender.setItems(laender);
        messageEinstellungen.setText("test");
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
        messageAnlegen.setText("");
    }
    //Mitarbeiter Bearbeiten
    public void mitarbeiterBearbeiten(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitBearbeiten);
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
