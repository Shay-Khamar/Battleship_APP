package uk.ac.bournemouth.ap.battleshiplogic
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.Ship
import kotlin.random.Random


/**
 * An implementation of [BattleshipOpponent] that places ships randomly on the grid.
 *
 * @property columns the number of columns on the grid
 * @property rows the number of rows on the grid
 * @property ships the list of ships to place on the grid
 */
class MyBattleShipOpponent(
    override val columns: Int,
    override val rows: Int,
    override val ships: List<StudentShip>
): BattleshipOpponent {

    /** Constructor that validates the ships passed in.*/
    init {
        // Validate ships
        val invalidShips = mutableListOf<StudentShip>()
        for (i in 0 until ships.size) {
            val ship1 = ships[i]
            if (!isValidShip(ship1)) {
                invalidShips.add(ship1)
                continue
            }
            for (j in i+1 until ships.size) {
                val ship2 = ships[j]
                if (ship1.overlaps(ship2)) {
                    invalidShips.add(ship1)
                    invalidShips.add(ship2)
                    break
                }
            }
        }
        if (invalidShips.isNotEmpty()) {
            throw IllegalArgumentException("Invalid ships detected: $invalidShips")
        }
    }

    /** Checks if the given ship is valid.*/
    private fun isValidShip(ship: StudentShip): Boolean {
        // Check ship position is within grid boundaries
        if (ship.left < 0 || ship.right >= columns || ship.top < 0 || ship.bottom >= rows) {
            return false
        }

        /** Check ship size matches its position*/
        val size = when {
            ship.left == ship.right -> ship.bottom - ship.top + 1 // vertical ship
            ship.top == ship.bottom -> ship.right - ship.left + 1 // horizontal ship
            else -> return false // invalid orientation
        }
        if (size != ship.size) {
            return false
        }

        /** Check ship does not overlap with any other ships*/
        for (otherShip in ships) {
            if (ship != otherShip && ship.overlaps(otherShip)) {
                return false
            }
        }

        return true
    }








    /** Constructor that creates a random grid based on the given parameter*/
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







    /**Gets information about the ship at the given column and row.*/
    override fun shipAt(column: Int, row: Int): BattleshipOpponent.ShipInfo<StudentShip>? {
        val index = ships.indexOfFirst { it.columnIndices.contains(column) && it.rowIndices.contains(row) }
        return if (index != -1) {
            BattleshipOpponent.ShipInfo(index, ships[index])
        } else {
            null
        }

    }

    /**Data class representing information about a ship.*/
    data class ShipInfo<out S: StudentShip>(val index: Int, val ship: S)

}
/**Extension function to check if two ships overlap.*/
  fun Ship.overlaps(other: Ship) : Boolean {
    return right >= other.left && left <= other.right &&
            bottom >= other.top && top <= other.bottom
}

/**
Generates a list of random StudentShip objects within the given height and width bounds,
with ship sizes specified by the shipSizes array, using the given random generator.
The function generates horizontal and vertical ships of the specified sizes, making sure
they do not overlap with each other.
@param height the height of the game board
@param width the width of the game board
@param shipSizes an array of ship sizes to generate
@param random the random number generator to use
@return a list of generated StudentShip objects
 */
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
            }

        } while (overlaps)

        generatedShips.add(randomShip)
    }

    // Generate vertical ships

    return generatedShips
}



