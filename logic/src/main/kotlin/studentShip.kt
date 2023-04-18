import uk.ac.bournemouth.ap.battleshiplib.Ship

class StudentShip(override val top: Int, override val left: Int,override val bottom: Int,override val right: Int):Ship {
//For horizontal ships the top and bottom values stay the same, while the left to right values are different
// For vertical ships the left and right values stay same while the top and bottom values are different
    override val columnIndices: IntRange get() = left..right
    override val rowIndices: IntRange get() = top..bottom
    //If ship is place on the grid horizontally it would be in the range ship.size - 0
    // The top and bottom and top would both be 0 + 1
    override val width: Int get() = right - left + 1
    //You do the reverse for vertical placed ships
    override val height: Int get() = bottom - top + 1
    //you would always times the primary value by 1
    override val size: Int get() = width*height
}