package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.CrewClassRequirement;
import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.FlagRequirement;
import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.WoolongRequirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.CaptainClass;
import it.unicam.cs.mpgc.rpg126421.model.shared.CrewClass;

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

        // ── Scena 1 — Arrivo a Europa ─────────────────────────────────────────

        Choice investigateStation = new Choice.Builder(
                "Gira per la stazione. Qualcuno sa qualcosa.",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(0)
                        .flag("ep2_investigated", "true")
                        .narrative(
                                "La stazione commerciale di Europa è un posto dove " +
                                        "tutti guardano altrove apposta. " +
                                        "Tre ore di giri a vuoto, poi un nome: Nyx. " +
                                        "Una freelancer. Conosce la Helix Corporation " +
                                        "meglio di quanto convenga."
                        )
                        .build()
        )
                .logEntry("Arrivati a Europa. Il nome Nyx torna più volte.")
                .build();

        Choice hackTerminals = new Choice.Builder(
                "[HACKER] Accedi ai terminali della stazione.",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(0)
                        .flag("ep2_investigated", "true")
                        .flag("ep2_hacked_terminals", "true")
                        .narrative(
                                "I log di transazione della stazione raccontano " +
                                        "una storia interessante. " +
                                        "Pagamenti regolari verso conti anonimi. " +
                                        "Tutti con lo stesso prefisso: HLX. " +
                                        "E un nome ricorrente nei manifesti di carico: Nyx."
                        )
                        .build()
        )
                .requires(new CrewClassRequirement(CaptainClass.HACKER))
                .logEntry("I terminali parlano. Helix è ovunque. E c'è un nome: Nyx.")
                .build();

        Scene scene1 = new Scene(
                "ep2_s1",
                "Europa. Porto commerciale, livello basso.\n\n" +
                        "L'aria sa di ozono bruciato e transazioni illegali. " +
                        "Marcus cammina dietro di te con le mani in tasca, " +
                        "gli occhi che si muovono troppo velocemente per sembrare tranquillo.\n\n" +
                        "\"Helix ha qualcosa qui,\" dice. " +
                        "\"Lo sento. È lo stesso odore di dieci anni fa.\"",
                List.of(investigateStation, hackTerminals)
        );

        // ── Scena 2 — L'incontro con Nyx ─────────────────────────────────────

        Choice talkToNyx = new Choice.Builder(
                "Siediti. Cosa vuoi in cambio?",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(5)
                        .trust("marcus", -5)
                        .flag("ep2_talked_nyx", "true")
                        .narrative(
                                "Nyx non gira intorno alle cose. " +
                                        "Conosce la struttura locale della Helix Corporation. " +
                                        "Sa dove sono i server, chi comanda, come entrarci. " +
                                        "In cambio vuole protezione — qualcuno l'ha messa nel mirino. " +
                                        "Marcus non sembra convinto."
                        )
                        .build()
        )
                .logEntry("Nyx offre informazioni. In cambio vuole protezione.")
                .build();

        Choice interrogateNyx = new Choice.Builder(
                "[GUNSLINGER] Spinala al muro. Chi lavora per chi?",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(-10)
                        .trust("marcus", 5)
                        .flag("ep2_talked_nyx", "true")
                        .flag("ep2_intimidated_nyx", "true")
                        .narrative(
                                "Nyx non si spaventa facilmente. " +
                                        "Ma parla lo stesso — forse perché sa che " +
                                        "non ha molte alternative. " +
                                        "Le informazioni sono le stesse. " +
                                        "La fiducia, meno."
                        )
                        .build()
        )
                .requires(new CrewClassRequirement(CaptainClass.GUNSLINGER))
                .logEntry("Nyx ha parlato. Non di sua spontanea volontà.")
                .build();

        Scene scene2 = new Scene(
                "ep2_s2",
                "Nyx ti aspetta a un tavolo in fondo al bar. " +
                        "Come se sapesse che saresti venuto.\n\n" +
                        "Capelli scuri, giacca consumata, uno sguardo che ha visto " +
                        "troppe cose per fidarsi di qualcuno.\n\n" +
                        "\"So chi siete,\" dice. " +
                        "\"E so cosa state cercando. " +
                        "Possiamo aiutarci a vicenda, o possiamo fare finta " +
                        "di non esserci mai incontrati. " +
                        "A voi la scelta.\"",
                List.of(talkToNyx, interrogateNyx)
        );

        // ── Scena 3 — L'attacco Helix ─────────────────────────────────────────

        Choice fightBack = new Choice.Builder(
                "Tieni la posizione. Non si passa.",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(5)
                        .trust("marcus", 10)
                        .flag("ep2_survived_attack", "true")
                        .narrative(
                                "Gli agenti Helix non si aspettavano resistenza. " +
                                        "O forse sì, e non gliene importava. " +
                                        "In ogni caso, la stazione è di nuovo silenziosa. " +
                                        "Per ora."
                        )
                        .build()
        )
                .logEntry("Tenuta la posizione durante l'attacco Helix.")
                .build();

        Choice escapeWithNyx = new Choice.Builder(
                "Porta Nyx fuori di qui. Prima che arrivino altri.",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(0)
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
        )
                .logEntry("Portata Nyx fuori dalla stazione sotto attacco.")
                .build();

        Scene scene3 = new Scene(
                "ep2_s3",
                "Il suono arriva prima delle persone — " +
                        "scariche elettriche, stivali sul metallo.\n\n" +
                        "Agenti Helix. Almeno quattro. " +
                        "Cercano Nyx, ma a quest'ora non fanno distinzioni.\n\n" +
                        "Marcus: \"Sapevo che sarebbe andata così.\"",
                List.of(fightBack, escapeWithNyx)
        );

        // ── Scena 4 — Reclutare Nyx (scelta chiave) ──────────────────────────

        Choice recruitNyx = new Choice.Builder(
                "Vieni con noi. La Blue Mantis ha posto.",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(5)
                        .trust("marcus", -15)
                        .flag("recruitedNyx", "true")
                        .narrative(
                                "Nyx considera la proposta per un momento. " +
                                        "Poi raccoglie le sue cose — " +
                                        "non sono molte — e sale sulla nave. " +
                                        "Marcus la guarda salire senza commentare. " +
                                        "Il suo silenzio dice tutto."
                        )
                        .build()
        )
                .logEntry("Nyx è sulla Blue Mantis. Marcus non è contento.")
                .keyChoice()
                .build();

        Choice rejectNyx = new Choice.Builder(
                "Ognuno per la sua strada. Non è affar nostro.",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(-5)
                        .trust("marcus", 10)
                        .flag("recruitedNyx", "false")
                        .narrative(
                                "Nyx annuisce come se se lo aspettasse. " +
                                        "\"Se cambiate idea,\" dice, " +
                                        "\"sapete dove trovarmi. " +
                                        "Per un po', almeno.\" " +
                                        "Marcus sembra sollevato. " +
                                        "È una delle rare volte in cui lo vedi così."
                        )
                        .build()
        )
                .logEntry("Nyx lasciata andare. Marcus sembra sollevato.")
                .keyChoice()
                .build();

        Choice recruitNyxIfProtected = new Choice.Builder(
                "Ti abbiamo salvata la vita. Adesso ci devi qualcosa.",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(0)
                        .trust("marcus", -10)
                        .flag("recruitedNyx", "true")
                        .narrative(
                                "Non è un invito — è un fatto. " +
                                        "Nyx lo sa. Sale sulla nave con un'espressione " +
                                        "che non è gratitudine, ma ci somiglia. " +
                                        "Quanto durerà dipenderà da voi."
                        )
                        .build()
        )
                .failsIf(new FlagRequirement("ep2_protected_nyx", "true"))
                .logEntry("Nyx sulla nave — per debito, non per scelta.")
                .keyChoice()
                .build();

        Scene scene4 = new Scene(
                "ep2_s4",
                "La stazione si sta calmando. " +
                        "Nyx è in piedi davanti al portello della Blue Mantis.\n\n" +
                        "Marcus parla piano, solo per te: " +
                        "\"Quella donna sa troppo sulla Helix. " +
                        "Potrebbe essere una risorsa. " +
                        "O un problema. " +
                        "Probabilmente tutte e due le cose.\"\n\n" +
                        "Aaron Morrow — il vecchio partner di Marcus — " +
                        "è sparito mentre indagava sulla Helix Corporation. " +
                        "Nessuno ha mai trovato niente. " +
                        "Forse Nyx sa qualcosa. " +
                        "Forse no.",
                List.of(recruitNyx, rejectNyx, recruitNyxIfProtected)
        );

        return List.of(scene1, scene2, scene3, scene4);
    }

    @Override
    public void initialize(GameSession session) {
        // Logica contestuale:
        // se sparedLena = true → testo scena 1 potrebbe menzionare
        // informazioni passate da Lena sulla struttura di Europa
    }
}