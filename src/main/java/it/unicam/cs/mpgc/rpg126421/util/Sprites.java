package it.unicam.cs.mpgc.rpg126421.util;

/**
 * Costanti per i path degli sprite.
 * Unico punto di modifica se cambiano i nomi dei file.
 */
public final class Sprites {

    private static final String BASE =
            "/it/unicam/cs/mpgc/rpg126421/sprites/";

    // Characters
    public static final String LENA    = BASE + "characters/lena_facecard.png";
    public static final String NYX     = BASE + "characters/nyx_facecard.png";
    public static final String MARCUS  = BASE + "characters/marcus_facecard.png";
    public static final String KESSLER = BASE + "characters/kessler_facecard.png";

    // Backgrounds
    public static final String BG_EP1 = BASE + "background/ep1_bg.png";
    public static final String BG_EP2 = BASE + "background/ep2_bg.png";
    public static final String BG_EP3 = BASE + "background/ep3_bg.png";
    public static final String BG_EP3_1 = BASE + "background/ep3.1_bg.png";


    private Sprites() {} // non istanziabile
}