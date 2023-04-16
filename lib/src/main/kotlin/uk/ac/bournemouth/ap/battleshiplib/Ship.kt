package uk.ac.bournemouth.ap.battleshiplib

import uk.ac.bournemouth.ap.lib.matrix.int.IntMatrix
import uk.ac.bournemouth.ap.lib.matrix.Matrix
import uk.ac.bournemouth.ap.lib.matrix.MutableMatrix
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

interface Ship {
    //
    val top: Int
    val left: Int
    val bottom: Int
    val right: Int

    val columnIndices: IntRange get() = left..right
    val rowIndices: IntRange get() = top..bottom
    //If ship is place on the grid horizontally it would be in the range ship.size - 0
    // The top and bottom and top would both be 0 + 1
    val width: Int get() = right - left + 1
    //You do the reverse for vertical placed ships
    val height: Int get() = bottom - top + 1
    //you would always times the primary value by 1
    val size: Int get() = width*height
    // I don't think this is incredibly important
    val topLeft: Coordinate get() = Coordinate(top, left)
    val bottomRight: Coordinate get() = Coordinate(bottom, right)
}

inline fun Ship.forEachIndex(action: (Int, Int) -> Unit) {
    for (x in columnIndices) {
        for (y in rowIndices) {
            action(x, y)
        }
    }
}

inline fun Ship.mapIndices(action: (Int, Int) -> Int): IntMatrix {
    return IntMatrix(width, height) { mx, my ->
        action(mx+left, my+top)
    }
}

inline fun <T> Ship.mapIndices(action: (Int, Int) -> T): Matrix<T> {
    return MutableMatrix(width, height) { mx, my ->
        action(mx+left, my+top)
    }
}
