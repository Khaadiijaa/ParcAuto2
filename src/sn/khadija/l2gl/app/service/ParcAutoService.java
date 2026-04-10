package sn.khadija.l2gl.app.service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;
import sn.khadija.l2gl.app.model.*;

public class ParcAutoService {

    // Étape 6
    private final List<Vehicule> vehicules = new ArrayList<>();
    private final Map<String, Vehicule> indexParImmat = new HashMap<>();

    public void ajouterVehicule(Vehicule v) {
        vehicules.add(v);
        indexParImmat.put(v.getImmat(), v);
    }

    public void supprimerVehicule(String immat) {
        Vehicule v = indexParImmat.remove(immat);
        if (v != null) vehicules.remove(v);
    }

    public Vehicule rechercher(String immat) {
        return indexParImmat.get(immat);
    }

    // Étape 7
    public Set<Vehicule> vehiculesUniques() {
        return new HashSet<>(vehicules);
    }

    // Étape 8
    private final Map<String, List<Entretien>> entretiensParImmat = new HashMap<>();

    public void ajouterEntretien(Entretien e) {
        entretiensParImmat
                .computeIfAbsent(e.getVehicule().getImmat(), k -> new ArrayList<>())
                .add(e);
    }

    public List<Entretien> getEntretiens(String immat) {
        return entretiensParImmat.getOrDefault(immat, new ArrayList<>());
    }

    // Étape 9
    public List<Vehicule> vehiculesDisponibles() {
        return vehicules.stream()
                .filter(v -> v.isDisponible())
                .collect(Collectors.toList());
    }

    public List<String> immatriculationsTriees() {
        return vehicules.stream()
                .map(v -> v.getImmat())
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Vehicule> top3Kilometrage() {
        return vehicules.stream()
                .sorted((a, b) -> b.getKm() - a.getKm())
                .limit(3)
                .collect(Collectors.toList());
    }

    // Étape 10
    public double kilometrageMoyen() {
        return vehicules.stream()
                .mapToInt(v -> v.getKm())
                .average()
                .orElse(0.0);
    }

    public Map<String, Long> nombreParEtat() {
        return vehicules.stream()
                .collect(Collectors.groupingBy(
                        v -> v.isDisponible() ? "DISPONIBLE" : v.isEnPanne() ? "EN_PANNE" : "AUTRE",
                        Collectors.counting()
                ));
    }

    public Map<String, Integer> coutTotalEntretiensParVehicule() {
        return entretiensParImmat.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().stream().mapToInt(Entretien::getCout).sum()
                ));
    }

    // Étape 15 : locations
    private final List<Location> locations = new ArrayList<>();

    public void demarrerLocation(Location loc) {
        loc.getVehicule().setDisponible(false);
        loc.getVehicule().setEtat("EN_LOCATION");
        locations.add(loc);
    }

    public void terminerLocation(Long locationId, LocalDate fin) {
        locations.stream()
                .filter(l -> l.getId().equals(locationId))
                .findFirst()
                .ifPresent(l -> {
                    l.terminer(fin);
                    l.getVehicule().setDisponible(true);
                    l.getVehicule().setEtat("DISPONIBLE");
                });
    }

    public List<Vehicule> vehiculesAReviser(int seuilKm, int seuilAnnee) {
        int anneeActuelle = LocalDate.now().getYear();
        return vehicules.stream()
                .filter(v -> v.getKm() > seuilKm || v.getAnnee() < seuilAnnee)
                .collect(Collectors.toList());
    }

    // Étape 14 : rapport
    public List<LigneRapport> genererRapport() {
        return vehicules.stream()
                .map(v -> new LigneRapport(v.getImmat(), v.getKm(), v.getAnnee(), v.getEtat()))
                .collect(Collectors.toList());
    }

    // Étapes 1-5
    public List<Vehicule> filtrerVehicules(List<Vehicule> src, Test<Vehicule> regle) {
        List<Vehicule> res = new ArrayList<>();
        for (Vehicule v : src) if (regle.tester(v)) res.add(v);
        return res;
    }

    public List<String> mapperVehicules(List<Vehicule> src, Transform<Vehicule, String> f) {
        List<String> res = new ArrayList<>();
        for (Vehicule v : src) res.add(f.appliquer(v));
        return res;
    }

    public void appliquerSurVehicules(List<Vehicule> src, Action<Vehicule> action) {
        for (Vehicule v : src) action.executer(v);
    }

    public void trierVehicules(List<Vehicule> src, Comparateur<Vehicule> cmp) {
        Collections.sort(src, (a, b) -> cmp.comparer(a, b));
    }
}