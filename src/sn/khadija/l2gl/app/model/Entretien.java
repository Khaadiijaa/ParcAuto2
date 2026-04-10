package sn.khadija.l2gl.app.model;

import java.time.LocalDate;

public class Entretien extends Entite implements Identifiable {

    private final Vehicule vehicule;
    private final LocalDate date;
    private final String description;
    private final int cout;

    public Entretien(Long id, Vehicule vehicule, LocalDate date,
                     String description, int cout) {
        super(id);
        if (cout < 0) throw new IllegalArgumentException("Coût >= 0");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description non vide");
        this.vehicule = vehicule;
        this.date = date;
        this.description = description;
        this.cout = cout;
    }

    public Vehicule getVehicule() { return vehicule; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }
    public int getCout() { return cout; }

    @Override
    public String afficher() {
        return "Entretien[" + getId() + "] " + vehicule.getImmat()
                + " | " + date + " | " + description
                + " | coût: " + cout + " FCFA";
    }
}