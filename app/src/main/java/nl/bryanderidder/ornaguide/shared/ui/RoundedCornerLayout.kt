package nl.bryanderidder.ornaguide.shared.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.widget.FrameLayout


/**
 * Rounded corner custom view
 * @author Bryan de Ridder
 */
open class RoundedCornerLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private var cornerRadius = 15f
    private var borderWidth = 0f

    override fun draw(canvas: Canvas) {
        val halfBorderWidth = borderWidth / 2
        val count = canvas.save()
        val path = Path()
        if (borderWidth > 0f) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = borderWidth
        } else {
            // this prevents the border from removing anti-aliassing
            paint.color = (background as ColorDrawable).color
        }
        path.addRoundRect(
            RectF(
                halfBorderWidth,
                halfBorderWidth,
                width.toFloat() - halfBorderWidth,
                height.toFloat() - halfBorderWidth
            ),
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )
        canvas.drawPath(path, paint)
        canvas.clipPath(path)
        super.draw(canvas)
        canvas.restoreToCount(count)
    }
}