package zeiterfassungssystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Controller {
    @FXML
    Pane rootPane, mitAnlegen, mitBearbeiten, mitLoeschen;

    @FXML
    Label messageAnlegen, messageBearbeiten, messageLoeschen;

    public void initialize(){
        rootPane.getChildren().clear();
    }

    public void progBeenden(){
        System.exit(0);
    }

    public void mitarbeiterAnlegen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitAnlegen);
        messageAnlegen.setText("anlegen");
    }

    public void mitarbeiterBearbeiten(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitBearbeiten);
        messageBearbeiten.setText("bearbeiten");
    }

    public void mitarbeiterLoeschen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitLoeschen);
        messageLoeschen.setText("loeschen");
    }
}
