package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

import java.util.ArrayList;
import java.util.Collections;
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
    private final List<Outcome> failureOutcomes;


    private Choice(Builder builder) {
        this.text         = builder.text;
        this.requirement  = builder.requirement;
        this.outcome      = builder.outcome;
        this.timeoutSeconds  = builder.timeoutSeconds;
        this.logEntry        = builder.logEntry;
        this.failConditions  = Collections.unmodifiableList(builder.failConditions);
        this.failureOutcomes = Collections.unmodifiableList(builder.failureOutcomes);}

    public String getText() { return text; }

    public Optional<Requirement> getRequirement() {
        return Optional.ofNullable(requirement);
    }

    public Outcome getOutcome() { return outcome; }

    public String getLogEntry() { return logEntry; }

    public boolean hasTimeout()      { return timeoutSeconds > 0; }

    public int getTimeoutSeconds()   { return timeoutSeconds; }

    public Outcome getFailureOutcome(GameSession session) {
        for (int i = 0; i < failConditions.size(); i++) {
            if (!failConditions.get(i).isMet(session)) {
                return failureOutcomes.get(i);
            }
        }
        return null;
    }
    public boolean willFail(GameSession session) {
        return failConditions.stream()
                .anyMatch(r -> !r.isMet(session));
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
        private String logEntry = "";
        private final List<Requirement> failConditions = new ArrayList<>();
        private final List<Outcome> failureOutcomes = new ArrayList<>();  // aggiungi




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


        public Builder timeout(int seconds) {
            if (seconds <= 0) throw new IllegalArgumentException("Timeout must be positive");
            this.timeoutSeconds = seconds;
            return this;
        }
        public Builder failsIf(Requirement r, Outcome failureOutcome) {
            failConditions.add(r);
            failureOutcomes.add(failureOutcome);
            return this;
        }
        public Choice build() { return new Choice(this); }
    }
}