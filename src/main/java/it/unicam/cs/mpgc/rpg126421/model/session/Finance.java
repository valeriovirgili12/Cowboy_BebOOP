package it.unicam.cs.mpgc.rpg126421.model.session;
    /**
     * Gestisce i Woolong (valuta di gioco).
     * I soldi sono sempre pochi come nella serie.
     */
    public class Finance {

        private static final int STARTING_WOOLONG = 500;

        private int woolong;
        private static final int MIN_WOOLONG = -2000;

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
         * @return false se si scende sotto i 2000 Woolong di debiti
         */
        public boolean spend(int amount) {
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
            this.woolong -= amount;
            return this.woolong >= MIN_WOOLONG;
        }

        public boolean canAfford(int amount) {
            return this.woolong >= amount;
        }

        public boolean isBankrupt() {
            return this.woolong <= MIN_WOOLONG;
        }

        @Override
        public String toString() {
            return "Finance{woolong=" + woolong + "}";
        }
    }

