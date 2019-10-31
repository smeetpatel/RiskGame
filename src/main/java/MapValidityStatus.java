package main.java;

/**
 * Represents validity status of a game map.
 */
public enum MapValidityStatus {
    /**
     * Represents presence of empty continent in the map.
     */
    EMPTYCONTINENT,

    /**
     * Represents presence of unconnected graph.
     */
    UNCONNECTEDGRAPH,

    /**
     * Represents presence of unconnected country within a continent.
     */
    UNCONNECTEDCONTINENT,

    /**
     * Represents valid map.
     */
    VALIDMAP
}
