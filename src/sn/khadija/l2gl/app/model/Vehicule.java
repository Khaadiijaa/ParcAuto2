package sn.khadija.l2gl.app.model;

public class Vehicule extends Entite implements Identifiable {

    private final String immat;
    private int km;
    private final int annee;
    private boolean disponible;
    private boolean panne;
    private String etat;

    public Vehicule(Long id, String immat, int km, int annee) {
        super(id);
        if (immat == null || immat.isBlank())
            throw new IllegalArgumentException("Immatriculation non vide");
        if (km < 0) throw new IllegalArgumentException("Kilométrage >= 0");
        if (annee < 1990) throw new IllegalArgumentException("Année >= 1990");
        this.immat = immat;
        this.km = km;
        this.annee = annee;
        this.disponible = true;
        this.panne = false;
        this.etat = "DISPONIBLE";
    }

    public String getImmat() { return immat; }
    public int getKm() { return km; }
    public int getAnnee() { return annee; }
    public boolean isDisponible() { return disponible; }
    public boolean isEnPanne() { return panne; }
    public String getEtat() { return etat; }

    public void setKm(int km) {
        if (km < 0) throw new IllegalArgumentException("Kilométrage >= 0");
        this.km = km;
    }

    public void setEtat(String etat) { this.etat = etat; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public void setPanne(boolean panne) { this.panne = panne; }

    @Override
    public String afficher() {
        return "Vehicule[" + getId() + "] " + immat
                + " | " + km + " km | " + annee
                + " | état: " + etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicule)) return false;
        Vehicule v = (Vehicule) o;
        return immat.equals(v.immat);
    }

    @Override
    public int hashCode() {
        return immat.hashCode();
    }
}