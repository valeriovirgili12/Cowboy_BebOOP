package it.unicam.cs.mpgc.rpg126421.model.market;

import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

import java.util.Arrays;
import java.util.List;

/**
 * Mercato nero accessibile alla fine dell'Episodio 1.
 * Mostra gli item disponibili e delega l'acquisto a GameService.
 */
public class BlackMarket {

    private static final List<Item> AVAILABLE_ITEMS =
            Arrays.asList(Item.values());

    /**
     * Restituisce tutti gli item acquistabili.
     */
    public List<Item> getAvailableItems() {
        return AVAILABLE_ITEMS;
    }

    /**
     * Restituisce solo gli item che il giocatore non possiede ancora.
     */
    public List<Item> getAvailableItems(GameSession session) {
        return AVAILABLE_ITEMS.stream()
                .filter(item -> !session.hasItem(item))
                .toList();
    }
}