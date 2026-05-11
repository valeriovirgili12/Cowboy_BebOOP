package it.unicam.cs.mpgc.rpg126421.model.episode;

import java.util.Optional;

/**
 * Una scelta che il giocatore può compiere durante una scena.
 * Può avere un requisito opzionale e produce sempre un Outcome.
 */
public class Choice {

    private final String text;
    private final Requirement requirement; // null = sempre disponibile
    private final Outcome outcome;
    private final boolean isKeyChoice;    // true = scelta finale pesante

    private Choice(Builder builder) {
        this.text         = builder.text;
        this.requirement  = builder.requirement;
        this.outcome      = builder.outcome;
        this.isKeyChoice  = builder.isKeyChoice;
    }

    public String getText() { return text; }

    public Optional<Requirement> getRequirement() {
        return Optional.ofNullable(requirement);
    }

    public Outcome getOutcome() { return outcome; }

    public boolean isKeyChoice() { return isKeyChoice; }

    /**
     * Restituisce true se la scelta è disponibile nella sessione corrente.
     */
    public boolean isAvailable(it.unicam.cs.mpgc.rpg126421.model.session.GameSession session) {
        return requirement == null || requirement.isMet(session);
    }

    // ── Builder ──────────────────────────────────────────────────────────────

    public static class Builder {
        private final String text;
        private final Outcome outcome;
        private Requirement requirement = null;
        private boolean isKeyChoice     = false;

        public Builder(String text, Outcome outcome) {
            if (text == null || text.isBlank())
                throw new IllegalArgumentException("Choice text cannot be blank");
            this.text    = text;
            this.outcome = outcome;
        }

        public Builder requires(Requirement requirement) {
            this.requirement = requirement;
            return this;
        }

        public Builder keyChoice() {
            this.isKeyChoice = true;
            return this;
        }

        public Choice build() { return new Choice(this); }
    }
}