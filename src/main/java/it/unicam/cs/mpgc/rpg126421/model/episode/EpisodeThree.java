package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.FlagRequirement;
import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.NyxLoyalRequirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

import java.util.List;

/**
 * Episodio 3 — "Last Call Signal"
 * La base nascosta della Helix Corporation.
 * Adrian Kessler. La verità su Aaron Morrow.
 * Quattro finali — sempre visibili, non sempre raggiungibili.
 */
public class EpisodeThree extends Episode {

    public EpisodeThree() {
        super("ep3", "Last Call Signal", buildScenes());
    }

    private static List<Scene> buildScenes() {

        // ── Scena 1 — Ingresso nella base ─────────────────────────────────────

        Choice infiltrate = new Choice.Builder(
                "Entrate in silenzio. Nessun allarme.",
                new Outcome.Builder()
                        .flag("ep3_entry", "stealth")
                        .narrative(
                                "I corridoi sono deserti. " +
                                        "O qualcuno vuole che pensiate questo."
                        )
                        .build()
        )
                .logEntry("Entrati nella base Helix in silenzio.")
                .build();

        Choice forceEntry = new Choice.Builder(
                "Forzate l'ingresso. La sorpresa è tutto.",
                new Outcome.Builder()
                        .morale(-5)
                        .flag("ep3_entry", "force")
                        .narrative(
                                "L'allarme scatta dopo trenta secondi. " +
                                        "Abbastanza per essere dentro. " +
                                        "Non abbastanza per essere al sicuro."
                        )
                        .build()
        )
                .logEntry("Entrati di forza. L'allarme è scattato.")
                .build();

        Choice nyxEntry = new Choice.Builder(
                "[NYX] Nyx disabilita i sistemi di sicurezza.",
                new Outcome.Builder()
                        .morale(5)
                        .trust("marcus", 5)
                        .flag("ep3_entry", "stealth")
                        .flag("ep3_nyx_contributed", "true")
                        .narrative(
                                "Nyx lavora in silenzio per quattro minuti. " +
                                        "\"Siete dentro. Venti minuti prima che " +
                                        "qualcuno si accorga di qualcosa.\" " +
                                        "Marcus la guarda come se stesse rivalutando qualcosa."
                        )
                        .build()
        )
                .requires(new FlagRequirement("recruitedNyx", "true"))
                .logEntry("Nyx ha disabilitato i sistemi Helix. Venti minuti.")
                .build();

        Scene scene1 = new Scene(
                "ep3_s1",
                "La base è nascosta nell'ombra di un gate collassato " +
                        "ai margini del sistema solare.\n\n" +
                        "Nessuna targa. Nessun segnale ufficiale. " +
                        "Solo antenne e silenzio.\n\n" +
                        "Marcus: \"Aaron è arrivato fin qui. " +
                        "Dieci anni fa. Non è mai tornato indietro.\"",
                List.of(infiltrate, forceEntry, nyxEntry)
        );

        // ── Scena 2 — Kessler parla ───────────────────────────────────────────

        Choice listenToKessler = new Choice.Builder(
                "Ascoltalo. Cosa offre esattamente?",
                new Outcome.Builder()
                        .trust("marcus", -5)
                        .flag("ep3_listened_kessler", "true")
                        .narrative(
                                "Kessler parla con la calma di chi ha già vinto troppe volte. " +
                                        "La Helix gestisce il sistema delle taglie da vent'anni. " +
                                        "Crea criminali. Risolve i problemi che ha creato. " +
                                        "Viene pagata due volte. " +
                                        "\"Il sistema funziona,\" dice. " +
                                        "\"Voi funzionate dentro al sistema. " +
                                        "Questo non deve cambiare.\""
                        )
                        .build()
        )
                .logEntry("Kessler ha spiegato tutto. Vent'anni di bugie.")
                .build();

        Choice askAboutAaron = new Choice.Builder(
                "Aaron Morrow. Cosa gli è successo.",
                new Outcome.Builder()
                        .morale(5)
                        .trust("marcus", 20)
                        .flag("ep3_asked_aaron", "true")
                        .narrative(
                                "Per la prima volta qualcosa cambia nell'espressione di Kessler. " +
                                        "Non molto. Abbastanza. " +
                                        "\"Morrow ha fatto una scelta,\" dice. \"Come tutti.\" " +
                                        "Marcus stringe i pugni ma non si muove. " +
                                        "Ci vuole più coraggio a restare fermi che a sparare."
                        )
                        .build()
        )
                .logEntry("Chiesto di Aaron Morrow. Kessler non ha negato niente.")
                .build();

        Scene scene2 = new Scene(
                "ep3_s2",
                "Adrian Kessler vi aspetta nella sala centrale " +
                        "come se foste ospiti attesi.\n\n" +
                        "Capelli grigi, voce piatta, " +
                        "occhi che calcolano tutto quello che guardano.\n\n" +
                        "\"Benvenuti. Ho seguito il vostro percorso con interesse. " +
                        "Siete arrivati fin qui — questo vi rende " +
                        "più svegli della media. O più fortunati.\"\n\n" +
                        "Marcus, piano, solo per te: " +
                        "\"Dieci anni fa ho abbandonato questa indagine per paura. " +
                        "Non lo farò di nuovo. Qualunque cosa tu decida.\"",
                List.of(listenToKessler, askAboutAaron)
        );

        // ── Scena 3 — I quattro finali ────────────────────────────────────────

        // ── FINALE A — Accetta l'accordo (sempre disponibile, sempre funziona)
        Choice acceptDeal = new Choice.Builder(
                "Accetta l'accordo. Lavorate per Helix.",
                new Outcome.Builder()
                        .woolong(15000)
                        .morale(-30)
                        .trust("marcus", -40)
                        .flag("finale", "deal")
                        .narrative(
                                "Kessler sorride per la prima volta. " +
                                        "Un sorriso piccolo, preciso. \"Scelta saggia.\"\n\n" +
                                        "Marcus esce dalla stanza senza guardarti. " +
                                        "La Blue Mantis riparte con il serbatoio pieno " +
                                        "e qualcosa di rotto che non si vede dall'esterno."
                        )
                        .build()
        )
                .logEntry("Accettato l'accordo con Kessler. Quindici mila woolong.")
                .keyChoice()
                .build();

        // ── FINALE B — Consegna Kessler
        // Funziona se Marcus trust alto. Fallisce se basso → tradimento.
        Choice deliverKessler = new Choice.Builder(
                "Consegna Kessler all'ISSP.",
                new Outcome.Builder()
                        .woolong(5000)
                        .morale(20)
                        .trust("marcus", 25)
                        .flag("finale", "delivered")
                        .narrative(
                                "Kessler viene portato via. Calmo, come sempre. " +
                                        "\"Sarò fuori in sei mesi.\" Forse ha ragione. " +
                                        "Marcus lo guarda andare. " +
                                        "\"Almeno ci abbiamo provato.\" " +
                                        "È abbastanza. Per ora."
                        )
                        .onFailure(new Outcome.Builder()
                                .woolong(0)
                                .morale(-20)
                                .trust("marcus", -30)
                                .flag("finale", "betrayed")
                                .gameOver()
                                .narrative(
                                        "Marcus si blocca. " +
                                                "Un secondo. Due. " +
                                                "Kessler lo legge prima che tu possa fare qualcosa.\n\n" +
                                                "\"Lei ha dubbi,\" dice Kessler. " +
                                                "\"Li vedo da qui.\"\n\n" +
                                                "Kessler scappa. Marcus non ti guarda. " +
                                                "Forse non ti guarderà mai più.\n\n" +
                                                "GAME OVER — La fiducia di Marcus non era abbastanza."
                                )
                                .build()
                        )
                        .build()
        )
                .failsIf(new it.unicam.cs.mpgc.rpg126421.model.episode.requirement
                        .MarcusTrustRequirement())
                .logEntry("Kessler consegnato all'ISSP.")
                .keyChoice()
                .build();

        // ── FINALE C — Distruggi i server
        // Richiede Nyx reclutata e leale. Senza → game over.
        Choice destroyServers = new Choice.Builder(
                "Distruggi i server Helix. Brucia tutto.",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(15)
                        .trust("marcus", 10)
                        .flag("finale", "destroyed")
                        .narrative(
                                "Nyx lavora veloce. Troppo veloce " +
                                        "per qualcuno che non l'ha già fatto prima. " +
                                        "I server vanno giù uno a uno. " +
                                        "Nessuna prova. Nessun processo. " +
                                        "Ma la macchina si è fermata. Per quanto, non si sa."
                        )
                        .onFailure(new Outcome.Builder()
                                .woolong(0)
                                .morale(-25)
                                .flag("finale", "failed_destroy")
                                .gameOver()
                                .narrative(
                                        "Non sapete dove colpire. " +
                                                "I sistemi Helix sono troppo complessi " +
                                                "senza qualcuno che li conosca dall'interno.\n\n" +
                                                "La sicurezza vi blocca prima che " +
                                                "arriviate ai server principali.\n\n" +
                                                "GAME OVER — Serviva Nyx, leale e pronta."
                                )
                                .build()
                        )
                        .build()
        )
                .failsIf(new NyxLoyalRequirement())
                .logEntry("Tentata la distruzione dei server Helix.")
                .keyChoice()
                .build();

        // ── FINALE D — Trasmetti tutto (finale raro)
        // Richiede: Lena salvata + Marcus trust alto + Nyx NON reclutata.
        // Senza requisiti → Kessler smonta tutto davanti a te.
        Choice broadcastAll = new Choice.Builder(
                "Trasmetti tutto.Il sistema deve sapere.",
                new Outcome.Builder()
                        .woolong(-2000)
                        .morale(40)
                        .trust("marcus", 35)
                        .flag("finale", "broadcast")
                        .narrative(
                                "Il segnale parte da ogni antenna della base. " +
                                        "I dati di Helix — vent'anni di manipolazioni, " +
                                        "taglie false, persone distrutte — " +
                                        "sono su ogni frequenza del sistema solare. " +
                                        "Non si ferma quello che è già ovunque.\n\n" +
                                        "Marcus guarda lo schermo senza parlare. " +
                                        "Poi, piano: \"Aaron avrebbe voluto vederlo. " +
                                        "Tutto quanto.\"\n\n" +
                                        "Kessler è ancora in piedi dall'altra parte della stanza. " +
                                        "Per la prima volta non ha niente da dire."
                        )
                        .onFailure(new Outcome.Builder()
                                .woolong(0)
                                .morale(-15)
                                .trust("marcus", -10)
                                .flag("finale", "failed_broadcast")
                                .narrative(
                                        "Kessler guarda lo schermo e ride. " +
                                                "Freddo, controllato.\n\n" +
                                                "\"Quelli sono file fabbricati. " +
                                                "Li ho preparati io stesso, " +
                                                "per un'eventualità come questa. " +
                                                "Nemmeno i giornalisti abboccano " +
                                                "più a roba così.\"\n\n" +
                                                "La trasmissione non cambia niente. " +
                                                "Helix sopravvive. " +
                                                "Voi anche — ma è difficile " +
                                                "chiamarla una vittoria."
                                )
                                .build()
                        )
                        .build()
        )
                .failsIf(new it.unicam.cs.mpgc.rpg126421.model.episode.requirement
                        .BroadcastRequirement())
                .logEntry("Tentata la trasmissione dei dati Helix.")
                .keyChoice()
                .build();

        Scene scene3 = new Scene(
                "ep3_s3",
                "Kessler aspetta la tua risposta.\n\n" +
                        "Marcus aspetta la tua risposta.\n\n" +
                        "Forse anche tu stai aspettando qualcosa — " +
                        "una certezza, un segno, una ragione abbastanza buona " +
                        "da sembrare giusta.\n\n" +
                        "Non arriverà. " +
                        "Le ragioni buone non arrivano mai prima delle scelte difficili. " +
                        "Solo dopo. Se va bene.",
                List.of(acceptDeal, deliverKessler, destroyServers, broadcastAll)
        );

        return List.of(scene1, scene2, scene3);
    }

    @Override
    public void initialize(GameSession session) {
        // Logica contestuale futura
    }
}