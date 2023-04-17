import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.Ship

class myBattleShipOpponent(override val rows: Int, override val columns: Int, override val ships: List<StudentShip>): BattleshipOpponent {


    private val cells = Array(rows) { IntArray(columns) { 0 } }

    /** I don't want to hard code the the ship therefore I want to only give it the Top(row) value
     and depending on the orientation feed it the size(column)* */
    val ship = listOf(
        StudentShip(0, 0, 0, 4),/** a horizontal ship of length 5 at row 0, columns 0-4*/
        StudentShip(2, 3, 6, 3),/**a vertical ship of length 4 at rows 2-5, column 3*/
        StudentShip(4, 4,6,4),
        StudentShip(0, 6, 2, 6),
        StudentShip(3, 9,4, 9))

    val createOpponent = myBattleShipOpponent(10, 10, ship)

    override fun shipAt(column: Int, row: Int): BattleshipOpponent.ShipInfo<Ship>? {
        TODO("Not yet implemented")
    }


}