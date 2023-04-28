package uk.ac.bournemouth.ap.battleships

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.battleshiplogic.MyBattleShipOpponent
import uk.ac.bournemouth.ap.battleshiplogic.StudentBattleShipGrid
import uk.ac.bournemouth.ap.battleshiplogic.StudentShip
//Constants for grid size and spacing
private val COL_COUNT get() = 10
private val ROW_COUNT get() = 10
private var cellSize: Float = 0f
private var cellSpacing: Float = 0f
private var cellSpacingRatio: Float = 0.1f


//Paint Objects for drawing the grid and cells
private var gridPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL
    color = Color.GRAY
}

private var shipPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL
    color = Color.YELLOW
}

private var emptyCellPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL
    color = Color.WHITE
}

private var hitPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL
    color = Color.RED
}

private var missPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL
    color = Color.WHITE
}

private var selectedPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL
    color = Color.BLUE
}



// A custom view for displaying the game grid
public class GridView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(
                context,
                attrs,
                defStyleAttr)

    // Called when the size of the view changes
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recalculateDimensions(w,h)
    }
    // Draws the grid and cells on the canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Calculate the size and position of the grid
        val gridLeft: Float = 0f
        val gridTop: Float = 0f
        val gridRight: Float = gridLeft + COL_COUNT * (cellSize + cellSpacing) + cellSpacing
        val gridBottom: Float = gridTop + ROW_COUNT * (cellSize + cellSpacing) + cellSpacing
        // Draw the grid background
        canvas.drawRect(gridLeft, gridTop, gridRight, gridBottom, gridPaint)
        // Iterate over the rows and columns of the grid
        for (row in 0 until ROW_COUNT){
            // Calculate the y position of the current row
            val cy = gridTop + cellSpacing + ((cellSize + cellSpacing) * row) + cellSize / 2f
            for(col in 0 until COL_COUNT){

                val paint =  emptyCellPaint

                val cx = gridLeft + cellSpacing + ((cellSize + cellSpacing) * col) + cellSize / 2f

                canvas.drawRect(
                    cx - cellSize / 2f,
                    cy - cellSize / 2f,
                    cx + cellSize / 2f,
                    cy + cellSize / 2f,
                    paint
                )


            }

        }


    }
    /**
    Recalculates the size of the cells and the spacing between cells based on the width and height of the view.
    If no parameters are passed, it uses the current width and height of the view.
    @param w The width of the view.
    @param h The height of the view.
     */
    private fun recalculateDimensions(w: Int = width, h: Int = height){
        val cellSizeX = w / (COL_COUNT + (COL_COUNT + 1) * cellSpacingRatio)
        val cellSizeY = h / (ROW_COUNT + (ROW_COUNT + 1) * cellSpacingRatio)
        cellSize = minOf(cellSizeX, cellSizeY)
        cellSpacing = cellSize * cellSpacingRatio
    }

    /**
    The current game state represented by a StudentBattleShipGrid.
     */
    var game: StudentBattleShipGrid = StudentBattleShipGrid(opponent =  MyBattleShipOpponent())
        set(value) {
            field = value
            // After the new value is set, make sure to recalculate sizes and then trigger a redraw
            recalculateDimensions()
            invalidate()
        }

    private val colCount:Int get() = game.columns
    private val rowCount:Int get() = game.rows

    /**A gesture detector to detect user input on the view.*/

    private val gestureDetector = GestureDetectorCompat(context, object:
        GestureDetector.SimpleOnGestureListener() {
        /**Handles the down motion event.*/
        override fun onDown(e: MotionEvent): Boolean = true
        /**Handles the single tap up motion event.*/
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val columnTouched = ((e.x - cellSpacing * 0.5f) / (cellSpacing + cellSize)).toInt()
            val rowTouched = ((e.y - cellSpacing * 0.5f) / (cellSpacing + cellSize)).toInt()
            if (columnTouched in 0 until game.columns && rowTouched in 0 until game.rows) {
                try {
                    val result = game.shootAt(columnTouched, rowTouched)
                    invalidate()
                    // Handle the result of the shot
                    when (result) {
                        is GuessResult.MISS -> {
                            // Show a miss animation or message
                        }
                        is GuessResult.HIT -> {
                            // Show a hit animation or message
                        }
                        is GuessResult.SUNK -> {
                            // Show a sunk animation or message
                        }
                    }
                    return true
                } catch (e: IllegalArgumentException) {
                    // Handle an invalid cell error
                } catch (e: Exception) {
                    // Handle a duplicate move error
                }
            }
            return false
        }
    })

}