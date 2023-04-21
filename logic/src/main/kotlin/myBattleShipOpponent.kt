import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.Ship
import kotlin.random.Random

class MyBattleShipOpponent(override val rows: Int, override val columns: Int, override val ships: List<StudentShip>): BattleshipOpponent {



    constructor(rows: Int, columns: Int, shipSizes: List<Int>,  random: Random) : this(
        rows,
        columns,
        randomShips(rows, columns, shipSizes, random)
    )




    val createOpponent = MyBattleShipOpponent(10, 10, ships)

    override fun shipAt(column: Int, row: Int): BattleshipOpponent.ShipInfo<StudentShip>? {
        val index = ships.indexOfFirst { it.columnIndices.contains(column) && it.rowIndices.contains(row) }
        return if (index != -1) {
            BattleshipOpponent.ShipInfo(index, ships[index])
        } else {
            null
        }

    }

}


fun randomShips(height: Int, width: Int, shipSizes: List<Int>, random:Random): List<StudentShip> {
    val generatedShips = mutableListOf<StudentShip>()

    // Generate horizontal ships
    for (size in shipSizes) {
        val maxTop = height - 1
        val maxLeft = width - size

        var randomShip: StudentShip?
        do {
            val top = (0..maxTop).random()
            val left = (0..maxLeft).random()
            val bottom = top
            val right = left + size - 1

            val overlaps = generatedShips.any { s ->
                (top..bottom).intersect(s.top..s.bottom).isNotEmpty() &&
                        (left..right).intersect(s.left..s.right).isNotEmpty() &&
                        (s.top..s.bottom).intersect(top..bottom).isNotEmpty() &&
                        (s.left..s.right).intersect(left..right).isNotEmpty()
            }

            if (!overlaps) {
                randomShip = StudentShip(top, left, bottom, right)
            } else {
                randomShip = null
            }
        } while (randomShip == null)

        generatedShips.add(randomShip)
    }

    // Generate vertical ships
    for (size in shipSizes) {
        val maxTop = height - size
        val maxLeft = width - 1

        var randomShip: StudentShip?
        do {
            val top = (0..maxTop).random()
            val left = (0..maxLeft).random()
            val bottom = top + size - 1
            val right = left

            val overlaps = generatedShips.any { s ->
                (top..bottom).intersect(s.top..s.bottom).isNotEmpty() &&
                        (left..right).intersect(s.left..s.right).isNotEmpty() &&
                        (s.top..s.bottom).intersect(top..bottom).isNotEmpty() &&
                        (s.left..s.right).intersect(left..right).isNotEmpty()
            }

            if (!overlaps) {
                randomShip = StudentShip(top, left, bottom, right)
            } else {
                randomShip = null
            }
        } while (randomShip == null)

        generatedShips.add(randomShip)
    }

    return generatedShips
}

/** I don't want to hard code the the ship therefore I want to only give it the Top(row) value
and depending on the orientation feed it the size(column)* */
/**val ship = listOf(
StudentShip(0, 0, 0, 4),/** a horizontal ship of length 5 at row 0, columns 0-4*/
StudentShip(2, 3, 6, 3),/**a vertical ship of length 4 at rows 2-5, column 3*/
StudentShip(4, 4,6,4),
StudentShip(0, 6, 2, 6),
StudentShip(3, 9,4, 9))*/


