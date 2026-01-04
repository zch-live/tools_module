package com.tools.module.wright

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.tools.module.R
import java.math.BigDecimal
import java.math.RoundingMode

class RulerView(val mContext: Context, attr: AttributeSet) : View(mContext, attr) {

    private val divCount: Float //单位毫米多少像素点
    private val edgeLimit = dp2px(mContext, 5f)
    private val linePaint = Paint()
    private val txtPaint = Paint()
    private val bitmap: Bitmap
    var limitHeight = 0
    var viewWidth = 0

    init {
        val metrics = resources.displayMetrics
        divCount = metrics.densityDpi.toBigDecimal().divide(BigDecimal(25.4), 2, RoundingMode.DOWN)
            .toFloat()
        linePaint.color = Color.BLACK
        linePaint.isAntiAlias = true
        txtPaint.color = Color.BLACK
        txtPaint.isAntiAlias = true
        txtPaint.textSize = 20f * metrics.density
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_ruler_line)
    }

    fun getMinY(): Float {
        return edgeLimit - 1f
    }

    fun calculate(y: Float): String {
        try {
            return y.toBigDecimal()
                .divide(divCount.toBigDecimal().multiply(BigDecimal.TEN), 2, RoundingMode.DOWN)
                .toPlainString()
        } catch (e: Exception) {
            return "0.00"
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        limitHeight = bottom - top - edgeLimit
        viewWidth = right
    }

    override fun onDraw(canvas: Canvas) {
        var startH = edgeLimit.toFloat()
        var index = 0
        val viewRight = width
        val txtStart = viewRight - divCount * 12
        while (startH < limitHeight) {
            if (index % 10 == 0) {
                canvas.rotate(90f, txtStart, startH)
                canvas.drawText((index / 10).toString(), txtStart, startH, txtPaint)
                canvas.rotate(-90f, txtStart, startH)
                canvas.drawLine(
                    viewRight - divCount * 7,
                    startH - 0.5f,
                    viewRight.toFloat(),
                    startH + 0.5f,
                    linePaint
                )
            } else if (index % 5 == 0) {
                canvas.drawLine(
                    viewRight - divCount * 4.5f,
                    startH - 0.5f,
                    viewRight.toFloat(),
                    startH + 0.5f,
                    linePaint
                )
            } else {
                canvas.drawLine(
                    viewRight - divCount * 3,
                    startH - 0.5f,
                    viewRight.toFloat(),
                    startH + 0.5f,
                    linePaint
                )
            }
            startH += divCount
            index++
        }
//        canvas.drawBitmap(bitmap, imgX, imgY, imgPaint)
    }

    fun dp2px(context: Context, dpValue: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dpValue * density + 0.5f).toInt() // 加 0.5f 是为了四舍五入
    }
}