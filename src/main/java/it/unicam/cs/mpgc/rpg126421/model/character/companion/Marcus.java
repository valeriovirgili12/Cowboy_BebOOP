package it.unicam.cs.mpgc.rpg126421.model.character.companion;

import it.unicam.cs.mpgc.rpg126421.model.character.PermanentCompanion;
import it.unicam.cs.mpgc.rpg126421.model.shared.CrewClass;

/**
 * Marcus Veil — ex detective ISSP, compagno permanente.
 * Pragmatico, emotivamente represso, porta il peso di un'indagine fallita.
 * Il suo livello di fiducia determina l'outcome di alcuni finali.
 */
public class Marcus extends PermanentCompanion {

    public Marcus() {
        super("Marcus Veil", CrewClass.GUNSLINGER);
    }

    /**
     * Reagisce alle scelte del giocatore con battute contestuali.
     * Chiamato dopo ogni scelta chiave — il testo appare nella UI.
     */
    @Override
    public String reactToChoice(String choiceFlag) {
        return switch (choiceFlag) {
            case "ep1_spared_lena"   -> "\"Lena Morrow... Aaron non sapeva di avere una sorella.\"";
            case "ep1_delivered_lena"-> "\"Era una taglia. Solo una taglia. Convinciti.\"";
            case "ep2_recruited_nyx" -> "\"Non mi fido di lei. Ma suppongo che non sia una novità.\"";
            case "ep2_rejected_nyx"  -> "\"Scelta saggia. Meno variabili, meno rischi.\"";
            case "ep3_deal_accepted" -> "\"...non dire nulla. Non voglio sentirlo.\"";
            case "ep3_kessler_delivered" -> "\"Forse questa volta facciamo la cosa giusta.\"";
            case "ep3_servers_destroyed" -> "\"Niente prove, niente processo. Ma almeno è finita.\"";
            case "ep3_broadcast"     -> "\"Aaron avrebbe voluto vederlo. Tutto quanto.\"";
            default                  -> "";
        };
    }
}