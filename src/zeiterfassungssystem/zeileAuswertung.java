package zeiterfassungssystem;

import javafx.beans.property.SimpleStringProperty;

public class zeileAuswertung {
    private final SimpleStringProperty tag;
    private final SimpleStringProperty von;
    private final SimpleStringProperty bis;
    private final SimpleStringProperty pause;
    private final SimpleStringProperty ist;
    private final SimpleStringProperty soll;
    private final SimpleStringProperty diff;
    private final SimpleStringProperty saldo;

    public String getTag() {
        return tag.get();
    }

    public SimpleStringProperty tagProperty() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag.set(tag);
    }

    public String getVon() {
        return von.get();
    }

    public SimpleStringProperty vonProperty() {
        return von;
    }

    public void setVon(String von) {
        this.von.set(von);
    }

    public String getBis() {
        return bis.get();
    }

    public SimpleStringProperty bisProperty() {
        return bis;
    }

    public void setBis(String bis) {
        this.bis.set(bis);
    }

    public String getPause() {
        return pause.get();
    }

    public SimpleStringProperty pauseProperty() {
        return pause;
    }

    public void setPause(String pause) {
        this.pause.set(pause);
    }

    public String getIst() {
        return ist.get();
    }

    public SimpleStringProperty istProperty() {
        return ist;
    }

    public void setIst(String ist) {
        this.ist.set(ist);
    }

    public String getSoll() {
        return soll.get();
    }

    public SimpleStringProperty sollProperty() {
        return soll;
    }

    public void setSoll(String soll) {
        this.soll.set(soll);
    }

    public String getDiff() {
        return diff.get();
    }

    public SimpleStringProperty diffProperty() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff.set(diff);
    }

    public String getSaldo() {
        return saldo.get();
    }

    public SimpleStringProperty saldoProperty() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo.set(saldo);
    }

    public zeileAuswertung(String tag, String von, String bis, String pause, String ist, String soll, String diff, String saldo) {
        this.tag = new SimpleStringProperty(tag);
        this.von = new SimpleStringProperty(von);
        this.bis = new SimpleStringProperty(bis);
        this.pause = new SimpleStringProperty(pause);
        this.ist = new SimpleStringProperty(ist);
        this.soll = new SimpleStringProperty(soll);
        this.diff = new SimpleStringProperty(diff);
        this.saldo = new SimpleStringProperty(saldo);
    }
}
