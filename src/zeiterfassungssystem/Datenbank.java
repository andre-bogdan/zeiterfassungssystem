package zeiterfassungssystem;

import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Datenbank {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private Mitarbeiter mitarbeiter;

    private String
            host, port, dbName, user, pass, smtp, emailUser, emailPass, emailPort, emailAbsName, emailAbsEmail, vorname, nachname, position, standort, bundesland, email, telefon;

    public Datenbank() {
        Reader reader = null;
        try {
            reader = new FileReader("system.ini");
            Properties prop = new Properties();
            prop.load(reader);
            this.host = prop.getProperty("HOST");
            this.port = prop.getProperty("PORT");
            this.user = prop.getProperty("USER");
            this.pass = prop.getProperty("PWD");
            this.dbName = prop.getProperty("DATENBANK");
            this.smtp = prop.getProperty("SMTP");
            this.emailUser = prop.getProperty("MAILU");
            this.emailPass = prop.getProperty("MAILP");
            this.emailPort = prop.getProperty("MAILPort");
            this.emailAbsName = prop.getProperty("MAILNAME");
            this.emailAbsEmail = prop.getProperty("MAILEMAIL");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("Fehler bei MySQL-JDBC-Bridge" + e);
        }

    }

    /**
     * Datenbankverbindung Öffnen
     */
    public boolean db_open() {
        boolean con = false;
        try {
            String url = "jdbc:mysql://" + host + "/" + dbName;
            connection = DriverManager.getConnection(url, user, pass);
            if(connection == null){
                con = false;
            }else con = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Datenbank und E-Mail conf Speichern/Updaten
     */
    public void db_update(String host, String port, String dbName, String user, String pass, String smtp, String emailUser, String emailPass, String emailPort, String emailAbsName, String emailAbsEmail){
        File file = new File("system.ini");
        Writer writer = null;
        try {
            writer = new FileWriter("system.ini");
            Properties prop1 = new Properties(System.getProperties());
            //Datenbank Konfiguration
            prop1.setProperty("HOST", host);
            prop1.setProperty("PORT", port);
            prop1.setProperty("DATENBANK", dbName);
            prop1.setProperty("USER", user);
            prop1.setProperty("PWD", pass);
            //Mail Konfiguration
            prop1.setProperty("SMTP", smtp);
            prop1.setProperty("MAILU", emailUser);
            prop1.setProperty("MAILP", emailPass);
            prop1.setProperty("MAILPort", emailPort);
            prop1.setProperty("MAILNAME", emailAbsName);
            prop1.setProperty("MAILEMAIL", emailAbsEmail);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {

            }

        }
    }

    /**
     * Konfiguratinsdaten aus .ini Datei einlesen
     * @return
     */
    public String[] readIni(){
        String[] configData = new String[11];
        Reader reader = null;
        try {
            reader = new FileReader("system.ini");
            Properties prop = new Properties();
            prop.load(reader);
            configData[0] = prop.getProperty("HOST");
            configData[1] = prop.getProperty("PORT");
            configData[2] = prop.getProperty("DATENBANK");
            configData[3] = prop.getProperty("USER");
            configData[4] = prop.getProperty("PWD");
            configData[5] = prop.getProperty("SMTP");
            configData[6] = prop.getProperty("MAILU");
            configData[7] = prop.getProperty("MAILP");
            configData[8] = prop.getProperty("MAILPORT");
            configData[9] = prop.getProperty("MAILNAME");
            configData[10] = prop.getProperty("MAILEMAIL");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Rueckgabewert
        return configData;
    }

    /**
     * Aktualisiertes Mitarbeiter-Obejekt in db schreiben
     * @param mitarbeiter
     */
    public void mitarbeiterSchreibenUpdate(Mitarbeiter mitarbeiter) {
       this.mitarbeiter = mitarbeiter;
        System.out.println(this.mitarbeiter.getId());
        try {
            statement = connection.createStatement();
            String sqlQuery = "UPDATE mitarbeiter SET " +
                    "vorname = '" + this.mitarbeiter.getVname() + "'," +
                    "nachname = '" + this.mitarbeiter.getNname() + "'," +
                    "position = '" + this.mitarbeiter.getPosition() + "'," +
                    "standort = '" + this.mitarbeiter.getStandort() + "'," +
                    "bundesland = '" + this.mitarbeiter.getBland() + "'," +
                    "email = '" + this.mitarbeiter.getEmail() + "'," +
                    "telefon = '" + this.mitarbeiter.getTelefon() + "' " +
                    "WHERE id = '" + this.mitarbeiter.getId() + "'";
            statement.executeUpdate(sqlQuery);
            System.out.println("Update erflogreich");
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Neu angelegten Mitarbeiter in db schreiben
     * @param mitarbeiter
     */
    public void mitarbeiterSchreiben(Mitarbeiter mitarbeiter) {
        try {
            statement = connection.createStatement();
            String sqlQuery = "insert into mitarbeiter(vorname,nachname,position,standort,bundesland,email,telefon,benutzername,passwort) values ('"+mitarbeiter.getVname()+"','"+mitarbeiter.getNname()+"','"+mitarbeiter.getPosition()+"','"+mitarbeiter.getStandort()+"','"+mitarbeiter.getBland()+"','"+ mitarbeiter.getEmail()+"','"+ mitarbeiter.getTelefon()+"','"+ mitarbeiter.getBenutzername()+"','"+ mitarbeiter.getPasswort()+"')";
            statement.executeUpdate(sqlQuery);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mitarbeiter zur uebergebenen id einlesen und zurueck geben
     * @param id
     * @return
     */
    public Mitarbeiter  mitarbeiterEinLesen(String id) {
        Mitarbeiter mitarbeiter = new Mitarbeiter();
        try {
            statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM mitarbeiter WHERE id = '"+id+"'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                mitarbeiter.setId(resultSet.getInt("id"));
                mitarbeiter.setVname(resultSet.getString("vorname"));
                mitarbeiter.setNname(resultSet.getString("nachname"));
                mitarbeiter.setPosition(resultSet.getString("position"));
                mitarbeiter.setStandort(resultSet.getString("standort"));
                mitarbeiter.setBland(resultSet.getString("bundesland"));
                mitarbeiter.setEmail(resultSet.getString("email"));
                mitarbeiter.setTelefon(resultSet.getString("telefon"));
            }
            statement.close();

        } catch (SQLException e) {
            System.out.println("Fehler bei Abfrage: " + e);
        }
        return mitarbeiter;
    }

    /**
     * id, vornam und nachname zur Anzeige in den ComboBoxen auslesen
     * @return
     */
    public ArrayList<String> mitarbeiterlisteLesen() {
        ArrayList mitarbeiterliste = new ArrayList();
        try {
            statement = connection.createStatement();
            String sqlQuery = "SELECT id, nachname, vorname FROM mitarbeiter";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nachn = resultSet.getString("nachname");
                String vname = resultSet.getString("vorname");
                String eintrag = id+", "+nachn+", "+vname;
                mitarbeiterliste.add(eintrag);
            }
            statement.close();

        } catch (SQLException e) {
            System.out.println("Fehler bei Abfrage: " + e);
        }
        return mitarbeiterliste;
    }

    /**
     * Mitarbeiter loeschen (es werden nur Benutzername und Passwort zurueckgesetzt, weitere Logins zu
     * verhindern - auf die Datensaetze soll weiterhin zugegriffen werden koennen)
     * @param id
     */
    public void deleteMitarbeiter(int id) {
        try {
            statement = connection.createStatement();
            String sqlQuery = "UPDATE mitarbeiter SET " +
                    "benutzername = ''," +
                    "passwort = ''" +
                    "WHERE id = '" + id + "'";
            statement.executeUpdate(sqlQuery);
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
