import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.Ship

class myBattleShipOpponent(override val rows: Int, override val columns: Int, override val ships: List<StudentShip>): BattleshipOpponent {

    private val gridSize = rows * columns
    private val cells = Array(rows) { IntArray(columns) { 0 } }
    val ship = mutableListOf<StudentShip>()
    //Defines the ships lengths/sizes
    val shipSizes = listOf(5,4,3,3,2)

    fun randomShips( top: Int,  left: Int, bottom: Int, right: Int):List<StudentShip>{
        for(size in shipSizes){
            var randomShip: StudentShip? = null
            while(randomShip == null){
                //Generate a random orientation (horizontal or vertical)
                val isHorizontal = listOf(true,false).random()


                //Generate a random position for the ship
                val maxTop = if (isHorizontal) gridSize - 1 else gridSize - size
                val maxLeft = if (isHorizontal) gridSize - size else gridSize - 1
                val top = (0..maxTop).random()
                val left = (0..maxLeft).random()

                //Calculate the bottom and right positions base on the size and orientation
                val bottom = if (isHorizontal) top else top + size -1
                val right = if (isHorizontal) left + size - 1 else left

                //Check if the ship overlaps with any existing ships
                val overlaps = ships.any {s ->
                    (top..bottom). intersect(s.top..s.bottom).isNotEmpty()&&
                            (left..right).intersect(s.left..s.right).isNotEmpty()

                }

                if (!overlaps){
                    randomShip = StudentShip(top, left, bottom, right)
                }

            }
            ship.add(randomShip)
        }
        return ship

    }
    /** I don't want to hard code the the ship therefore I want to only give it the Top(row) value
     and depending on the orientation feed it the size(column)* */
    /**val ship = listOf(
        StudentShip(0, 0, 0, 4),/** a horizontal ship of length 5 at row 0, columns 0-4*/
        StudentShip(2, 3, 6, 3),/**a vertical ship of length 4 at rows 2-5, column 3*/
        StudentShip(4, 4,6,4),
        StudentShip(0, 6, 2, 6),
        StudentShip(3, 9,4, 9))*/

    val createOpponent = myBattleShipOpponent(10, 10, ship)

    override fun shipAt(column: Int, row: Int): BattleshipOpponent.ShipInfo<Ship>? {
        TODO("Not yet implemented")
    }


}