package zeiterfassungssystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import static javafx.scene.input.KeyCode.O;

public class Controller {
    //Variablen
    @FXML
    Pane rootPane, einstellungen, mitAnlegen, mitBearbeiten, mitLoeschen;
    @FXML
    Label messageEinstellungen, messageAnlegen, messageBearbeiten, messageLoeschen;
    @FXML
    ComboBox bundeslaender;

    ObservableList<String> laender = FXCollections.observableArrayList("Baden-Württemberg","Bayern","Berlin","Brandenburg","Bremen","Hamburg","Hessen","Mecklenburg-Vorpommern","Niedersachsen","Nordrhein-Westfalen","Rheinland-Pfalz","Saarland,Sachsen","Sachsen-Anhalt","Schleswig-Holstein","Thüringen");

    //Initialisierung der Oberflaeche
    public void initialize(){
        rootPane.getChildren().clear();
    }

    //Datei
    public void einstellungen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(einstellungen);
        bundeslaender.setItems(laender);
        messageEinstellungen.setText("test");
    }

    public void progBeenden(){
        System.exit(0);
    }

    //Bearbeiten
    public void mitarbeiterAnlegen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitAnlegen);
        messageAnlegen.setText("");
    }

    public void mitarbeiterBearbeiten(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitBearbeiten);
        messageBearbeiten.setText("");
    }

    public void mitarbeiterLoeschen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitLoeschen);
        messageLoeschen.setText("");
    }

    //Auswerten

    //Hilfe
}
