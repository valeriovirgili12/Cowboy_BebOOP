package it.unicam.cs.mpgc.rpg126421.model.episode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Conseguenze di una scelta del giocatore.
 * Modifica woolong, morale, trust dei crew member e flag di mondo.
 */
public class Outcome {

    private final int woolongDelta;
    private final int moraleDelta;
    private final Map<String, Integer> trustDeltas;
    private final Map<String, String> flagsToSet;
    private final String narrativeText;
    private final boolean causesGameOver;

    private Outcome(Builder builder) {
        this.woolongDelta  = builder.woolongDelta;
        this.moraleDelta   = builder.moraleDelta;
        this.trustDeltas   = Collections.unmodifiableMap(builder.trustDeltas);
        this.flagsToSet    = Collections.unmodifiableMap(builder.flagsToSet);
        this.narrativeText = builder.narrativeText;
        this.causesGameOver  = builder.causesGameOver;

    }

    public int getWoolongDelta()                { return woolongDelta; }
    public int getMoraleDelta()                 { return moraleDelta; }
    public Map<String, Integer> getTrustDeltas(){ return trustDeltas; }
    public Map<String, String> getFlagsToSet()  { return flagsToSet; }
    public String getNarrativeText()            { return narrativeText; }
    public boolean causesGameOver() { return causesGameOver; }

    // ── Builder ──────────────────────────────────────────────────────────────

    public static class Builder {
        private int woolongDelta  = 0;
        private int moraleDelta   = 0;
        private final Map<String, Integer> trustDeltas = new HashMap<>();
        private final Map<String, String>  flagsToSet  = new HashMap<>();
        private String narrativeText = "";
        private boolean causesGameOver = false;

        public Builder woolong(int delta)               { this.woolongDelta = delta; return this; }
        public Builder morale(int delta)                { this.moraleDelta  = delta; return this; }
        public Builder trust(String memberName, int d)  { trustDeltas.put(memberName, d); return this; }
        public Builder flag(String key, String value)   { flagsToSet.put(key, value); return this; }
        public Builder narrative(String text)           { this.narrativeText = text; return this; }
        public Builder gameOver()                       { this.causesGameOver = true; return this; }
        public Outcome build() { return new Outcome(this); }
    }
}