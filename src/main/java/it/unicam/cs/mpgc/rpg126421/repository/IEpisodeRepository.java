package it.unicam.cs.mpgc.rpg126421.repository;

import it.unicam.cs.mpgc.rpg126421.model.episode.Episode;

import java.util.List;

/**
 * Interfaccia per il repository degli episodi.
 * Fornisce i 3 episodi del gioco pronti per essere aggiunti alla sessione.
 * Seguendo il principio di inversione delle dipendenze (SOLID - DIP),
 * GameService dipende da questa interfaccia, non dall'implementazione concreta.
 */
public interface IEpisodeRepository {

    /**
     * Restituisce tutti gli episodi del gioco nell'ordine corretto.
     */
    List<Episode> getAllEpisodes();

    /**
     * Restituisce un episodio per id.
     */
    Episode getById(String id);
}