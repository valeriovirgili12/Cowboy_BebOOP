package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.CrewClassRequirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.CaptainClass;
import it.unicam.cs.mpgc.rpg126421.util.Sprites;

import java.util.List;

/**
 * Episodio 2 — "Neon Rain"
 * Europa. Una stazione commerciale che puzza di accordi sotto banco.
 * Nyx è qui. Anche la Helix Corporation.
 */
public class EpisodeTwo extends Episode {

    public EpisodeTwo() {
        super("ep2", "Neon Rain", buildScenes());
    }

    private static List<Scene> buildScenes() {

        // ── Scena 0 — Intro ───────────────────────────────────────────────────
        Scene scene0 = Scene.intro(
                "ep2_s0",
                "BLUE MANTIS — ROTTA PER EUROPA\n\n" +
                        "Quello che è successo su Io pesa ancora.\n" +
                        "Marcus non ha detto molto da quando avete lasciato la stazione.\n\n" +
                        "Il nome Helix Corporation è comparso nei dati di Lena.\n" +
                        "Un'azienda che gestisce sicurezza, taglie, intelligenza artificiale.\n" +
                        "Almeno in superficie.\n\n" +
                        "Marcus conosce quel nome.\n" +
                        "Dieci anni fa stava indagando su di loro\n" +
                        "quando il suo partner — Aaron Morrow — è sparito.\n" +
                        "Nessuna traccia. Nessuna spiegazione.\n" +
                        "Marcus ha smesso di cercare. Non sa ancora perché.\n\n" +
                        "Europa. Porto commerciale, livello basso.\n" +
                        "Qualcuno qui sa qualcosa.\n" +
                        "Lo sentite entrambi.",
                Sprites.MARCUS,
                Sprites.BG_EP2
        );

        // ── Scena 1 — Arrivo a Europa ─────────────────────────────────────────
        Choice investigateStation = new Choice.Builder(
                "Gira per la stazione. Qualcuno saprà qualcosa.",
                new Outcome.Builder()
                        .flag("ep2_investigated", "true")
                        .narrative(
                                "La stazione commerciale di Europa è un posto dove " +
                                        "tutti guardano altrove apposta. " +
                                        "Tre ore di giri a vuoto, poi un nome: Nyx. " +
                                        "Una freelancer. Conosce la Helix Corporation " +
                                        "meglio di quanto convenga."
                        )
                        .build()
        ).logEntry("Arrivati a Europa. Il nome Nyx torna più volte.")
                .build();

        Choice hackTerminals = new Choice.Builder(
                "[HACKER] Accedi ai terminali della stazione.",
                new Outcome.Builder()
                        .flag("ep2_investigated", "true")
                        .flag("ep2_hacked_terminals", "true")
                        .narrative(
                                "I log di transazione della stazione raccontano " +
                                        "una storia interessante. " +
                                        "Pagamenti regolari verso conti anonimi. " +
                                        "Tutti con lo stesso prefisso: HLX. " +
                                        "E un nome ricorrente nei manifesti di carico: Kessler."
                        )
                        .build()
        ).requires(new CrewClassRequirement(CaptainClass.HACKER))
                .logEntry("I terminali parlano. Helix è ovunque. E c'è un nome: Nyx.")
                .build();

        Scene scene1 = Scene.withSprites(
                "ep2_s1",
                "L'aria sa di ozono bruciato e transazioni illegali. " +
                        "Marcus cammina dietro di te con le mani in tasca, " +
                        "gli occhi che si muovono troppo velocemente per sembrare tranquillo.\n\n" +
                        "\"Helix ha qualcosa qui,\" dice. " +
                        "\"Lo sento. È lo stesso odore di dieci anni fa.\"",
                List.of(investigateStation, hackTerminals),
                null,
                Sprites.BG_EP2
        );

        // ── Scena 2 — L'incontro con Nyx ─────────────────────────────────────
        Choice talkToNyx = new Choice.Builder(
                "Siediti. Cosa vuole in cambio?",
                new Outcome.Builder()
                        .morale(5)
                        .trust("marcus", -10)
                        .flag("ep2_talked_nyx", "true")
                        .narrative(
                                "Nyx non gira intorno alle cose. " +
                                        "Conosce la struttura locale della Helix Corporation. " +
                                        "Sa dove sono i server, chi comanda, come entrarci. " +
                                        "In cambio vuole protezione: qualcuno l'ha messa nel mirino. " +
                                        "Marcus non sembra convinto."
                        )
                        .build()
        ).logEntry("Nyx offre informazioni. In cambio vuole protezione.")
                .build();

        Choice interrogateNyx = new Choice.Builder(
                "Taglia corto. Non hai tempo per i giochi",
                new Outcome.Builder()
                        .morale(-10)
                        .trust("marcus", 5)
                        .flag("ep2_talked_nyx", "true")
                        .flag("ep2_intimidated_nyx", "true")
                        .narrative(
                                "Nyx non si spaventa facilmente ma," +
                                        " una volta contro il muro, le conviene parlare. \n" +
                                        "Forse perché sa che non ha molte alternative. " +
                                        "Le informazioni sono le stesse. " +
                                        "La fiducia, meno."
                        )
                        .build()
        ).logEntry("Nyx ha parlato. Non di sua spontanea volontà.")
                .build();

        Scene scene2 = Scene.withSprites(
                "ep2_s2",
                "Nyx vi aspetta a un tavolo in fondo al bar. " +
                        "Come se sapesse che sareste arrivati.\n\n" +
                        "Acconciatura quantomeno particolare, felpa consumata, sguardo innocente.\n\n" +
                        "\"So chi siete,\" dice. " +
                        "\"E so cosa state cercando. " +
                        "Possiamo aiutarci a vicenda, o possiamo fare finta " +
                        "di non esserci mai incontrati. " +
                        "A voi la scelta.\"",
                List.of(talkToNyx, interrogateNyx),
                Sprites.NYX,
                Sprites.BG_EP2
        );

        // ── Scena 3 — L'attacco Helix ─────────────────────────────────────────
        Choice fightBack = new Choice.Builder(
                "[GUNSLINGER] Tieni la posizione. Non si passa.",
                new Outcome.Builder()
                        .morale(10)
                        .trust("marcus", 5)
                        .flag("ep2_survived_attack", "true")
                        .flag("ep2_protected_nyx", "true")
                        .narrative(
                                "Chi spara per primo spara due volte, diceva qualcuno. " +
                                        "Hai sempre avuto il grilletto facile.\n" +
                                        "Gli agenti Helix a terra: sono venuti per voi o per lei?\n" +
                                        "Ovviamente non intendono rispondere.\n" +
                                        "\"Marcus\": è ora di tornare.\n" +
                                        "Annuisci."
                        )
                        .build()
        ).requires(new CrewClassRequirement(CaptainClass.GUNSLINGER))
                .logEntry("Tenuta la posizione durante l'attacco Helix.")
                .build();

        Choice escapeWithNyx = new Choice.Builder(
                "Porta Nyx fuori di qui, prima che ne arrivino altri.",
                new Outcome.Builder()
                        .trust("marcus", -5)
                        .flag("ep2_survived_attack", "true")
                        .flag("ep2_protected_nyx", "true")
                        .narrative(
                                "Vi dileguate nei corridoi di servizio " +
                                        "prima che la situazione peggiori. " +
                                        "Nyx conosce ogni uscita di questa stazione. " +
                                        "Utile. Sospetto. " +
                                        "Marcus non dice nulla, ma lo pensa."
                        )
                        .build()
        ).logEntry("Portata Nyx fuori dalla stazione sotto attacco.")
                .build();

        Scene scene3 = Scene.withSprites(
                "ep2_s3",
                "All'improvviso senti dei passi in lontananza: " +
                        "scariche elettriche, stivali sul metallo.\n\n" +
                        "Agenti Helix. Almeno quattro. " +
                        "Cercano Nyx, ma a quest'ora non fanno distinzioni.\n\n" +
                        "Marcus: \"Sapevo che sarebbe andata così.\"\n\n" +
                        "Nyx: \"Sono arrivati.\"",
                List.of(fightBack, escapeWithNyx),
                null,
                Sprites.BG_EP2
        );

        // ── Scena 4 — Reclutare Nyx (scelta chiave) ──────────────────────────
        Choice recruitNyx = new Choice.Builder(
                "Vieni con noi. La Blue Mantis ha posto.",
                new Outcome.Builder()
                        .morale(5)
                        .trust("marcus", -15)
                        .flag("ep2_recruited_nyx", "true")
                        .narrative(
                                "Nyx considera la proposta per un momento. " +
                                        "Poi raccoglie le sue cose — " +
                                        "non sono molte — e sale sulla nave. " +
                                        "Marcus la guarda salire senza commentare. " +
                                        "Il suo silenzio dice tutto."
                        )
                        .build()
        ).logEntry("Nyx è sulla Blue Mantis. Marcus non è contento.")
                .keyChoice()
                .build();

        Choice rejectNyx = new Choice.Builder(
                "Non ci servi sulla nave. Ci servi fuori.",
                new Outcome.Builder()
                        .morale(-5)
                        .trust("marcus", 10)
                        .flag("ep2_recruited_nyx", "false")
                        .narrative(
                                "Nyx annuisce come se se lo aspettasse. " +
                                        "\"Vi devo un favore,\" dice, " +
                                        "\"sapete dove trovarmi.\"\n" +
                                        "Marcus sembra sollevato. " +
                                        "È una delle rare volte in cui lo vedi così."
                        )
                        .build()
        ).logEntry("Nyx lasciata andare. Marcus sembra sollevato.")
                .keyChoice()
                .build();

        Scene scene4 = Scene.withSprites(
                "ep2_s4",
                "La stazione si sta calmando. " +
                        "Nyx è in piedi davanti al portello della Blue Mantis.\n\n" +
                        "Marcus ti dice all'orecchio: " +
                        "\"Quella donna sa troppo sulla Helix. " +
                        "Potrebbe essere una risorsa. " +
                        "Sicuramente sarà un problema.\n" +
                        "Che facciamo?\"\n\n" +
                        "All'improvviso ricordi: Aaron Morrow — il vecchio partner di Marcus — " +
                        "è sparito mentre indagava sulla Helix Corporation. " +
                        "Nessuno ha mai trovato niente. " +
                        "Nyx sa qualcosa, e ti deve un favore.",
                List.of(recruitNyx, rejectNyx),
                Sprites.NYX,
                Sprites.BG_EP2
        );

        return List.of(scene0, scene1, scene2, scene3, scene4);
    }

    @Override
    public void initialize(GameSession session) {
        // no-op
    }
}