package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Una scelta che il giocatore può compiere durante una scena.
 * Può avere un requisito opzionale e produce sempre un Outcome.
 */
public class Choice {

    private final String text;
    private final Requirement requirement; // null = sempre disponibile
    private final Outcome outcome;
    private final int timeoutSeconds;
    private final String logEntry;
    private final List<Requirement> failConditions;



    private Choice(Builder builder) {
        this.text         = builder.text;
        this.requirement  = builder.requirement;
        this.outcome      = builder.outcome;
        this.timeoutSeconds  = builder.timeoutSeconds;
        this.logEntry        = builder.logEntry;
        this.failConditions = builder.failConditions;

    }

    public String getText() { return text; }

    public Optional<Requirement> getRequirement() {
        return Optional.ofNullable(requirement);
    }

    public Outcome getOutcome() { return outcome; }

    public String getLogEntry() { return logEntry; }

    public boolean hasTimeout()      { return timeoutSeconds > 0; }

    public int getTimeoutSeconds()   { return timeoutSeconds; }

    public List<Requirement>  getFailConditions() { return failConditions; }

    public boolean willFail(GameSession session) {
        return failConditions.stream()
                .anyMatch(r -> !r.isMet(session));
    }
    public Requirement getFirstFailedCondition(GameSession session) {
        return failConditions.stream()
                .filter(r -> !r.isMet(session))
                .findFirst()
                .orElse(null);
    }
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
        public int timeoutSeconds;
        private Requirement requirement = null;
        private boolean isKeyChoice     = false;
        private String logEntry = "";
        private final List<Requirement> failConditions = new ArrayList<>();




        public Builder(String text, Outcome outcome) {
            if (text == null || text.isBlank())
                throw new IllegalArgumentException("Choice text cannot be blank");
            this.text    = text;
            this.outcome = outcome;
        }

        public Builder logEntry(String text) {
            this.logEntry = text;
            return this;
        }

        public Builder requires(Requirement requirement) {
            this.requirement = requirement;
            return this;
        }

        public Builder keyChoice() {
            this.isKeyChoice = true;
            return this;
        }

        public Builder timeout(int seconds) {
            if (seconds <= 0) throw new IllegalArgumentException("Timeout must be positive");
            this.timeoutSeconds = seconds;
            return this;
        }
        public Builder failsIf(Requirement r) {
            failConditions.add(r);
            return this;
        }
        public Choice build() { return new Choice(this); }
    }
}