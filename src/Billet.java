import java.util.Date;

public class Billet {
    private Lieu lieuDepart;
    private Lieu lieuArrivee;
    private Date dateDepart;
    private Date dateArrivee;
    private Double prix;

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

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Billet{" +
                "lieuDepart=" + lieuDepart +
                ", lieuArrivee=" + lieuArrivee +
                ", dateDepart=" + dateDepart +
                ", dateArrivee=" + dateArrivee +
                ", prix=" + prix +
                '}';
    }
}
