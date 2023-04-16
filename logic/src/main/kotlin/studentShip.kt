import uk.ac.bournemouth.ap.battleshiplib.Ship

class StudentShip(override val top: Int, override val left: Int,override val bottom: Int,override val right: Int):Ship {
//For horizontal ships the top and bottom values stay the same, while the left to right values are different
// For vertical ships the left and right values stay same while the top and bottom values are different
    enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }

    override val columnIndices: IntRange get() = left..right
    override val rowIndices: IntRange get() = top..bottom

    override val width: Int get() = right - left + 1
    override val height: Int get() = bottom - top + 1
    override val size: Int get() = width*height



}