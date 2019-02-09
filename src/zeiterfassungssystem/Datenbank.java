package zeiterfassungssystem;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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

    public void updatePid(int id, String bname, String pas) {
        try {
            statement = connection.createStatement();
            String sqlQuery = "UPDATE mitarbeiter SET " +
                    "benutzername = '" + bname + "'," +
                    "passwort = '" + pas + "'" +
                    "WHERE id = '" + id + "'";
            statement.executeUpdate(sqlQuery);
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void zeitenManuellErfassen(int id, String tag, String von, String bis) {
        try {

            statement = connection.createStatement();
            String sqlQueryDel = "DELETE FROM arbeitszeiten WHERE userID = '"+id+"' AND tag = '"+tag+"'";
            statement.executeUpdate(sqlQueryDel);
            statement.close();

            statement = connection.createStatement();
            String sqlQuery = "INSERT INTO arbeitszeiten (userID,tag,kommt,geht) values ( " +
                    "'" + id + "'," +
                    "'" + tag + "'," +
                    "'" + von + "'," +
                    "'" + bis + "' " +
                    ")";
            statement.executeUpdate(sqlQuery);
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[][] auswertungErstellen(String id, String jahr, String monat) {
        String[][] out = null;
        try {
            statement = connection.createStatement();
            String sqlQuery = "SELECT  tag, kommt, geht , sec_to_time(time_to_sec(geht)-time_to_sec(kommt)-60*45) as ist," +
                    "time_to_sec(subtime(subtime(subtime(geht,kommt),'00:45'),'08:00')) as diff " +
                    "FROM arbeitszeiten " +
                    "WHERE MONTH(tag) = '" + monat + "' AND YEAR(tag) = '" + jahr + "' AND userID = '" + id + "' AND kommt is not null AND geht is not null";
            resultSet = statement.executeQuery(sqlQuery);
            //Ermitteln wie gross das array sein muss
            int k = 0;
            while (resultSet.next()) {
                k++;
            }

            out = new String[k][8];

            statement = connection.createStatement();
            String sqlQuery2 = "SELECT  tag, kommt, geht , sec_to_time(time_to_sec(geht)-time_to_sec(kommt)-60*45) as ist," +
                    "time_to_sec(subtime(subtime(subtime(geht,kommt),'00:45'),'08:00')) as diff " +
                    "FROM arbeitszeiten " +
                    "WHERE MONTH(tag) = '" + monat + "' AND YEAR(tag) = '" + jahr + "' AND userID = '" + id + "' AND kommt is not null AND geht is not null";
            resultSet = statement.executeQuery(sqlQuery2);

            int saldo = 0;
            int i = 0;
            while (resultSet.next()) {
                out[i][0] = resultSet.getString("tag");
                //System.out.println(out[i][0]);
                out[i][1] = resultSet.getString("kommt");
                //System.out.println(out[i][1]);
                out[i][2] = resultSet.getString("geht");
                //System.out.println(out[i][2]);
                out[i][3] = "00:45";
                //System.out.println(out[i][3]);
                out[i][4] = resultSet.getString("ist");
                //System.out.println(out[i][4]);
                out[i][5] = "08:00";
                //System.out.println(out[i][5]);
                int dif = resultSet.getInt("diff");
                out[i][6] = secFormat(dif);
                //System.out.println(out[i][6]);
                saldo = saldo + dif;
                out[i][7] = secFormat(saldo);
                //System.out.println(out[i][7]);
                i++;
            }

            statement.close();

        } catch (SQLException e) {
            System.out.println("Fehler bei Abfrage: " + e);
        }
        return out;
    }

    public String secFormat(int secIn){
        String out = null;
        if (secIn > 0){
            int hh = (int) (secIn / 3600);
            int mm = (int) ((secIn - hh*3600) / 60);
            int ss = secIn - hh*3600 - mm*60;
            DecimalFormat format = new DecimalFormat("00");
            out = format.format(hh) + ":" + format.format(mm) + ":" + format.format(ss);
        }
        else if (secIn < 0){
            secIn = secIn * -1;
            int hh = (int) (secIn / 3600);
            int mm = (int) ((secIn - hh*3600) / 60);
            int ss = secIn - hh*3600 - mm*60;
            DecimalFormat format = new DecimalFormat("00");
            out = "- "+format.format(hh) + ":" + format.format(mm) + ":" + format.format(ss);
        }else{
            out = "00:00:00";
        }



        return out;
    }

    public int zufallszeit(int min, int max){
        int zeit = 0;
        Random rand = new Random();
        zeit = rand.nextInt((max - min) + 1) + min;
        return zeit;
    }

    public void dbFuellen(int id, int i, String startDatum){
        Date tag = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            tag = df.parse(startDatum);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone("CET"));
        cal.setTime(tag);

        for(int j = 0; j < i; j++) {
            int stdk = zufallszeit(6, 8);
            int mink = zufallszeit(0, 59);
            String von = "0" + stdk + ":" + mink;
            int stdg = zufallszeit(15, 17);
            int ming = zufallszeit(0, 59);
            String bis = stdg + ":" + ming;
            int dow = cal.get(Calendar.DAY_OF_WEEK);
            if (dow >= Calendar.MONDAY && dow <= Calendar.FRIDAY) {
                try {
                    statement = connection.createStatement();
                    String sqlQuery = "INSERT INTO arbeitszeiten (userID,tag,kommt,geht) values ( " +
                            "'" + id + "'," +
                            "'" + df.format(cal.getTime()) + "'," +
                            "'" + von + "'," +
                            "'" + bis + "' " +
                            ")";
                    statement.executeUpdate(sqlQuery);
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    public int getArbeitstage(String von, String bis) {

        // Lokale Variable zur Ausgabe der Arbeitstage
        int arbeitstage = 0;
        java.util.Date anfang = null;
        Date ende = null;

        // Datumsformat festlegen
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // Eingaben parsen
        try {
            anfang = df.parse(von);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            ende = df.parse(bis);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Calender-Objekt erstellen
        Calendar calAnfang = new GregorianCalendar();
        calAnfang.setTimeZone(TimeZone.getTimeZone("CET"));
        calAnfang.setFirstDayOfWeek(1);
        calAnfang.setTime(anfang);

        Calendar calEnde = new GregorianCalendar();
        calEnde.setTimeZone(TimeZone.getTimeZone("CET"));
        calEnde.setTime(ende);

        // Berechnung der Arbeitstage
        if (calEnde.getTimeInMillis() <= calAnfang.getTimeInMillis()) {
            throw new IllegalArgumentException();
        } else {
            while (calEnde.getTimeInMillis() >= calAnfang.getTimeInMillis()) {
                int dow = calAnfang.get(Calendar.DAY_OF_WEEK);
                if ((dow != 7) && (dow != 6)) {
                    arbeitstage = arbeitstage + 1;
                }
                calAnfang.add(Calendar.DAY_OF_WEEK, 1);
            }
        }

        return arbeitstage;
    }


}
