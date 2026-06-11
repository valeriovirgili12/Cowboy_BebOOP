package it.unicam.cs.mpgc.rpg126421.repository;

import it.unicam.cs.mpgc.rpg126421.factory.EpisodeFactory;
import it.unicam.cs.mpgc.rpg126421.model.episode.Episode;
import it.unicam.cs.mpgc.rpg126421.service.GameService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EpisodeRepository implements IEpisodeRepository {

    private final List<Episode> episodes;
    private final Map<String, Episode> episodeMap;

    public EpisodeRepository(List<Episode> episodes) {

        this.episodes = episodes;

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

        if (episode == null) {
            throw new IllegalArgumentException("Episode not found: " + id);
        }

        return episode;
    }
}