package it.unicam.cs.mpgc.rpg126421.model.episode.narrative;

import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

public class Ep3Script {

    private final GameSession session;

    public Ep3Script(GameSession session) {
        System.out.println("SESSION HASH = " + session.hashCode());
        this.session = session;
    }

    // ─────────────────────────────────────────────
    // FLAGS
    // ─────────────────────────────────────────────

    public boolean nyxPresent() {
        return "true".equals(session.getWorldState().getFlag("ep2_recruited_nyx"));
    }

    // ─────────────────────────────────────────────
    // NYX LINES
    // ─────────────────────────────────────────────

    private String nyxLineS1() {
        return nyxPresent()
                ? "Nyx controlla i sistemi in silenzio.\n" +
                "Non dice niente. Non ne ha bisogno.\n\n"
                : "Siete in due. Come sempre.\n\n";
    }

    private String nyxLineS2() {
        return nyxPresent()
                ? "Nyx... \"dov'è andata quella?\"\n" +
                "\"Ha un piano\" dice Marcus\n" +
                "\"Come un piano? Ma possibile che sono sempre l'ultimo a sapere le-\"\n" +
                "Mentre stai parlando, la porta si apre da sola.\n"
                : "\"Finalmente ci siamo.\"\n" +
                "\"comunque andrà, sappi che-\"\n" +
                "Mentre stai parlando, la porta si apre.\n";
    }

    // ─────────────────────────────────────────────
    // EP3 S0 BRIDGE
    // ─────────────────────────────────────────────

    public String scene0() {
        return
                "BASE HELIX — GATE COLLASSATO, MARGINI DEL SISTEMA SOLARE\n\n" +
                        "Nessuna targa. Nessun segnale ufficiale.\n" +
                        "Solo antenne e silenzio e il rumore della Blue Mantis\n" +
                        "che attracca in un posto dove non dovrebbe essere.\n\n" +
                        "Marcus è in piedi dietro di te.\n" +
                        "\"Aaron è arrivato fin qui,\" dice.\n" +
                        "\"Dieci anni fa. Non è mai tornato indietro.\n" +
                        "Io mi sono fermato prima. Per paura.\"\n" +
                        "Una pausa. \"Non lo farò di nuovo.\"\n\n" +
                        nyxLineS1() +
                        "Adrian Kessler vi aspetta dentro.\n" +
                        "Lo sa già che siete qui.\n" +
                        "Probabilmente lo sapeva prima di voi.";
    }

    // ─────────────────────────────────────────────
    // EP3 S1 BRIDGE
    // ─────────────────────────────────────────────

    public String scene1Context() {
        System.out.println("NYX FLAG = " +
                session.getWorldState().getFlag("ep2_recruited_nyx"));
        return
                "Vi fate strada nei corridoi, ancora nessuno.\n" +
                        "All'improvviso una porta con su scritto:\n[SALA DI COMANDO]...\n" +
                        "Siamo arrivati - dici\n" +
                        "Marcus non parla.\n\n" +

                        nyxLineS2() +
                        "\"Entrate!\" - sentite provenire da dentro\n" +
                        "Vi fate coraggio, fate due passi e..\n\n";
    }
    public String getContext(String sceneId) {

        if (!requiresContext(sceneId)) {
            return "";
        }

        return scene1Context();
    }
    public boolean requiresContext(String sceneId) {
        return "ep3_s1".equals(sceneId); //per ora
    }
}