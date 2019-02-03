package zeiterfassungssystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;

import static com.sun.javafx.scene.control.skin.Utils.getResource;
import static javafx.scene.input.KeyCode.O;

public class Controller {
    //Variablen
    Datenbank db = new Datenbank();
    @FXML
    Pane rootPane, logoPane, einstellungen, mitAnlegen, mitBearbeiten, mitLoeschen, pswErneuern, zeitenErfassen;
    @FXML
    ImageView image;
    @FXML
    Label messageEinstellungen, messageAnlegen, messageBearbeiten, messageLoeschen, messagePswErneuern, messageZeitenErfassen;
    @FXML
    TextField dbHost, dbPort, dbName, dbUsername, dbPasswort, mailSmtp, mailUser, mailPasswort, mailPort, mailAbsName, mailAbsEmail,
            vName, nName, position, standort, email, telefon ;
    @FXML
    ComboBox bundeslaender;

    ObservableList<String> laender = FXCollections.observableArrayList("Baden-Württemberg","Bayern","Berlin","Brandenburg","Bremen","Hamburg","Hessen","Mecklenburg-Vorpommern","Niedersachsen","Nordrhein-Westfalen","Rheinland-Pfalz","Saarland,Sachsen","Sachsen-Anhalt","Schleswig-Holstein","Thüringen");

    //Initialisierung der Oberflaeche
    public void initialize(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(logoPane);
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
        mailPort.setText(daten[8]);
        mailAbsName.setText(daten[9]);
        mailAbsEmail.setText(daten[10]);
        messageEinstellungen.setText("");
    }
    //Einstellungen speichern
    public void einstellungenSpeichern(){
        String[] daten = new String[11];
        daten[0] = dbHost.getText();
        daten[1] = dbPort.getText();
        daten[2] = dbName.getText();
        daten[3] = dbUsername.getText();
        daten[4] = dbPasswort.getText();
        daten[5] = mailSmtp.getText();
        daten[6] = mailUser.getText();
        daten[7] = mailPasswort.getText();
        daten[8] = mailPort.getText();
        daten[9] = mailAbsName.getText();
        daten[10] = mailAbsEmail.getText();
        try {
            for (int i = 0; i < daten.length; i++){
                if(daten[i] == null){
                    daten[i] = "";
                }
            }
            db.db_update(daten[0],daten[1],daten[2],daten[3],daten[4],daten[5],daten[6],daten[7],daten[8],daten[9],daten[10]);
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
    //Neu angelegten Mitarbeiter speichern
    public void mitarbeiterAnlegenSpeichern(){
        //daten einlesen
        String[] daten = new String[8];
        daten[0] = vName.getText();
        daten[1] = nName.getText();
        daten[2] = position.getText();
        daten[3] = standort.getText();
        daten[4] = (String) bundeslaender.getValue();
        daten[5] = email.getText();
        daten[6] = telefon.getText();

        try {
            Mitarbeiter mitarbeiter = new Mitarbeiter();

            //Benutzername und Passwort generieten
            String[] pid = mitarbeiter.generierePid(daten[0],daten[1]);

            //Benutzername und Passwort verschicken
            Mail mail = new Mail();
            String adresse = daten[5];
            String betreff = "Zugangsdaten";
            String nachricht = "<html><head></head><body><h1>Deine Zugangsdaten:</h1><p>Benutzername: "+pid[0]+"</p><p>Passwort: "+pid[1]+"</p></body></html>";
            try {
                mail.sendMail(adresse,betreff,nachricht);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            //Passwort verschluesseln
            String hpsw = mitarbeiter.hashPasswort(daten[1]);

            mitarbeiter.setVname(daten[0]);
            mitarbeiter.setNname(daten[1]);
            mitarbeiter.setPosition(daten[2]);
            mitarbeiter.setStandort(daten[3]);
            mitarbeiter.setBland(daten[4]);
            mitarbeiter.setEmail(daten[5]);
            mitarbeiter.setTelefon(daten[6]);
            mitarbeiter.setBenutzername(pid[0]);
            mitarbeiter.setPasswort(hpsw);

            Admin admin = new Admin(mitarbeiter);
            //Mitarbeiter speichern
            admin.mitarbeiterAnlegen(mitarbeiter);

        }catch(NullPointerException e){
            messageAnlegen.setText("Es müssen alle Felder ausgefüllt sein!");
        }
        vName.setText("");
        nName.setText("");
        position.setText("");
        standort.setText("");
        email.setText("");
        telefon.setText("");
        messageAnlegen.setText("Daten gespeichert");
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
