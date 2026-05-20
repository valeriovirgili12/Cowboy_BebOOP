package it.unicam.cs.mpgc.rpg126421.model.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registro narrativo delle scelte significative del giocatore.
 * Mostrato come riepilogo nella schermata finale.
 */
public class NarrativeLog {

    private final List<String> entries = new ArrayList<>();

    /**
     * Aggiunge una voce al log.
     * @param entry testo descrittivo della scelta fatta
     */
    public void add(String entry) {
        if (entry != null && !entry.isBlank()) {
            entries.add(entry);
        }
    }

    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Restituisce il log come testo unico formattato.
     */
    public String getSummary() {
        if (entries.isEmpty()) return "Nessuna scelta registrata.";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            sb.append(i + 1).append(". ").append(entries.get(i)).append("\n");
        }
        return sb.toString();
    }
}