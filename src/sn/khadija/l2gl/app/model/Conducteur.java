package sn.khadija.l2gl.app.model;

public class Conducteur extends Entite implements Identifiable {

    private final String nom;
    private final String permis;

    public Conducteur(Long id, String nom, String permis) {
        super(id);
        if (nom == null || nom.isBlank()) throw new IllegalArgumentException("Nom non vide");
        if (permis == null || permis.isBlank()) throw new IllegalArgumentException("Permis non vide");
        this.nom = nom;
        this.permis = permis;
    }

    public String getNom() { return nom; }
    public String getPermis() { return permis; }

    @Override
    public String afficher() {
        return "Conducteur[" + getId() + "] " + nom + " - permis: " + permis;
    }
}