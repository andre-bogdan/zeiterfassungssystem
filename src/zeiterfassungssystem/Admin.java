package zeiterfassungssystem;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class Admin extends Mitarbeiter {

    public Admin(){
    }

    public Admin(Mitarbeiter mitarbeiter) {
        super();
    }

    public void mitarbeiterAnlegen(Mitarbeiter mitarbeiter){
        Datenbank db = new Datenbank();
        db.db_open();
        db.mitarbeiterSchreiben(mitarbeiter);
    }

    public void mitarbeiterUpdate(Mitarbeiter mitarbeiter){
        Datenbank db = new Datenbank();
        db.db_open();
        db.mitarbeiterSchreibenUpdate(mitarbeiter);
    }

    public void mitarbeiterLoeschen(int id){
        Datenbank db = new Datenbank();
        db.db_open();
        db.deleteMitarbeiter(id);
    }
}
