package sn.khadija.l2gl.app.model;

public abstract class Entite {

    private final Long id;

    protected Entite(Long id) {
        if (id == null) throw new IllegalArgumentException("L'id ne peut pas être null");
        this.id = id;
    }

    public final Long getId() { return id; }

    public abstract String afficher();
}