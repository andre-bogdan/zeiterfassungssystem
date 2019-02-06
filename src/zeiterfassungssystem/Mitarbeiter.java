package zeiterfassungssystem;

import org.mindrot.jbcrypt.BCrypt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class Mitarbeiter {
    private int id;
    private String vname;
    private String nname;
    private String position;
    private String standort;
    private String bland;
    private String email;
    private String telefon;
    private String benutzername;
    private String passwort;

    public Mitarbeiter() {

    }

    public Mitarbeiter(String vname, String nname, String position, String standort, String bland, String email, String telefon, String benutzername, String passwort) {
        this.vname = vname;
        this.nname = nname;
        this.position = position;
        this.standort = standort;
        this.bland = bland;
        this.email = email;
        this.telefon = telefon;
        this.benutzername = benutzername;
        this.passwort = passwort;
    }

    public Mitarbeiter(Mitarbeiter mitarbeiter){
        this.vname = mitarbeiter.vname;
        this.nname = mitarbeiter.nname;
        this.position = mitarbeiter.position;
        this.standort = mitarbeiter.standort;
        this.bland = mitarbeiter.bland;
        this.email = mitarbeiter.email;
        this.telefon = mitarbeiter.telefon;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStandort() {
        return standort;
    }

    public void setStandort(String standort) {
        this.standort = standort;
    }

    public String getBland() {
        return bland;
    }

    public void setBland(String bland) {
        this.bland = bland;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getBenutzername() { return benutzername; }

    public void setBenutzername(String benutzername) { this.benutzername = benutzername; }

    public String getPasswort() { return passwort; }

    public void setPasswort(String passwort) { this.passwort = passwort; }

    public String[] generierePid(String vname, String nname){
        String[] pid = new String[2];
        char char1 = vname.charAt(0);
        String benutzername = char1+nname;
        String pass = String.valueOf(pswGenerieren());
        pid[0] = benutzername;
        pid[1] = pass;
        return pid;
    }

    protected String pswGenerieren(){
        String[] psw = {"a","b","c","d","e","f","g","h","j","k","m","n","p","q","r","s","t","u","v","w","x","x","z",
                "A","B","C","D","E","F","G","H","J","K","M","N","P","Q","R","S","T","U","V","W","X","Y","Z",
                "1","2","3","4","5","6","7","8","9",
                "?","!","&","%","#"};
        //mischen
        psw = mischen(psw);
        //Ersten 6 Zahlen auswaehlen
        psw = Arrays.copyOfRange(psw, 0, 8);
        //Zahlen ausgeben
        String out = psw[0]+psw[1]+psw[2]+psw[3]+psw[4]+psw[5]+psw[6]+psw[7];
        return out;
    }

    private String[] mischen(String[] zeichenkette) {
        //Hilfsvariblen
        String tmp;
        int rand;
        //Zufallsfunktion
        Random z = new Random();
        for (int i = 0; i < zeichenkette.length; i++)
        {
            //Zufallszahl
            rand = z.nextInt(zeichenkette.length);
            //Zahlen tauschen
            tmp = zeichenkette[i];
            zeichenkette[i] = zeichenkette[rand];
            zeichenkette[rand] = tmp;
        }
        //eingegebenes array gemischt zuruckgeben
        return zeichenkette;
    }

    public static String hashPasswort(String txt) {

            return txt;
    }

    /*public static String hashPasswort(String txt) {

        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            //error action
        }
        return null;
    }*/
}
