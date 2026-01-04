package com.tools.module.wright

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import cn.tools.module.R


class RotateDragView : View {
    private var startAngle = 0f
    private var currentAngle = 0f
    private var touchX = 0f
    private var touchY = 0f
    private var isRotating = false
    private var paint: Paint? = null
    private lateinit var pointer: Bitmap
    var callback:((Float)->Unit)?=null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, set: AttributeSet?) : super(context, set) {
        init()
    }

    private fun init() {
        paint = Paint()
        //        paint.setColor(0xFF0000FF); // 蓝色
        paint!!.style = Paint.Style.FILL
        pointer = BitmapFactory.decodeResource(resources, R.mipmap.ic_protractor_pointer)
        val width = pointer.getWidth()
        val height = pointer.getHeight()
        Log.i("LZ_TEST", "init: width:" + width + "---height:" + pointer.getHeight())
    }

    fun reset() {
        currentAngle = 0f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制一个圆，圆的中心位于视图的中心
        val centerX = width / 2
        val centerY = height / 2
        //        int radius = Math.min(centerX, centerY);
//        canvas.drawCircle(centerX, centerY, radius, paint);

        // 根据当前角度旋转
        canvas.rotate(currentAngle, centerX.toFloat(), centerY.toFloat())
        Log.i("LZ_TEST", "onDraw: currentAngle$currentAngle---startAngle:$startAngle")
        // 绘制一个矩形，用作拖动的手柄
//        RectF rect = new RectF(centerX - 50, centerY - 25, centerX + 50, centerY + 25);
//        canvas.drawRect(rect, paint);
        val div = centerY * 14f / 316
        canvas.drawBitmap(pointer!!, null, RectF(centerX - div, 0f, centerX + div, centerY * 1f), paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchX = x
                touchY = y
                startAngle = getAngle(touchX, touchY, (width / 2).toFloat(), (height / 2).toFloat())
                isRotating = true
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (isRotating) {
                    val newAngle = getAngle(x, y, (width / 2).toFloat(), (height / 2).toFloat())
                    currentAngle += newAngle - startAngle
                    startAngle = newAngle
                    callback?.invoke(currentAngle)
                    invalidate() // 触发重绘
                }
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isRotating = false
                return true
            }
        }
        return false
    }

    // 计算触点相对于圆心的角度
    private fun getAngle(x: Float, y: Float, centerX: Float, centerY: Float): Float {
        val angle = Math.atan2((y - centerY).toDouble(), (x - centerX).toDouble())
        return Math.toDegrees(angle).toFloat()
    }
}