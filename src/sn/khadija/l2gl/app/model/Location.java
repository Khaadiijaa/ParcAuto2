package sn.khadija.l2gl.app.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Location extends Entite implements Identifiable {

    private final Vehicule vehicule;
    private final Conducteur conducteur;
    private final LocalDate dateDebut;
    private Optional<LocalDate> dateFin;
    private final int prixJour;

    public Location(Long id, Vehicule vehicule, Conducteur conducteur,
                    LocalDate dateDebut, int prixJour) {
        super(id);
        if (prixJour < 0) throw new IllegalArgumentException("Prix/jour >= 0");
        this.vehicule = vehicule;
        this.conducteur = conducteur;
        this.dateDebut = dateDebut;
        this.dateFin = Optional.empty();
        this.prixJour = prixJour;
    }

    public Vehicule getVehicule() { return vehicule; }
    public Conducteur getConducteur() { return conducteur; }
    public LocalDate getDateDebut() { return dateDebut; }
    public Optional<LocalDate> getDateFin() { return dateFin; }
    public int getPrixJour() { return prixJour; }

    public void terminer(LocalDate fin) {
        if (fin.isBefore(dateDebut))
            throw new IllegalArgumentException("La date de fin doit être >= date de début");
        this.dateFin = Optional.of(fin);
    }

    public long dureeJours() {
        LocalDate fin = dateFin.orElse(LocalDate.now());
        return ChronoUnit.DAYS.between(dateDebut, fin);
    }

    @Override
    public String afficher() {
        return "Location[" + getId() + "] " + vehicule.getImmat()
                + " -> " + conducteur.getNom()
                + " | début: " + dateDebut
                + " | durée: " + dureeJours() + " jours"
                + " | prix/j: " + prixJour;
    }
}