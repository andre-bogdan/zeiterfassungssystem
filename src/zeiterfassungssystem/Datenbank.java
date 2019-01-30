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

    private String host;
    private String port;
    private String dbName;
    private String user;
    private String pass;
    private String smtp;
    private String emailUser;
    private String emailPass;

    public Datenbank() {
        Reader reader = null;
        try {
            reader = new FileReader("system.ini");
            Properties prop = new Properties();
            prop.load(reader);
            this.host = prop.getProperty("HOST");
            this.user = prop.getProperty("USER");
            this.pass = prop.getProperty("PWD");
            this.dbName = prop.getProperty("DATENBANK");
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
    public void db_update(String host, String port, String dbName, String user, String pass, String smtp, String emailUser, String emailPass){
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
        String[] configData = new String[8];
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

    /*public void mitarbeiterAnlegen(Kunde kunde) {
        try {
            statement = connection.createStatement();
            String sqlQuery = "INSERT INTO kunden (" +
                    "firma,anrede,vorname,nachname, strasse, hsnr, plz, ort, telefon, telefax, web, email, " +
                    "ap_anrede, ap_vorname, ap_nachname, ap_telefon, ap_email, stdsatz) VALUES(" +
                    "'" + kunde.getFirma() + "','" + kunde.getAnrede() + "','" + kunde.getVorname() + "','" + kunde.getName() + "'," +
                    "'" + kunde.getStrasse() + "','" + kunde.getHausnummer() + "'," +
                    "'" + kunde.getPlz() + "','" + kunde.getOrt() + "','" + kunde.getTelefon() + "','" + kunde.getFax() + "'," +
                    "'" + kunde.getWeb() + "','" + kunde.getEmail() + "','" + kunde.getAnrede() + "','" + kunde.getApVorname() + "'," +
                    "'" + kunde.getApName() + "','" + kunde.getApTelefon() + "','" + kunde.getApEmail() + "'," +
                    "'" + kunde.getStundenSatz() + "') ";

            statement.executeUpdate(sqlQuery);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
