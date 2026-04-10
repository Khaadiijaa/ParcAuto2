package sn.khadija.l2gl.app.model;

public record LigneRapport(String immat, int km, int annee, String etat) {

    public String afficher() {
        return "Rapport -> " + immat + " | " + km + " km | " + annee + " | état: " + etat;
    }
}