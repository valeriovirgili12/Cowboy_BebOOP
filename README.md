# Cowboy BebOOP

> *"3, 2, 1... Let's Jam."*

RPG narrativo a scelte multiple ambientato in un futuro distopico ispirato a *Cowboy Bebop*. Progetto universitario per il corso di Programmazione a Oggetti — Università di Camerino, secondo anno.

Il giocatore veste i panni del capitano della **Blue Mantis**, una nave da bounty hunting in perenne crisi di carburante, trascinato in una cospirazione che coinvolge la più potente corporazione del sistema solare. Tre episodi, otto finali, nessuna scelta innocua.

---

## Requisiti

- Java 25
- Gradle 9.0
- JavaFX 25 (incluso automaticamente via Gradle)

## Esecuzione

```bash
# Clona il repository
git clone https://github.com/valeriovirgili1201/it.unicam.mpgc.rpg126421.git
cd it.unicam.mpgc.rpg126421

# Compila
.\gradlew build        # Windows
./gradlew build        # Linux/macOS

# Avvia il gioco
.\gradlew run          # Windows
./gradlew run          # Linux/macOS
```

## Struttura del progetto

```
src/main/
├── java/it/unicam/cs/mpgc/rpg126421/
│   ├── controller/       JavaFX Controllers
│   ├── model/            Entità di dominio
│   ├── repository/       Accesso ai dati
│   ├── service/          Logica di business
│   └── util/             Utility (SceneManager, Sprites, Audio)
└── resources/it/unicam/cs/mpgc/rpg126421/
    ├── css/              Stile CRT
    ├── fxml/             Layout schermate
    ├── sprites/          Personaggi e sfondi
    └── audio/            Colonna sonora
```
Utilizzo strumenti AI nella realizzazione del progetto:

Claude: completamento metodi più semplici e commenti, correzione logica, implementazione sistema Audio, debug finale.

ChatGPT: trama, nomi di personaggi e oggetti, adattamento idea al tema RPG.

Gemini: restyling generale dei file .fxml e della UI.

Per la documentazione completa vedi la [Wiki](../../wiki).

---

*Università di Camerino — Corso Metodologie di Programmazione  — A.A. 2025/2026*
