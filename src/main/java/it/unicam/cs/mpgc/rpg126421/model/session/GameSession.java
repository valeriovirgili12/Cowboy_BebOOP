package it.unicam.cs.mpgc.rpg126421.model.session;

import it.unicam.cs.mpgc.rpg126421.model.character.Captain;
import it.unicam.cs.mpgc.rpg126421.model.character.companion.Marcus;
import it.unicam.cs.mpgc.rpg126421.model.character.crew.CrewMember;
import it.unicam.cs.mpgc.rpg126421.model.episode.Episode;
import it.unicam.cs.mpgc.rpg126421.model.market.BlackMarket;
import it.unicam.cs.mpgc.rpg126421.model.market.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Contenitore principale della partita in corso.
 * Tiene insieme capitano, crew, finanze, episodi e stato del mondo.
 */
public class GameSession {

    private final Captain captain;
    private final Marcus marcus;
    private final Finance finance;
    private final WorldState worldState;
    private final Set<CrewMember> crew;
    private final List<Item> inventory;
    private final List<Episode> episodes;


    public GameSession(Captain captain) {
        if (captain == null) throw new IllegalArgumentException("Captain cannot be null");
        this.captain    = captain;
        this.marcus     = new Marcus();
        this.finance    = new Finance();
        this.worldState = new WorldState();
        this.crew       = new HashSet<>();
        this.episodes   = new ArrayList<>();
        this.inventory  = new ArrayList<>();
    }

    public Captain getCaptain() { return captain; }

    public Finance getFinance() { return finance; }

    public WorldState getWorldState() { return worldState; }

    //public Marcus getMarcus() { return marcus; }
    /**
     * Aggiunge un membro alla crew. HashSet garantisce no duplicati.
     * @return false se il membro era già presente
     */
    public boolean recruitCrew(CrewMember member) {
        if (member == null) throw new IllegalArgumentException("CrewMember cannot be null");
        return crew.add(member);
    }

    /**
     * Rimuove un membro dalla crew.
     * @return false se il membro non era presente
     */
    public boolean dismissCrew(CrewMember member) {
        return crew.remove(member);
    }

    public Set<CrewMember> getCrew() {
        return Collections.unmodifiableSet(crew);
    }

    public boolean hasCrew(CrewMember member) {
        return crew.contains(member);
    }

    public void addEpisode(Episode episode) {
        if (episode == null) throw new IllegalArgumentException("Episode cannot be null");
        episodes.add(episode);
    }

    public List<Episode> getEpisodes() {
        return Collections.unmodifiableList(episodes);
    }


    /**
     * Restituisce il primo episodio non ancora completato.
     */
    public java.util.Optional<Episode> getCurrentEpisode() {
        return episodes.stream()
                .filter(e -> !e.isCompleted())
                .findFirst();
    }

    public void addItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        inventory.add(item);
    }
    public Marcus getMarcus() { return marcus; }

    public boolean hasItem(Item item) {
        return inventory.contains(item);
    }

    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    @Override
    public String toString() {
        return "GameSession{captain=" + captain.getName() +
                ", woolong=" + finance.getWoolong() +
                ", crew=" + crew.size() +
                ", episodes=" + episodes.size() + "}";
    }
}