package main.java;

/**
 * Represents possible status of fortification operation.
 */
public enum FortificationCheck {
    /**
     * Represents that there is no path of player owned countries between argument countries for fortification.
     */
    PATHFAIL,

    /**
     * Represents that are not enough countries to shift to other country.
     */
    ARMYCOUNTFAIL,

    /**
     * Represents that the destiny country does not exist.
     */
    TOCOUNTRYFAIL,

    /**
     * Represents that the origin country does not exist.
     */
    FROMCOUNTRYFAIL,

    /**
     * Represents successful completion of foritification operation.
     */
    FORTIFICATIONSUCCESS
}
