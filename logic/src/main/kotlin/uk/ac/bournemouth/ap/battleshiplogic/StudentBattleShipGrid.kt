package uk.ac.bournemouth.ap.battleshiplogic
import uk.ac.bournemouth.ap.battleshiplib.*
import uk.ac.bournemouth.ap.battleshiplogic.StudentShip
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid.Companion.DEFAULT_COLUMNS
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid.Companion.DEFAULT_ROWS
import uk.ac.bournemouth.ap.lib.matrix.MutableMatrix

class StudentBattleShipGrid( override val opponent: BattleshipOpponent): BattleshipGrid {

    override val columns: Int
        get() = opponent.columns
    override val rows: Int
        get() = opponent.rows

    // Create a 2D array of variable rows and columns, each index contains the value UNSET By default
    val dataGrid = MutableMatrix<GuessCell>(columns, rows, GuessCell.UNSET)
    //public var dataGrid: Array<Array<GuessCell>> = Array(rows) { Array(columns) { GuessCell.UNSET } }
    // Creates a boolean array intialized to false, based off the amount ships the oppnent has
    override val shipsSunk: BooleanArray = BooleanArray(opponent.ships.size)

    /*Check if the value is within the grid parameters, if not return Invalid Cell
    Return that postion on the Grid*/
    override fun get(column: Int, row: Int): GuessCell {
        require(column in 0 until columns && row in 0 until rows) {
            "Invalid cell"
        }
        //Note to self Use this to refer to a certain point on the grid
        return dataGrid[column,row]
    }
    //With the shootAt function Takes in user Input so a point on the grid
    //Checks the list of Ships
    //Checks each ship element looping through its values
    //Does that for the entire ship List
    //If the user input matches any of the ships values
    //Check that ships stored positions to determine if they have all been it
    //If so Return Sink
    //Else Return HIT


    override fun shootAt(column: Int, row: Int): GuessResult {
        require(column in 0 until columns && row in 0 until rows) { "Invalid cell" }
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
            if(! shipSunk) {
                fireGameChange(column, row)
                return GuessResult.HIT(shipIndex)
            } else {

                fireGameChange(column, row)
                return GuessResult.SUNK(shipIndex)
                shipInfo.ship.forEachIndex { x, y ->
                    dataGrid[x,y] = GuessCell.SUNK(shipIndex)}
            }
        }

    }

    fun fireGameChange(column: Int, row: Int) {
        for(listener in gameChangeListeners) {
            listener.onGridChanged(this, column, row)
        }
    }
    private val gameChangeListeners = mutableListOf<BattleshipGrid.BattleshipGridListener>()

    override fun addOnGridChangeListener(listener: BattleshipGrid.BattleshipGridListener){
        if (listener !in gameChangeListeners) {
            gameChangeListeners.add(listener)
        }
    }

        override fun removeOnGridChangeListener(listener: BattleshipGrid.BattleshipGridListener){
            gameChangeListeners.remove(listener)

        }
}