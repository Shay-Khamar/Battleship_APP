package uk.ac.bournemouth.ap.battleshiplogic
import uk.ac.bournemouth.ap.battleshiplib.Ship
class StudentShip(override val top: Int, override val left: Int,override val bottom: Int,override val right: Int):Ship {

    override val columnIndices: IntRange get() = left..right
    override val rowIndices: IntRange get() = top..bottom

}