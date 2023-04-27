package uk.ac.bournemouth.ap.battleshiplogic
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.Ship
import kotlin.random.Random

class MyBattleShipOpponent(
    override val columns: Int,
    override val rows: Int,
    override val ships: List<StudentShip>
): BattleshipOpponent {



    constructor(
        columns: Int = BattleshipGrid.DEFAULT_COLUMNS,
        rows: Int = BattleshipGrid.DEFAULT_ROWS,
        shipSizes: IntArray = BattleshipGrid.DEFAULT_SHIP_SIZES,
        random: Random = Random
    ) : this(
        columns,
        rows,
        randomShips(rows, columns, shipSizes, random)
    )





    override fun shipAt(column: Int, row: Int): BattleshipOpponent.ShipInfo<StudentShip>? {
        val index = ships.indexOfFirst { it.columnIndices.contains(column) && it.rowIndices.contains(row) }
        return if (index != -1) {
            BattleshipOpponent.ShipInfo(index, ships[index])
        } else {
            null
        }

    }

    data class ShipInfo<out S: StudentShip>(val index: Int, val ship: S)

}

fun Ship.overlaps(other: Ship) : Boolean {
    return right >= other.left && left <= other.right &&
            bottom >= other.top && top <= other.bottom
}


fun randomShips(height: Int, width: Int, shipSizes: IntArray, random:Random): List<StudentShip> {
    val generatedShips = mutableListOf<StudentShip>()

    // Generate horizontal ships
    for (size in shipSizes) {
        val maxTop = height - 1
        val maxLeft = width - size

        var randomShip: StudentShip
        do {
            val isHorizontal = when {
                width < size -> false
                height < size -> true
                else -> random.nextBoolean()
            }

            if (isHorizontal) {
                val row = random.nextInt(height)
                val left = random.nextInt(width - size +1)
                val right = left + size - 1
                randomShip = StudentShip(top = row, left = left, bottom = row, right = right)
            } else {
                val top = random.nextInt(height - size + 1)
                val column = random.nextInt(width)
                val bottom = top + size - 1
                randomShip = StudentShip(top = top, left = column, bottom = bottom, right = column)
            }


            val overlaps = generatedShips.any { s -> s.overlaps(randomShip)
/*
                (top..bottom).intersect(s.top..s.bottom).isNotEmpty() &&
                        (left..right).intersect(s.left..s.right).isNotEmpty() &&
                        (s.top..s.bottom).intersect(top..bottom).isNotEmpty() &&
                        (s.left..s.right).intersect(left..right).isNotEmpty()
*/
            }

        } while (overlaps)

        generatedShips.add(randomShip)
    }

    // Generate vertical ships

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


