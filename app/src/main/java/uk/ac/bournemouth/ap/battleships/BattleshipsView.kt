package uk.ac.bournemouth.ap.battleships
import android.view.View
import android.content.Context
import android.util.AttributeSet
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Typeface

private val colCount get() = 10
private val rowCount get() = 10
private val squareSize = 200f
private val margin = 50f
private val cellSize = (squareSize - margin * 2) / 10

private val squareColour: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL
    color = Color.BLUE
}



public class BattleshipsView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(
        context,
        attrs,
        defStyleAttr)

    override fun onSizeChanged(w: Int,  h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }




}