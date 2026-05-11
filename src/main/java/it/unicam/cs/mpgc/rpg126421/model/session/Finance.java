package it.unicam.cs.mpgc.rpg126421.model.session;
    /**
     * Gestisce i Woolong (valuta di gioco).
     * I soldi sono sempre pochi — come nella serie.
     */
    public class Finance {

        private static final int STARTING_WOOLONG = 300;

        private int woolong;

        public Finance() {
            this.woolong = STARTING_WOOLONG;
        }

        public int getWoolong() {
            return woolong;
        }

        /**
         * Aggiunge woolong (bounty, ricompense).
         * @param amount deve essere positivo
         */
        public void earn(int amount) {
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
            this.woolong += amount;
        }

        /**
         * Sottrae woolong (spese, danni).
         * @return false se non ci sono fondi sufficienti
         */
        public boolean spend(int amount) {
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
            if (this.woolong < amount) return false;
            this.woolong -= amount;
            return true;
        }

        public boolean canAfford(int amount) {
            return this.woolong >= amount;
        }

        @Override
        public String toString() {
            return "Finance{woolong=" + woolong + "}";
        }
    }

