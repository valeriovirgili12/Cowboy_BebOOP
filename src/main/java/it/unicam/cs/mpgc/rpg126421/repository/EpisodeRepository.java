package it.unicam.cs.mpgc.rpg126421.repository;

import it.unicam.cs.mpgc.rpg126421.model.episode.Episode;
import it.unicam.cs.mpgc.rpg126421.model.episode.EpisodeOne;
import it.unicam.cs.mpgc.rpg126421.model.episode.EpisodeThree;
import it.unicam.cs.mpgc.rpg126421.model.episode.EpisodeTwo;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementazione concreta del repository episodi.
 * Costruisce e tiene in memoria i 3 episodi del gioco.
 */
public class EpisodeRepository implements IEpisodeRepository {

    private final List<Episode> episodes;
    private final Map<String, Episode> episodeMap;

    public EpisodeRepository() {
        this.episodes = List.of(
                new EpisodeOne(),
                new EpisodeTwo(),
                new EpisodeThree()
        );
        this.episodeMap = episodes.stream()
                .collect(Collectors.toMap(Episode::getId, Function.identity()));
    }

    @Override
    public List<Episode> getAllEpisodes() {
        return episodes;
    }

    @Override
    public Episode getById(String id) {
        Episode episode = episodeMap.get(id);
        if (episode == null)
            throw new IllegalArgumentException("Episode not found: " + id);
        return episode;
    }
}
