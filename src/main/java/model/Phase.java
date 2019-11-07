package main.java.model;

/**
 * Maintains the current phase of the game.
 */
public enum Phase {

    /**
     * The game is yet to begin. First command has yet not been encountered.
     * Phase ends when 'editmap' or 'loadmap' command is encountered.
     */
    NULL,

    /**
     * An existing game map is being edited or a new game map is being created from the scratch.
     * Phase ends when 'loadmap' command is encountered.
     */
    EDITMAP,

    /**
     * Game is in start-up phase, i.e. gameplayers will be added/removed, countries will be distributed, and initial
     * armies will be assigned to the players.
     * Phase ends when 'populatecountries' command is encountered, i.e countries are distrubuted and initial armies are
     * assigned.
     */
    STARTUP,

    /**
     * Player assigns initial armies to the countries owned by him/her.
     * Phase ends when all player's have assigned initial armies.
     */
    ARMYALLOCATION,

    /**
     * Player will exchange cards.
     */
    CARDEXCHANGE,

    /**
     * Individual turn of player begins in round-robin fashion.
     * Player assigns reinforced armies amongst owned countries.
     * Phase ends when reinforcement armies are distributed amongst the owned countries.
     */
    REINFORCEMENT,

    /**
     * Player can attack on its adjacent countries.
     * Player can choose number of dice for attack operation
     */
    ATTACK,

    /**
     * Player has received more than six cards by defeating a player and forcing him/her out of the game.
     * Needs to exchange the cards right away.
     */
    ATTACKCARDEXCHANGE,

    /**
     * Player fortifies one of the owned countries if a legal move is allowed.
     * Phase ends when player successfully fortifies or decides to not foritfy.
     */
    FORTIFICATION,

    /**
     * Marks the end of the current player and signals to let the player get the turn.
     * Phase ends when next player starts their move.
     */
    TURNEND,

    /**
     * Indicates desire to completely close the game.
     */
    QUIT
}

