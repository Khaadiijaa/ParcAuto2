package sn.khadija.l2gl.app.app;

import java.time.LocalDate;
import java.util.*;
import sn.khadija.l2gl.app.model.*;
import sn.khadija.l2gl.app.repo.*;
import sn.khadija.l2gl.app.service.*;

public class Main {

    public static void main(String[] args) {

        ParcAutoService service = new ParcAutoService();

        // ===== ÉTAPES 6-7-8 =====
        Vehicule v1 = new Vehicule(1L, "AA123", 50000, 2018);
        Vehicule v2 = new Vehicule(2L, "BB456", 120000, 2010);
        Vehicule v3 = new Vehicule(3L, "CC789", 80000, 2015);

        service.ajouterVehicule(v1);
        service.ajouterVehicule(v2);
        service.ajouterVehicule(v3);

        System.out.println("=== ÉTAPE 6 ===");
        System.out.println("Recherche AA123 : " + service.rechercher("AA123").getImmat());

        System.out.println("\n=== ÉTAPE 7 ===");
        System.out.println("Uniques : " + service.vehiculesUniques().size());
        Set<Vehicule> testDoublon = new HashSet<>();
        testDoublon.add(v1);
        testDoublon.add(v1);
        System.out.println("Set avec doublon (attendu 1) : " + testDoublon.size());

        System.out.println("\n=== ÉTAPE 8 ===");
        Entretien e1 = new Entretien(1L, v1, LocalDate.of(2024,1,10), "Vidange", 15000);
        Entretien e2 = new Entretien(2L, v1, LocalDate.of(2024,6,20), "Pneus", 40000);
        Entretien e3 = new Entretien(3L, v2, LocalDate.of(2023,11,5), "Freins", 25000);
        service.ajouterEntretien(e1);
        service.ajouterEntretien(e2);
        service.ajouterEntretien(e3);
        System.out.println("Entretiens AA123 : " + service.getEntretiens("AA123").size());
        System.out.println("Entretiens CC789 (vide) : " + service.getEntretiens("CC789").size());

        // ===== ÉTAPE 9 =====
        System.out.println("\n=== ÉTAPE 9 ===");
        service.vehiculesDisponibles().forEach(v -> System.out.println("  Dispo: " + v.getImmat()));
        System.out.println("Triées : " + service.immatriculationsTriees());
        service.top3Kilometrage().forEach(v -> System.out.println("  Top: " + v.getImmat() + " " + v.getKm()));

        // ===== ÉTAPE 10 =====
        System.out.println("\n=== ÉTAPE 10 ===");
        System.out.println("Km moyen : " + service.kilometrageMoyen());
        System.out.println("Par état : " + service.nombreParEtat());
        System.out.println("Coûts : " + service.coutTotalEntretiensParVehicule());

        // ===== ÉTAPE 11 : afficher() =====
        System.out.println("\n=== ÉTAPE 11 ===");
        List<Entite> entites = new ArrayList<>();
        entites.add(v1);
        entites.add(v2);
        entites.add(e1);
        entites.forEach(e -> System.out.println(e.afficher()));

        // ===== ÉTAPE 12 : CRUD =====
        System.out.println("\n=== ÉTAPE 12 ===");
        InMemoryCrud<Vehicule> crud = new InMemoryCrud<>();
        crud.create(v1);
        crud.create(v2);
        crud.create(v3);
        System.out.println("Tous : " + crud.findAll().size());
        crud.delete(2L);
        System.out.println("Après suppression id=2 : " + crud.findAll().size());

        // ===== ÉTAPE 13 : Optional =====
        System.out.println("\n=== ÉTAPE 13 ===");
        crud.read(1L).ifPresent(v -> System.out.println("Trouvé : " + v.afficher()));
        System.out.println("Id 99 : " + crud.read(99L).orElse(null));
        Vehicule vDefaut = crud.read(99L).orElse(new Vehicule(99L, "INCONNU", 0, 2000));
        System.out.println("orElse : " + vDefaut.getImmat());
        try {
            crud.read(99L).orElseThrow(() ->
                    new RuntimeException("Véhicule introuvable id=99"));
        } catch (RuntimeException ex) {
            System.out.println("orElseThrow : " + ex.getMessage());
        }

        // ===== ÉTAPE 14 : record rapport =====
        System.out.println("\n=== ÉTAPE 14 ===");
        service.genererRapport().forEach(r -> System.out.println(r.afficher()));

        // ===== ÉTAPE 15 : Location + scénario final =====
        System.out.println("\n=== ÉTAPE 15 ===");
        Conducteur c1 = new Conducteur(1L, "Amadou Diallo", "B1234");
        Location loc1 = new Location(1L, v1, c1, LocalDate.of(2024, 3, 1), 5000);
        service.demarrerLocation(loc1);
        System.out.println("État v1 après location : " + v1.getEtat());
        service.terminerLocation(1L, LocalDate.of(2024, 3, 10));
        System.out.println("État v1 après fin : " + v1.getEtat());
        System.out.println("Durée location : " + loc1.dureeJours() + " jours");

        System.out.println("\nVéhicules à réviser (km>70000 ou année<2016) :");
        service.vehiculesAReviser(70000, 2016)
                .forEach(v -> System.out.println("  " + v.afficher()));

        System.out.println("\nRapport final :");
        service.genererRapport().forEach(r -> System.out.println(r.afficher()));
    }
}