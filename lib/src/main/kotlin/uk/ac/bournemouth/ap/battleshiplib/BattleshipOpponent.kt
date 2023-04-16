package uk.ac.bournemouth.ap.battleshiplib

import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

interface BattleshipOpponent {
    /**
     * The amount of columns in the game grid (how wide is the grid).
     */
    //Columnns and rows denote the size of the GRID So that be 10x10 as an example
    //These values can then be used to creat a 2D array GRID
    val columns: Int

    /**
     * The amount of rows in the game grid (how high is the grid)
     */
    val rows: Int

    /**
     * Get the list of ships for this opponent
     */
    //This variable then contains a set list of ships that the the opponent possess, It also has the values
    //
    val ships: List<Ship>

    /**
     * Determine the ship at the given position. Note that subclasses could specialise the return
     * type to be a shipInfo of a subtype of Ship.
     */
    // This takes in a column and row from the grid and returns information about a ship that is there
    //However the question mark means that the return type could be null because there may be nothing there to return
    fun shipAt(column: Int, row: Int): ShipInfo<Ship>?

    /**
     * Simple class to hold information about a ship, both the index and the ship itself
     */
    data class ShipInfo<out S: Ship>(val index: Int, val ship: S)
}

/**
 * Shortcut function that will allow accessing the shipAt function with a coordinate directly.
 */
fun BattleshipOpponent.shipAt(coordinate: Coordinate): BattleshipOpponent.ShipInfo<Ship>? =
    shipAt(coordinate.x, coordinate.y)