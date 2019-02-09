package zeiterfassungssystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Controller {
    //Variablen
    Datenbank db = new Datenbank();
    Mitarbeiter mitarbeiter;
    @FXML
    TableView tabelle;
    @FXML
    TableColumn colTag;
    @FXML
    TableColumn colVon;
    @FXML
    TableColumn colBis;
    @FXML
    TableColumn colPause;
    @FXML
    TableColumn colIst;
    @FXML
    TableColumn colSoll;
    @FXML
    TableColumn colDiff;
    @FXML
    TableColumn colSaldo;

    @FXML
    Pane rootPane, logoPane, einstellungen, mitAnlegen, mitBearbeiten, mitLoeschen, pswErneuern, zeitenErfassen, auswerten;
    @FXML
    ImageView image;
    @FXML
    Label messageEinstellungen, messageAnlegen, messageBearbeiten, messageLoeschen, messagePswErneuern, messageZeitenErfassen, auswertungText;
    @FXML
    TextField dbHost, dbPort, dbName, dbUsername, dbPasswort, mailSmtp, mailUser, mailPasswort, mailPort, mailAbsName, mailAbsEmail,
            vName, nName, position, standort, email, telefon,
            vName1, nName1, position1, standort1, email1, telefon1, von, bis;
    @FXML
    ComboBox bundeslaender, bundeslaender1, mitarbeiterauswahl, mitarbeiterauswahl2, mitarbeiterauswahl3, mitarbeiterauswahl4, mitarbeiterauswahl5, jahre, monate;

    @FXML
    DatePicker datum;

    ObservableList<String> laender = FXCollections.observableArrayList("Baden-Württemberg","Bayern","Berlin","Brandenburg","Bremen","Hamburg","Hessen","Mecklenburg-Vorpommern","Niedersachsen","Nordrhein-Westfalen","Rheinland-Pfalz","Saarland,Sachsen","Sachsen-Anhalt","Schleswig-Holstein","Thüringen");
    ObservableList<String> jahr = FXCollections.observableArrayList("2019");
    ObservableList<String> monat = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12");

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
        bundeslaender.setValue("Bundesland");
        email.setText("");
        telefon.setText("");
        messageAnlegen.setText("Daten gespeichert");
    }

    //------------------------------------------------------------------------------------------------------------------
    //Mitarbeiter Bearbeiten
    public void mitarbeiterBearbeiten(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitBearbeiten);
        mitarbeiterauswahl.getItems().clear();
        bundeslaender1.setItems(laender);
        db.db_open();
        ArrayList<String> liste = db.mitarbeiterlisteLesen();
        ObservableList mitarbeiterliste = FXCollections.observableArrayList(liste);
        mitarbeiterauswahl.getItems().addAll(mitarbeiterliste);
        liste.clear();

        messageBearbeiten.setText("");
    }
    //Ausgewaehlten Mitarbeiter zum Bearbeiten anzeigen
    public void mitarbeiterBearbeitenAuswahl(){
        String auswahl = (String) mitarbeiterauswahl.getValue();
        String[] segs = auswahl.split( Pattern.quote( ", " ) );
        String id = segs[0];
        String nn = segs[1];
        String vn = segs[2];
        db.db_open();
        Mitarbeiter mitarbeiter;
        mitarbeiter = db.mitarbeiterEinLesen(id);
        vName1.setText(mitarbeiter.getVname());
        nName1.setText(mitarbeiter.getNname());
        position1.setText(mitarbeiter.getPosition());
        standort1.setText(mitarbeiter.getStandort());
        bundeslaender1.setValue(mitarbeiter.getBland());
        email1.setText(mitarbeiter.getEmail());
        telefon1.setText(mitarbeiter.getTelefon());
        messageBearbeiten.setText("");
    }
    //Neu angelegten Mitarbeiter speichern
    //TODO Wenn der Vorname oder der Nachname geaendert wurde, muss auch ein neuer Benutsetname und ein neues Passwort erstellt werden!
    public void mitarbeiterBearbeitenSpeichern(){
        String auswahl = (String) mitarbeiterauswahl.getValue();
        String[] segs = auswahl.split( Pattern.quote( ", " ) );
        String id = segs[0];
        String nn = segs[1];
        String vn = segs[2];
        //daten einlesen
        String[] daten = new String[8];
        daten[0] = vName1.getText();
        daten[1] = nName1.getText();
        daten[2] = position1.getText();
        daten[3] = standort1.getText();
        daten[4] = (String) bundeslaender1.getValue();
        daten[5] = email1.getText();
        daten[6] = telefon1.getText();

        //System.out.println(id);

        try {
            Mitarbeiter mitarbeiter = new Mitarbeiter();
            mitarbeiter.setId(Integer.parseInt(id));
            mitarbeiter.setVname(daten[0]);
            mitarbeiter.setNname(daten[1]);
            mitarbeiter.setPosition(daten[2]);
            mitarbeiter.setStandort(daten[3]);
            mitarbeiter.setBland(daten[4]);
            mitarbeiter.setEmail(daten[5]);
            mitarbeiter.setTelefon(daten[6]);

            Admin admin = new Admin(mitarbeiter);

            //Mitarbeiter speichern
            admin.mitarbeiterUpdate(mitarbeiter);

        }catch(NullPointerException e){
            messageBearbeiten.setText("Es müssen alle Felder ausgefüllt sein!");
        }
        vName1.setText("");
        nName1.setText("");
        position1.setText("");
        standort1.setText("");
        bundeslaender1.setValue("Bundesland");
        email1.setText("");
        telefon1.setText("");
        messageBearbeiten.setText("Daten gespeichert");
    }

    //------------------------------------------------------------------------------------------------------------------
    //Mitarbeiter Loeschen
    public void mitarbeiterLoeschen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(mitLoeschen);
        mitarbeiterauswahl2.getItems().clear();
        db.db_open();
        ArrayList<String> liste2 = db.mitarbeiterlisteLesen();
        ObservableList mitarbeiterliste2 = FXCollections.observableArrayList(liste2);
        mitarbeiterauswahl2.getItems().addAll(mitarbeiterliste2);

        messageLoeschen.setText("");
    }
    public void mitarbeiterLoeschenButton(){
        String auswahl = (String) mitarbeiterauswahl2.getValue();
        String[] segs = auswahl.split( Pattern.quote( ", " ) );
        String id = segs[0];
        String nn = segs[1];
        String vn = segs[2];
        Admin admin = new Admin();
        admin.mitarbeiterLoeschen(Integer.parseInt(id));
        messageLoeschen.setText("Mitarbeiter " + vn + " " + nn + " ist inaktiv gesetzt!");
    }

    //------------------------------------------------------------------------------------------------------------------
    //Passwort erneuern
    public void passwortErneuern(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(pswErneuern);
        mitarbeiterauswahl3.getItems().clear();
        db.db_open();
        ArrayList<String> liste3 = db.mitarbeiterlisteLesen();
        ObservableList mitarbeiterliste3 = FXCollections.observableArrayList(liste3);
        mitarbeiterauswahl3.getItems().addAll(mitarbeiterliste3);

        messagePswErneuern.setText("");
    }
    public void passwortGenerieren(){
        String auswahl = (String) mitarbeiterauswahl3.getValue();
        String[] segs = auswahl.split( Pattern.quote( ", " ) );
        String id = segs[0];
        String nn = segs[1];
        String vn = segs[2];

        db.db_open();
        Mitarbeiter mitarbeiter;
        mitarbeiter = db.mitarbeiterEinLesen(id);
        String vorn = mitarbeiter.getVname();
        String nachn = mitarbeiter.getNname();
        String mail = mitarbeiter.getEmail();

        //Benutzername und Passwort generieren
        String[] pid = mitarbeiter.generierePid(vorn,nachn);

        //Benutzername und Passwort verschicken
        Mail mailer = new Mail();
        String adresse = mail;
        String betreff = "Zugangsdaten";
        String nachricht = "<html><head></head><body><h1>Deine Zugangsdaten:</h1><p>Benutzername: "+pid[0]+"</p><p>Passwort: "+pid[1]+"</p></body></html>";
        try {
            mailer.sendMail(adresse,betreff,nachricht);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //Passwort verschluesseln
        String hpsw = mitarbeiter.hashPasswort(pid[1]);

        mitarbeiter.setBenutzername(pid[0]);
        mitarbeiter.setPasswort(hpsw);

        Admin admin = new Admin(mitarbeiter);
        //Mitarbeiter speichern
        admin.mitarbeiterUpdatePid(Integer.parseInt(id),pid[0],hpsw);

        messagePswErneuern.setText("Mitarbeiter " + vorn + " " + nachn + " hat einen neuen Benutzername und Passwort bekommen ("+pid[1]+")!");
    }

    //------------------------------------------------------------------------------------------------------------------
    //Zeiten manuell erfassen
    public void zeitenErfassen(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(zeitenErfassen);

        mitarbeiterauswahl4.getItems().clear();
        db.db_open();
        ArrayList<String> liste4 = db.mitarbeiterlisteLesen();
        ObservableList mitarbeiterliste4 = FXCollections.observableArrayList(liste4);
        mitarbeiterauswahl4.getItems().addAll(mitarbeiterliste4);

        messageZeitenErfassen.setText("");
    }

    //Zeiten erfassen speichern
    public void zeitenErfassenSpeichern(){
        String auswahl = (String) mitarbeiterauswahl4.getValue();
        String[] segs = auswahl.split( Pattern.quote( ", " ) );
        String id4 = segs[0];
        String nn4 = segs[1];
        String vn4 = segs[2];
        String date = String.valueOf(datum.getValue());
        String vonEingabe = von.getText();
        String bisEingabe = bis.getText();

        Admin admin = new Admin();

        admin.zeitenManuellErfassen(Integer.parseInt(id4),date,vonEingabe,bisEingabe);

        messageZeitenErfassen.setText("Fuer "+vn4+" "+nn4+" wurde eine Arbeitszeit von "+vonEingabe+" bis "+bisEingabe+" am "+date+" eingetragen");
    }

    //Auswerten
    //------------------------------------------------------------------------------------------------------------------
    public void auswerten() {
        rootPane.getChildren().clear();
        rootPane.getChildren().add(auswerten);
        db.db_open();
        ArrayList<String> liste5 = db.mitarbeiterlisteLesen();
        ObservableList mitarbeiterliste5 = FXCollections.observableArrayList(liste5);
        mitarbeiterauswahl5.getItems().addAll(mitarbeiterliste5);
        liste5.clear();
        jahre.setItems(jahr);
        monate.setItems(monat);
    }
    //Auswertung anzeigen
    public void auswertungAnzeigen(){
        String auswahl = (String) mitarbeiterauswahl5.getValue();
        String[] segs = auswahl.split( Pattern.quote( ", " ) );
        String id5 = segs[0];
        String nn5 = segs[1];
        String vn5 = segs[2];
        String jahr = (String) jahre.getValue();
        String monat = (String) monate.getValue();

        db.db_open();
        String[][] erg = db.auswertungErstellen(id5,jahr,monat);
        
        TableView<zeileAuswertung> table = new TableView<zeileAuswertung>();
        ObservableList<zeileAuswertung> data = FXCollections.observableArrayList();
        for(int i = 0; i < erg.length; i++) {
            data.addAll(new zeileAuswertung(erg[i][0], erg[i][1], erg[i][2], erg[i][3], erg[i][4], erg[i][5], erg[i][6], erg[i][7]));
        }
        colTag.setCellValueFactory(new PropertyValueFactory("tag"));
        colVon.setCellValueFactory(new PropertyValueFactory("von"));
        colBis.setCellValueFactory(new PropertyValueFactory("bis"));
        colPause.setCellValueFactory(new PropertyValueFactory("pause"));
        colIst.setCellValueFactory(new PropertyValueFactory("ist"));
        colSoll.setCellValueFactory(new PropertyValueFactory("soll"));
        colDiff.setCellValueFactory(new PropertyValueFactory("diff"));
        colDiff.setStyle("-fx-alignment: CENTER-RIGHT;");
        colSaldo.setCellValueFactory(new PropertyValueFactory("saldo"));
        colSaldo.setStyle("-fx-alignment: CENTER-RIGHT;");
//Die Tabelle anzeigen.

        tabelle.setItems(data);

        int k = 0;
        String d = "";

        for(int j = 0; j < erg.length; j++){
            d = erg[j][8];
            k++;
        }

        int stdsoll = k * 8;
        int dsec = Integer.parseInt(d);
        int sollsec = stdsoll * 60 * 60;
        int istsec = sollsec + dsec;
        double diffprozent = dsec / (sollsec / 100.0);
        double prozent = Math.round(diffprozent * 100.0) / 100.0;
        auswertungText.setText("Stunden soll: " + db.secFormat(sollsec) + "   Stunden ist: " + db.secFormat(istsec) + "   Differenz: " + db.secFormat(dsec) + " Stunden ( " + prozent + " %)");
    }
    /*private <zeile> ObservableList<zeile> getZeile() {

        zeile zeile1 = new zeile();

        ObservableList<zeile> list = FXCollections.observableArrayList(zeile1);
        return list;
    }*/

    //Hilfe
    //------------------------------------------------------------------------------------------------------------------
}
