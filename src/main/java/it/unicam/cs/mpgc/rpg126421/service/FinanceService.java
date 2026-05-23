package it.unicam.cs.mpgc.rpg126421.service;

import it.unicam.cs.mpgc.rpg126421.model.market.Item;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Gestisce le operazioni finanziarie della partita.
 * Responsabilità unica: woolong, costi fissi, acquisti al mercato.
 */
public class FinanceService {

    private final GameSession session;

    public FinanceService(GameSession session) {
        this.session = session;
    }

    /**
     * Scala i costi fissi tra episodi (carburante, cibo).
     * @return true se il pagamento è andato a buon fine
     */
    public boolean applyFixedCosts(int amount) {
        return session.getFinance().spend(amount);
    }

    /**
     * Acquista un oggetto dal mercato nero.
     * @return true se l'acquisto è andato a buon fine
     */
    public boolean buyItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        boolean paid = session.getFinance().spend(item.getCost());
        if (paid) {
            session.addItem(item);
        }
        return paid;
    }

    public boolean canAfford(int amount) {
        return session.getFinance().canAfford(amount);
    }
}