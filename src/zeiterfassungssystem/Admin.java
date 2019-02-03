package zeiterfassungssystem;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class Admin extends Mitarbeiter {
    public Admin(Mitarbeiter mitarbeiter) {
        super();
    }

    public void mitarbeiterAnlegen(Mitarbeiter mitarbeiter){
        Datenbank db = new Datenbank();
        db.db_open();
        db.mitarbeiterSchreiben(mitarbeiter);
    }
}
