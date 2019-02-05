import java.util.Date;
import java.util.Objects;

public class Billet {
    private Lieu lieuDepart;
    private Lieu lieuArrivee;
    private Date dateDepart;
    private Date dateArrivee;
    private Double prix;

    public Billet() {
    }

    public Billet(Billet billet, Double prix) {
        lieuArrivee = billet.lieuArrivee;
        lieuDepart = billet.lieuDepart;
        dateDepart = billet.dateDepart;
        dateArrivee = billet.dateArrivee;
        this.prix = prix.doubleValue();
    }

    public Billet(Billet billet) {
        lieuArrivee = billet.lieuArrivee;
        lieuDepart = billet.lieuDepart;
        dateDepart = billet.dateDepart;
        dateArrivee = billet.dateArrivee;
        this.prix = billet.prix.doubleValue();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Billet billet = (Billet) o;
        return lieuDepart == billet.lieuDepart &&
                lieuArrivee == billet.lieuArrivee &&
                Objects.equals(dateDepart, billet.dateDepart) &&
                Objects.equals(dateArrivee, billet.dateArrivee) &&
                Objects.equals(prix, billet.prix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lieuDepart, lieuArrivee, dateDepart, dateArrivee, prix);
    }
}
