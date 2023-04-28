package uk.ac.bournemouth.ap.battleshiplogic
import uk.ac.bournemouth.ap.battleshiplib.*
import uk.ac.bournemouth.ap.battleshiplogic.StudentShip
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid.Companion.DEFAULT_COLUMNS
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid.Companion.DEFAULT_ROWS
import uk.ac.bournemouth.ap.lib.matrix.MutableMatrix
/**
 * This class implements the BattleshipGrid interface to create a game grid
 * for a student player to play against an opponent.
 *
 * @property opponent The opponent of the student player.
 */

class StudentBattleShipGrid( override val opponent: BattleshipOpponent): BattleshipGrid {

    /**
     * The number of columns and rows in the grid.
     */
    override val columns: Int
        get() = opponent.columns
    override val rows: Int
        get() = opponent.rows

    /**
     * The data grid is a 2D array of variable rows and columns, each index contains the value UNSET by default.
     */
    val dataGrid = MutableMatrix<GuessCell>(columns, rows, GuessCell.UNSET)

    /**
     * An array of booleans to keep track of which ships have been sunk.
     */
    override val shipsSunk: BooleanArray = BooleanArray(opponent.ships.size)

    /**
     * Indicates whether the game has finished.
     */
    override val isFinished: Boolean get() = shipsSunk.all { it }

    /**
     * A set of pairs representing the cells that have already been targeted by the student player.
     */
    private val targetedCells = mutableSetOf<Pair<Int, Int>>()

    /**
     * Returns the value at the given column and row on the grid.
     *
     * @param column The column index.
     * @param row The row index.
     * @return The value at the given position on the grid.
     * @throws IllegalArgumentException if the column or row is not within the grid parameters.
     */
    override fun get(column: Int, row: Int): GuessCell {
        require(column in 0 until columns && row in 0 until rows) {
            "Invalid cell"
        }
        //Note to self Use this to refer to a certain point on the grid
        return dataGrid[column,row]
    }



    /**
     * Shoots at the given cell on the grid.
     *
     * @param column The column index.
     * @param row The row index.
     * @return The result of the shot (MISS, HIT, or SUNK).
     * @throws IllegalArgumentException if the column or row is not within the grid parameters, or if the cell has already been hit or sunk.
     * @throws Exception if the same cell is targeted twice.
     */
    override fun shootAt(column: Int, row: Int): GuessResult {
        require(column in 0 until columns && row in 0 until rows
                && dataGrid[column, row] !is GuessCell.HIT
                && dataGrid[column, row] !is GuessCell.SUNK) { "Invalid cell" }
        if (Pair(column, row) in targetedCells) {
            throw Exception("Duplicate move")
        }
        targetedCells.add(Pair(column, row))
        val shipInfo = opponent.shipAt(column, row)
        if (shipInfo == null) {
            dataGrid[column,row] = GuessCell.MISS
            fireGameChange(column, row)
            return GuessResult.MISS

        } else {
            val shipIndex = shipInfo.index
            dataGrid[column,row] = GuessCell.HIT(shipIndex)

            var shipSunk = true
            shipInfo.ship.forEachIndex { x, y ->
                if (dataGrid[x,y] !is GuessCell.HIT) shipSunk = false
            }
            if(shipSunk) {
                shipInfo.ship.forEachIndex { x, y ->
                    dataGrid[x,y] = GuessCell.SUNK(shipIndex)
                }
                shipsSunk[shipIndex] = true
                fireGameChange(column, row)
                if (shipsSunk.all { it }) {
                    isFinished
                }
                return GuessResult.SUNK(shipIndex)
            } else {
                fireGameChange(column, row)
                return GuessResult.HIT(shipIndex)
            }
        }
    }


    /**
     * Notifies all listeners that the grid has changed at the given column and row.
     * @param column The column index of the changed cell.
     * @param row The row index of the changed cell.
     */
    fun fireGameChange(column: Int, row: Int) {
        for(listener in gameChangeListeners) {
            listener.onGridChanged(this, column, row)
        }
    }

    /**
     * A list of listeners that should be notified when the grid changes.
     */

    private val gameChangeListeners = mutableListOf<BattleshipGrid.BattleshipGridListener>()

    /**
     * Adds a listener to the list of listeners that should be notified when the grid changes.
     * @param listener The listener to add.
     */
    override fun addOnGridChangeListener(listener: BattleshipGrid.BattleshipGridListener){
        if (listener !in gameChangeListeners) {
            gameChangeListeners.add(listener)
        }
    }

    /**
     * Removes a listener from the list of listeners that should be notified when the grid changes.
     * @param listener The listener to remove.
     */
        override fun removeOnGridChangeListener(listener: BattleshipGrid.BattleshipGridListener){
            gameChangeListeners.remove(listener)

        }
}