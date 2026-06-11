package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.episode.narrative.Ep3Script;
import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.*;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.util.Sprites;

import java.util.List;

/**
 * Episodio 3 — "Last Call Signal"
 * La base nascosta della Helix Corporation.
 * Adrian Kessler. La verità su Aaron Morrow.
 * Quattro finali — A e B sempre visibili, C e D mutuamente esclusivi.
 */
public class EpisodeThree extends Episode {

    public EpisodeThree(Ep3Script script) {
        super("ep3", "Last Call Signal", buildScenes(script));

    }

    private static List<Scene> buildScenes(Ep3Script script) {

        // ── Scena 0 — Intro (testo sovrascritto da initialize()) ──────────────
        Scene scene0 = Scene.withSprites(
                "ep3_s0",
                "",
                List.of(new Choice.Builder("[ CONTINUA ]",
                        new Outcome.Builder().build()).build()),
                Sprites.MARCUS,
                Sprites.BG_EP3
        );

        // ── Scena 1 — Ingresso nella base ─────────────────────────────────────
        Choice infiltrate = new Choice.Builder(
                "Entrate in silenzio. Nessun allarme.",
                new Outcome.Builder()
                        .flag("ep3_entry", "stealth")
                        .narrative(
                                "Scelta meno rischiosa - pensi\n"
                        )
                        .build()
        ).logEntry("Entrati nella base Helix in silenzio.")
                .build();

        Choice forceEntry = new Choice.Builder(
                "Forzate l'ingresso. La sorpresa è tutto.",
                new Outcome.Builder()
                        .morale(-5)
                        .flag("ep3_entry", "force")
                        .narrative(
                                "\"Se dobbiamo fare le cose, facciamole per bene\" - dici, " +
                                        "prima di sparare alla porta.\n" +
                                        "Ma quale effetto sorpresa? Sanno che stiamo arrivando - pensi.\n"
                        )
                        .build()
        ).logEntry("Entrati di forza. L'allarme è scattato.")
                .build();

        Choice nyxEntry = new Choice.Builder(
                "[NYX] Nyx disabilita i sistemi di sicurezza.",
                new Outcome.Builder()
                        .morale(5)
                        .trust("marcus", 5)
                        .flag("ep3_entry", "stealth")
                        .flag("ep3_nyx_contributed", "true")
                        .narrative(
                                "Questa ragazza sa il fatto suo - pensi\n" +
                                        "Forse avrebbe fatto comodo come appoggio fuori di qui... " +
                                        "ma ormai è tardi, il dado è tratto.\n"

                        )
                        .build()
        ).requires(new FlagRequirement("ep2_recruited_nyx", "true"))
                .logEntry("Nyx ha disabilitato i sistemi Helix.")
                .build();

        Scene scene1 = Scene.withSprites(
                "ep3_s1",
                "Arrivati all'ingresso,  c'è troppo silenzio.\n" +
                        "In giro non c'è un'anima.\n\n" +
                        "Che Kessler vi stia aspettando?",
                List.of(infiltrate, forceEntry, nyxEntry),
                null,
                Sprites.BG_EP3
        );

        // ── Scena 2 — Kessler parla (testo sovrascritto da initialize()) ──────
        Choice listenToKessler = new Choice.Builder(
                "Ascoltalo. Cosa offre esattamente?",
                new Outcome.Builder()
                        .trust("marcus", -5)
                        .flag("ep3_listened_kessler", "true")
                        .narrative(
                                "\"50.000 Woolong\" - esclama\n" +
                                        "\"Più uno stipendio fisso, per ogni lavoro che farete per me\"\n" +
                                        "Rimanete impassibili, perché vuole offrirvi un accordo?\n" +
                                        "Siete davvero così scomodi?\n" +
                                        "\"La Helix gestisce il sistema delle taglie da vent'anni - continua\"\n" +
                                        "\"Noi plasmiamo i criminali, pubblichiamo la taglia, passiamo a riscuotere.\n" +
                                        "Questo sistema non può fallire, non può fermarsi.\n" +
                                        "Non combattete una guerra che non potete vincere.\n" +
                                        "Conosco le vostre abilità, sarete più utili da questa parte.\""
                        )
                        .build()
        ).logEntry("Kessler ha spiegato tutto. Vent'anni di bugie.")
                .build();

        Choice askAboutAaron = new Choice.Builder(
                "Aaron Morrow. Cosa gli è successo?",
                new Outcome.Builder()
                        .morale(5)
                        .trust("marcus", 20)
                        .flag("ep3_asked_aaron", "true")
                        .narrative(
                                "Kessler non si aspettava questa domanda.\n" +
                                        "Riflette per un po', poi esclama: " +
                                        "\"Morrow ha fatto una scelta!\"\n" +
                                        "\"È andato oltre le sue competenze... " +
                                        "ha ficcato il naso nei posti sbagliati, ed ha avuto la fine che meritava\"\n" +
                                        "Ti giri, vedi Marcus: la sua espressione è cambiata, non dici niente\n" +
                                        "\"Offro 50.000 mila Woolong\" - continua Kessler\n" +
                                        "\"Lasciate stare questa storia, non sapete neanche a cosa andate incontro.\n" +
                                        "Gestiamo il sistema delle taglie da vent'anni, " +
                                        "non sarà un branco di mocciosi a farci fermare.\""
                        )
                        .build()
        ).logEntry("Chiesto di Aaron Morrow. Kessler non ha negato niente.")
                .build();

        Scene scene2 = Scene.withSprites(
                "ep3_s2",
                "ep3_s2_placeholder",
                List.of(listenToKessler, askAboutAaron),
                Sprites.KESSLER,
                Sprites.BG_EP3_1
        );

        // ── Scena 3 — I quattro finali ────────────────────────────────────────

        Choice acceptDeal = new Choice.Builder(
                "Accetta l'accordo. Lavorate per Helix.",
                new Outcome.Builder()
                        .woolong(15000)
                        .morale(-30)
                        .trust("marcus", -40)
                        .flag("ep3_finale", "deal")
                        .narrative(
                                "\"Accettiamo\" - dici, quasi a bassa voce, quasi te ne vergogni.\n" +
                                        "Sono tanti soldi, pensi. Cibo, carburante... nella vita, bisogna scendere a compromessi.\n" +
                                        "\"Sapevo avresti scelto così, addio\"\n" +
                                        "Realizzi, ti giri, Marcus è nel corridoio\n" +
                                        "\"Il mondo gira per soldi e per amore\" - dice Kessler, mentre ride di gusto\n" +
                                        "\"Suvvia, te ne farai una ragione\"\n" +
                                        "In questo universo, i soldi sono tutto - pensi, per consolarti.\n" +
                                        "Hai perso un compagno fidato.\n" +
                                        "Ne è valsa davvero la pena?"
                        )
                        .build()
        ).logEntry("Accettato l'accordo con Kessler.")
                .keyChoice()
                .build();

        Choice killKessler = new Choice.Builder(
                "Uccidi Kessler. Finisce qui.",
                new Outcome.Builder()
                        .morale(10)
                        .trust("marcus", 20)
                        .flag("ep3_finale", "killed")
                        .narrative(
                                "Quando li riapri esclami: " +
                                        "\"Sai una cosa, Kessler? No? Nemmeno io.\"\n" +
                                        "Estrai la pistola\n" +
                                        "Premi il grilletto.\n" +
                                        "Il colpo interrompe il suo sorriso a metà.\n" +
                                        "Kessler crolla all'indietro come una marionetta a cui hanno tagliato i fili.\n" +
                                        "\"Non cambi mai.\" - dice Marcus mentre è già in movimento.\n" +
                                        "Le guardie reagiscono, lui le tiene occupate quel tanto che basta.\n" +
                                        "Correte tra allarmi, fumo e luci rosse intermittenti.\n" +
                                        "Per una volta, la fortuna decide di offrirvi un passaggio gratuito.\n" +
                                        "Quando l'aria della notte vi colpisce il volto, siete ancora vivi.\n" +
                                        "Non è una vittoria pulita. " +
                                        "Ma nell'universo nessuno regala finali puliti."
                        )
                        .onFailure(new Outcome.Builder()
                                .morale(-30)
                                .flag("ep3_finale", "failed_kill_nopistol")
                                .gameOver()
                                .narrative(
                                        "Quando li riapri esclami: " +
                                                "\"Sai una cosa, Kessler? No? Nemmeno io.\"\n" +
                                                "Porti la mano alla fondina.\n" +
                                                "Vuota. Certo.\n" +
                                                "Non hai mai comprato quella pistola.\n" +
                                                "Per un istante il tempo si ferma.\n" +
                                                "Marcus capisce immediatamente.\n" +
                                                "\"Aspetta!\"\n" +
                                                "Si lancia verso di te senza pensarci due volte.\n" +
                                                "Le guardie invece pensano eccome. E sparano.\n" +
                                                "Il rumore dei colpi copre tutto il resto. " +
                                                "Marcus ti spinge via dalla traiettoria.\n" +
                                                "Uno, due, tre impatti.\n" +
                                                "Troppi.\n" +
                                                "\"Ehi... capo...\"\n" +
                                                "Sorride, come se volesse alleggerire la situazione.\n" +
                                                "\"La prossima volta compra l'equipaggiamento completo.\"\n\n" +
                                                "Poi non dice più niente."
                                )
                                .build()
                        )
                        .onFailure2(new Outcome.Builder()
                                .morale(-30)
                                .flag("ep3_finale", "failed_kill_notrust")
                                .gameOver()
                                .narrative(
                                        "Kessler sorride.\n" +
                                                "Quel sorriso da uomo convinto che tutti abbiano un prezzo.\n" +
                                                "Stringi la mano sulla pistola. È il momento.\n" +
                                                "La estrai e la punti verso di lui.\n" +
                                                "Poi, con la coda dell'occhio, vedi Marcus.\n" +
                                                "Non è sorpreso. Non è spaventato. È deluso.\n" +
                                                "Una stanchezza silenziosa gli attraversa lo sguardo.\n" +
                                                "Come se avesse appena capito chi sei davvero.\n" +
                                                "\"Marcus io...\" - dici\n" +
                                                "Troppo tardi. Lui non si muove.\n" +
                                                "Non ti coprirà. Non questa volta.\n" +
                                                "Le guardie reagiscono prima che tu possa fare qualunque altra cosa.\n" +
                                                "Il mondo diventa un lampo bianco.\n" +
                                                "Poi solo buio.\n" +
                                                "Da qualche parte, molto lontano, un vecchio pezzo jazz continua a suonare."
                                )
                                .build()
                        )
                        .build()
        ).failsIf(new PistolRequirement())
                .failsIf(new MarcusTrustRequirement())
                .keyChoice()
                .build();

        Choice destroyHelix = new Choice.Builder(
                "Distruggi l'infrastruttura Helix dall'interno.",
                new Outcome.Builder()
                        .morale(20)
                        .trust("marcus", 15)
                        .flag("ep3_finale", "destroyed")
                        .narrative(
                                "Nyx è già sparita da un pezzo.\n" +
                                        "Nessuno l'ha vista infilarsi nei livelli più profondi della struttura.\n" +
                                        "È il suo talento migliore: esserci senza esserci.\n" +
                                        "Passano pochi secondi. " +
                                        "Poi tutti gli schermi della sala di comando si spengono.\n" +
                                        "Uno dopo l'altro. Come stelle che decidono di abbandonare il cielo.\n" +
                                        "\"Che cosa...\" - Kessler, incredulo\n" +
                                        "Gli allarmi esplodono in ogni corridoio della struttura.\n" +
                                        "Luci rosse. Sirene. Panico.\n" +
                                        "Vent'anni di dati, backup, archivi e segreti iniziano a dissolversi nel nulla digitale.\n" +
                                        "\"Ecco qual era il piano.\" dice Marcus, con un sorriso che non gli vedevi da tempo.\n" +
                                        "\"Andiamo.\"\n" +
                                        "Correte attraverso il caos. Guardie ovunque.\n" +
                                        "Qualche colpo sparato senza troppa precisione. " +
                                        "Qualche porta forzata. Qualche miracolo statistico.\n" +
                                        "A metà strada incrociate Nyx, che emerge da una nube di fumo " +
                                        "con l'aria di chi ha appena cancellato una cartella per sbaglio.\n" +
                                        "Quando raggiungete l'esterno, dietro di voi resta soltanto una fortezza in agonia.\n" +
                                        "Helix è morta.\n" +
                                        "Vent'anni di lavoro svaniti in una notte.\n" +
                                        "Per la prima volta dopo molto tempo, il futuro non appartiene più a Kessler."
                        )
                        .onFailure(new Outcome.Builder()
                                .morale(-20)
                                .flag("ep3_finale", "failed_destroy")
                                .gameOver()
                                .narrative(
                                        "Il piano era semplice. Forse troppo semplice.\n" +
                                                "Partono tutti gli allarmi. Le sirene riempiono la struttura.\n" +
                                                "Gli schermi restano accesi. I server continuano a funzionare.\n" +
                                                "Helix continua a respirare. Kessler osserva i monitor per qualche secondo.\n" +
                                                "Poi ride.\n" +
                                                "\"Oh, non aveva la password, vero?\"\n" +
                                                "La porta della sala si apre. " +
                                                "Due guardie trascinano Nyx all'interno in manette.\n" +
                                                "La buttano a terra senza troppa delicatezza.\n" +
                                                "Lei prova a rialzarsi, ma una guardia la costringe di nuovo in ginocchio.\n" +
                                                "\"Mi dispiace.\"\n" +
                                                "È l'unica cosa che riesce a dire.\n" +
                                                "Kessler si avvicina e la osserva come si osserva un bug fastidioso sullo schermo.\n" +
                                                "\"Portatela via.\"\n" +
                                                "\"Consideratelo un avvertimento.\"\n" +
                                                "\"Non avvicinatevi più a Helix.\"\n" +
                                                "\"La prossima volta sarà l'ultima.\"\n" +
                                                "Per una volta, nessuno ride."
                                )
                                .build()
                        )
                        .build()
        ).requires(new FlagRequirement("ep2_recruited_nyx", "true"))
                .failsIf(new LenaPasswordRequirement())
                .keyChoice()
                .build();

        Choice broadcastData = new Choice.Builder(
                "Trasmetti tutto. Che il sistema sappia.",
                new Outcome.Builder()
                        .woolong(-2000)
                        .morale(40)
                        .trust("marcus", 35)
                        .flag("ep3_finale", "broadcast")
                        .narrative(
                                "Kessler aspetta la tua risposta, " +
                                        "con la sicurezza di chi non ha mai dovuto affrontare le conseguenze delle proprie azioni.\n" +
                                        "\"No.\"\n" +
                                        "Kessler sospira, poi:\n" +
                                        "\"Sempre la scelta sbagliata. Prendeteli.\" - girandosi verso le guardie\n" +
                                        "È il momento, premi il trasmettitore.\n" +
                                        "Per un secondo non succede nulla.\n" +
                                        "Poi tutti gli schermi della sala di comando cambiano immagine.\n" +
                                        "Compare un notiziario. " +
                                        "La conduttrice ha l'espressione di chi ha appena ricevuto la storia del secolo.\n" +
                                        "Documenti. Registrazioni. Conti nascosti.\n" +
                                        "Ogni segreto di Helix viene riversato in diretta universale, davanti a miliardi di persone.\n" +
                                        "Da qualche parte, fuori dalla struttura, Nyx sorride davanti a una console portatile.\n" +
                                        "Non è mai entrata nell'edificio. Non ne aveva bisogno, vi doveva un favore.\n" +
                                        "Kessler guarda gli schermi, in silenzio. " +
                                        "Le sue bugie non appartengono più a lui. Ormai appartengono al mondo.\n" +
                                        "Marcus ride piano: \"Sai una cosa? Questa me la voglio riguardare.\"\n" +
                                        "Non avete guadagnato un solo woolong.\n" +
                                        "Non avete comprato una nave nuova.\n" +
                                        "Non avete salvato il mondo.\n" +
                                        "Ma avete impedito che qualcuno continuasse a venderlo a pezzi.\n" +
                                        "La verità è venuta a galla. Anche per Aaron.\n" +
                                        "Per una volta, è abbastanza."
                        )
                        .onFailure(new Outcome.Builder()
                                .morale(-15)
                                .flag("ep3_finale", "failed_broadcast")
                                .narrative(
                                        "\"Abbiamo le prove.\"\n" +
                                                "La tua voce riecheggia nella sala di comando.\n" +
                                                "\"L'Operazione Black Dusk.\" \"Il Progetto Chimera.\"\n" +
                                                "\"Le transazioni fantasma nelle colonie esterne.\"\n" +
                                                "\"Abbiamo visto tutto.\"\n" +
                                                "Kessler ti osserva in silenzio. Marcus ti osserva in silenzio.\n" +
                                                "Persino le guardie sembrano curiose di sentire la risposta.\n" +
                                                "Poi scoppia a ridere. Non una risata nervosa. Una risata sincera. Divertita.\n" +
                                                "\"Oh, è meraviglioso.\"\n" +
                                                "\"Ci siete cascati davvero.\"\n" +
                                                "\"Quei dati li ho fatti circolare io - tutti falsi. Tutti contraffatti.\"\n" +
                                                "\"Una rete di esche per capire chi stava scavando troppo a fondo.\"\n" +
                                                "Il sangue ti si gela nelle vene. Marcus abbassa lo sguardo.\n" +
                                                "Ora capisci perché alcune cose non tornavano. Troppo facili da trovare.\n" +
                                                "\"Francamente non pensavo foste così inesperti da crederci.\"\n" +
                                                "Le guardie chiudono il cerchio intorno a voi.\n" +
                                                "Armi puntate. Nessuna via di fuga.\n" +
                                                "Nessun colpo di scena. Nessuna trasmissione. Nessuna verità.\n" +
                                                "Kessler si sistema il colletto della giacca.\n" +
                                                "\"Avreste potuto andarvene ricchi, avete preferito fare gli eroi.\"\n" +
                                                "Fa una pausa.\n" +
                                                "\"È andata male.\""
                                )
                                .build()
                        )
                        .build()
        ).requires(new FlagRequirement("ep2_recruited_nyx", "false"))
                .failsIf(new MarketArchiveRequirement())
                .keyChoice()
                .build();

        Scene scene3 = Scene.finalScene(
                "ep3_s3",
                "Tutto un tratto, silenzio.\n" +
                        "Hai lo sguardo perso nel vuoto, ti si sfoca la vista.\n" +
                        "La testa ti sta esplodendo.\n" +
                        "Passano 10 secondi, sembrano un'eternità.\n" +
                        "Nel mentre inizi a pensare: non sono pochi... ma Marcus?\n" +
                        "Lui non me lo perdonerebbe mai.\n" +
                        "E Nyx? È nella sala dei server ma, sa cosa sta facendo?\n" +
                        "Cosa faccio? Morrow si è sacrificato per nulla?\n" +
                        "Ha una bella faccia tosta questo, che faccio? Sparo?\n" +
                        "E Lena? Cosa sapeva?\nCosa sappiamo noi veramente?\n" +
                        "Poi chiudi gli occhi, calma piatta.\n" +
                        "Sai quello che devi fare.\n\n\n" +
                        "[SCELTA FINALE]: alcune scelte potrebbero fallire " +
                        "a causa di scelte sbagliate durante la run",
                List.of(acceptDeal, killKessler, destroyHelix, broadcastData),
                null,
                Sprites.BG_EP3_1
        );

        return List.of(scene0, scene1, scene2, scene3);
    }

    @Override
    public void initialize(GameSession session) {}
}