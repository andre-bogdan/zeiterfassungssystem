package zeiterfassungssystem;

public class Mitarbeiter {
    private String
    vname, nname, position, standort, bland, email, telefon;

    public Mitarbeiter(String vname, String nname, String position, String standort, String bland, String email, String telefon) {
        this.vname = vname;
        this.nname = nname;
        this.position = position;
        this.standort = standort;
        this.bland = bland;
        this.email = email;
        this.telefon = telefon;
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
}
