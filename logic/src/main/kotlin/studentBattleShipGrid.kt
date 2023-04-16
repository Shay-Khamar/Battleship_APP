import uk.ac.bournemouth.ap.battleshiplib.*
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid.Companion.DEFAULT_COLUMNS
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid.Companion.DEFAULT_ROWS

class studentBattleShipGrid(override val rows: Int = DEFAULT_ROWS , override val columns: Int = DEFAULT_COLUMNS, override val opponent: BattleshipOpponent): BattleshipGrid {

    // Create a 2D array of variable rows and columns, each index contains the value UNSET By default
    public var dataGrid: Array<Array<GuessCell>> = Array(rows) { Array(columns) { GuessCell.UNSET } }
    val opponentShips: List<Ship> get() = opponent.ships

    // Creates a boolean array intialized to false, based off the amount ships the oppnent has
    override val shipsSunk: BooleanArray
    get() = BooleanArray(opponent.ships.size)

    //Return true if all ships in the array are sunk and ends the game
    override val isFinished: Boolean get() = shipsSunk.all { it }

    /*Check if the value is within the grid parameters, if not return Invalid Cell
    Return that postion on the Grid*/
    override fun get(column: Int, row: Int): GuessCell {
        require(column in 0 until columns && row in 0 until rows) { "Invalid cell" }
        //Note to self Use this to refer to a certain point on the grid
        return dataGrid[row][column]
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
        val cell = dataGrid[row][column]
       TODO("Not yet implemented")
    }

    override fun addOnGridChangeListener(listener: BattleshipGrid.BattleshipGridListener) {
        TODO("Not yet implemented")
    }

    override fun removeOnGridChangeListener(listener: BattleshipGrid.BattleshipGridListener) {
        TODO("Not yet implemented")
    }
}