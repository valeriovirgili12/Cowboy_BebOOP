package it.unicam.cs.mpgc.rpg126421.model.character;

import it.unicam.cs.mpgc.rpg126421.model.shared.CharacterRole;

/**
 * Compagno permanente — sempre presente, non reclutabile né licenziabile.
 * Ha un livello di fiducia verso il capitano che influenza i finali.
 * Diverso da CrewMember: non fa parte del roster opzionale.
 */
public abstract class PermanentCompanion extends Character {

    private static final int MAX_TRUST     = 100;
    private static final int MIN_TRUST     = 0;
    private static final int STARTING_TRUST = 50;

    private int trust;

    protected PermanentCompanion(String name, CharacterRole role) {
        super(name, role);
        this.trust = STARTING_TRUST;
    }

    public int getTrust() { return trust; }

    public void changeTrust(int delta) {
        this.trust = Math.clamp(this.trust + delta, MIN_TRUST, MAX_TRUST);
    }

    public boolean isTrustHigh() { return trust >= 60; }
    public boolean isTrustLow()  { return trust < 40; }

    /**
     * Reazione narrativa del compagno alla scelta appena fatta.
     * Restituisce una battuta contestuale, o stringa vuota se silenzioso.
     */
    public abstract String reactToChoice(String choiceFlag);

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{name='" + getName() + "', trust=" + trust + "}";
    }
}