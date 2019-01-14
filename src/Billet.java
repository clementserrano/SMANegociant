import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Billet {
    private Lieu lieuDepart;
    private Lieu lieuArrivee;
    private Date dateDepart;
    private Date dateArrivee;
    private int prix;

    public Billet() {
        this.lieuDepart = Lieu.PARIS;
        this.lieuArrivee = Lieu.MARSEILLE;
        this.dateDepart = new Date();
        this.dateArrivee = new Date();
        this.prix = 0;
    }

    public Billet(Lieu lieuDepart, Lieu lieuArrivee, Date dateDepart, Date dateArrivee, int prix) {
        this.lieuDepart = lieuDepart;
        this.lieuArrivee = lieuArrivee;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.prix = prix;
    }

    public Lieu getLieuDepart() {
        return lieuDepart;
    }

    public void setLieuDepart(Lieu lieuDepart) {
        this.lieuDepart = lieuDepart;
    }

    public Lieu getLieuArrivee() {
        return lieuArrivee;
    }

    public void setLieuArrivee(Lieu lieuArrivee) {
        this.lieuArrivee = lieuArrivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}
