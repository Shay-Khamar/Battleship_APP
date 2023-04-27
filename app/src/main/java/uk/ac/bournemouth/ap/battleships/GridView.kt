package uk.ac.bournemouth.ap.battleships

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import uk.ac.bournemouth.ap.battleshiplib.GuessCell

private val COL_COUNT get() = 10
private val ROW_COUNT get() = 10
private var cellSize: Float = 0f
private var cellSpacing: Float = 0f
private var cellSpacingRatio: Float = 0.1f



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




public class GridView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(
                context,
                attrs,
                defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recalculateDimensions(w,h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val gridLeft: Float = 0f
        val gridTop: Float = 0f
        val gridRight: Float = gridLeft + COL_COUNT * (cellSize + cellSpacing) + cellSpacing
        val gridBottom: Float = gridTop + ROW_COUNT * (cellSize + cellSpacing) + cellSpacing
        canvas.drawRect(gridLeft, gridTop, gridRight, gridBottom, gridPaint)

        for (row in 0 until ROW_COUNT){

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

    private fun recalculateDimensions(w: Int = width, h: Int = height){
        val cellSizeX = w / (COL_COUNT + (COL_COUNT + 1) * cellSpacingRatio)
        val cellSizeY = h / (ROW_COUNT + (ROW_COUNT + 1) * cellSpacingRatio)
        cellSize = minOf(cellSizeX, cellSizeY)
        cellSpacing = cellSize * cellSpacingRatio
    }
}