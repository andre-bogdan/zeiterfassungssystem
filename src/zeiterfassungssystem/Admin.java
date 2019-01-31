package zeiterfassungssystem;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class Admin extends Mitarbeiter {
    public Admin(Mitarbeiter mitarbeiter) {
        super(mitarbeiter);
    }

    public void mitarbeiterAnlegen(Mitarbeiter mitarbeiter){
        Datenbank db = new Datenbank();
        db.db_open();
        db.mitarbeiterSchreiben(mitarbeiter);
    }

    public String[] generierePid(){
        String[] pid = new String[2];
        String benutzername;
        benutzername = this.getVname();
        char char1 = benutzername.charAt(0);
        benutzername = char1+this.getNname();
        String pass = String.valueOf(pswGenerieren());
        pid[0] = benutzername;
        pid[1] = pass;
        return pid;
    }

    protected String[] pswGenerieren(){
        String[] psw = {"a","b","c","d","e","f","g","h","j","k","m","n","p","q","r","s","t","u","v","w","x","x","z","1","2","3","4","5","6","7","8","9","@","$","&","%","#"};
        //mischen
        psw = mischen(psw);
        //Ersten 6 Zahlen auswaehlen
        psw = Arrays.copyOfRange(psw, 0, 8);
        //Zahlen ausgeben
        return psw;
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

    public String getMd5(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
