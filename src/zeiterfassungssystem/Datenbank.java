package zeiterfassungssystem;

import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Datenbank {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

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
}
