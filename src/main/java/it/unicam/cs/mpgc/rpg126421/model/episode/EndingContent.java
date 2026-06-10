package it.unicam.cs.mpgc.rpg126421.model.episode;

/**
 * Contenuto testuale dei finali.
 * Separato dal controller — il controller visualizza, questo contiene.
 */
public record EndingContent(String title, String summary) {

    public static EndingContent forFinale(String finale) {
        return switch (finale) {
            case "deal" -> new EndingContent(
                    "3, 2, 1 — Let's Jam.",
                    "Hai accettato l'offerta. La Blue Mantis è piena di carburante.\n" +
                            "Marcus non c'è più\n\n" +
                            "Ma i woolong non mentono."
            );
            case "killed" -> new EndingContent(
                    "One Shot.",
                    "Kessler non ha finito la frase.\n" +
                            "Un colpo è bastato.\n\n" +
                            "La Helix è ancora in piedi,\n" +
                            "ma il suo re è morto sul trono.\n\n" +
                            "La notte continua a suonare jazz."
            );

            case "destroyed" -> new EndingContent(
                    "Ashes and Static.",
                    "Vent'anni di dati cancellati in pochi minuti.\n" +
                            "Backup, archivi, segreti.\n" +
                            "Tutto sparito.\n\n" +
                            "Quando il sole sorgerà,\n" +
                            "Helix sarà solo un ricordo."
            );

            case "broadcast" -> new EndingContent(
                    "Signal Clear.",
                    "La verità ha trovato una frequenza.\n" +
                            "Questa volta nessuno è riuscito a spegnerla.\n\n" +
                            "Non avete guadagnato nulla.\n" +
                            "Tranne forse la certezza di aver fatto la cosa giusta.\n\n" +
                            "Aaron Morrow può finalmente riposare."
            );

            case "failed_kill_nopistol" -> new EndingContent(
                    "Unarmed.",
                    "Avevi un piano.\n" +
                            "Ti mancava una pistola.\n\n" +
                            "Marcus ha pagato la differenza.\n\n" +
                            "Alcuni errori continuano a vivere più di chi li commette."
            );

            case "failed_kill_notrust" -> new EndingContent(
                    "Too Late.",
                    "Hai estratto la pistola.\n" +
                            "Marcus non si è mosso.\n\n" +
                            "La fiducia se ne va in silenzio.\n" +
                            "Proprio come le ultime occasioni."
            );

            case "failed_destroy" -> new EndingContent(
                    "Access Denied.",
                    "Gli allarmi hanno urlato.\n" +
                            "I server hanno continuato a funzionare.\n\n" +
                            "Nyx è sparita oltre una porta che non si è più riaperta.\n\n" +
                            "Per Helix era solo un avvertimento."
            );

            case "failed_broadcast" -> new EndingContent(
                    "Signal Lost.",
                    "I documenti erano falsi.\n" +
                            "La trappola era vera.\n\n" +
                            "Kessler vi aveva offerto una via d'uscita.\n" +
                            "Avete scelto di fare gli eroi.\n\n" +
                            "Le guardie hanno scritto il resto della storia."
            );

            default -> new EndingContent(
                    "See You, Space Cowboy...",
                    "Le luci della Blue Mantis si riaccendono.\n" +
                            "Da qualche parte c'è un'altra taglia,\n" +
                            "un altro guaio,\n" +
                            "un altro giorno da sprecare.\n\n" +
                            "See you, space cowboy..."
            );
        };
    }
}