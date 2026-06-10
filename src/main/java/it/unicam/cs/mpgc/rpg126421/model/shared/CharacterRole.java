package it.unicam.cs.mpgc.rpg126421.model.shared;

/**
 * Interfaccia comune per tutte le classi personaggio.
 * Implementata da CaptainClass e CrewClass.
 */
public interface CharacterRole {
    String getDisplayName();
    String getPerk();
}