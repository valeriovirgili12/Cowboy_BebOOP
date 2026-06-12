package it.unicam.cs.mpgc.rpg126421.model.factory;

import it.unicam.cs.mpgc.rpg126421.model.episode.*;
import it.unicam.cs.mpgc.rpg126421.model.episode.narrative.Ep3Script;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

import java.util.List;

public class EpisodeFactory {

    public static List<Episode> createEpisodes(GameSession session) {

        Ep3Script ep3Script = new Ep3Script(session);

        return List.of(
                new EpisodeOne(),
                new EpisodeTwo(),
                new EpisodeThree(ep3Script)
        );
    }
}