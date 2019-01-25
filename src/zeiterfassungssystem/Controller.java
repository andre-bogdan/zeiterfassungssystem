package zeiterfassungssystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Controller {
    //Variablen
    @FXML
    Pane rootPane, mitAnlegen, mitBearbeiten, mitLoeschen;

    @FXML
    Label messageAnlegen, messageBearbeiten, messageLoeschen;

    //Initialisierung der Oberflaeche
    public void initialize(){
        rootPane.getChildren().clear();
    }

    //Programm beenden
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
