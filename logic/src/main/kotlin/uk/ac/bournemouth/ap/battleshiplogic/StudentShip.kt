package uk.ac.bournemouth.ap.battleshiplogic
import uk.ac.bournemouth.ap.battleshiplib.Ship
/**Defines the StudentShip class that  implement the Ship interface
 * Uese soley to create StudentShip Elements for the ships list*/
class StudentShip(
    /** Override the top, left, bottom, and right properties from the Ship interface*/
    override val top: Int,
    override val left: Int,
    override val bottom: Int,
    override val right: Int
) : Ship {

    /**Override the columnIndices property to return a range of column indices from left to right*/
    override val columnIndices: IntRange
        get() = left..right

    /**Override the rowIndices property to return a range of row indices from top to bottom*/
    override val rowIndices: IntRange
        get() = top..bottom
}