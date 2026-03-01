package com.hashalbum.app.ui

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView

class ZoomableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        // Shared state so the activity can skip swipe-up-for-remark when zoomed
        var anyZoomed = false
    }

    private var currentScale = 1f
    private val maxScale = 5f

    var isZoomed = false
        private set

    var onSingleTap: (() -> Unit)? = null

    private val scaleGestureDetector = ScaleGestureDetector(context,
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                // Capture the current fitCenter matrix before switching to MATRIX mode
                if (scaleType != ScaleType.MATRIX) {
                    val m = Matrix(imageMatrix)
                    scaleType = ScaleType.MATRIX
                    imageMatrix = m
                }
                return true
            }

            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val factor = detector.scaleFactor
                val newScale = (currentScale * factor).coerceIn(1f, maxScale)
                val actual = newScale / currentScale
                currentScale = newScale

                val m = Matrix(imageMatrix)
                m.postScale(actual, actual, detector.focusX, detector.focusY)
                imageMatrix = constrainMatrix(m)

                val nowZoomed = currentScale > 1.05f
                if (nowZoomed != isZoomed) {
                    isZoomed = nowZoomed
                    anyZoomed = nowZoomed
                }
                parent?.requestDisallowInterceptTouchEvent(isZoomed)
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector) {
                if (currentScale <= 1.05f) resetToFitCenter()
            }
        }
    )

    private val gestureDetector = GestureDetector(context,
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                onSingleTap?.invoke()
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                if (isZoomed) {
                    resetToFitCenter()
                } else {
                    if (scaleType != ScaleType.MATRIX) {
                        val m = Matrix(imageMatrix)
                        scaleType = ScaleType.MATRIX
                        imageMatrix = m
                    }
                    val m = Matrix(imageMatrix)
                    m.postScale(2.5f, 2.5f, e.x, e.y)
                    imageMatrix = constrainMatrix(m)
                    currentScale = 2.5f
                    isZoomed = true
                    anyZoomed = true
                    parent?.requestDisallowInterceptTouchEvent(true)
                }
                return true
            }

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                if (!isZoomed) return false
                val m = Matrix(imageMatrix)
                m.postTranslate(-distanceX, -distanceY)
                imageMatrix = constrainMatrix(m)
                parent?.requestDisallowInterceptTouchEvent(true)
                return true
            }
        }
    )

    fun resetToFitCenter() {
        scaleType = ScaleType.FIT_CENTER
        currentScale = 1f
        isZoomed = false
        anyZoomed = false
        parent?.requestDisallowInterceptTouchEvent(false)
    }

    private fun constrainMatrix(m: Matrix): Matrix {
        val d = drawable ?: return m
        val dw = d.intrinsicWidth.toFloat()
        val dh = d.intrinsicHeight.toFloat()
        if (dw <= 0 || dh <= 0 || width == 0 || height == 0) return m

        val values = FloatArray(9)
        m.getValues(values)
        val sx = values[Matrix.MSCALE_X]
        val sy = values[Matrix.MSCALE_Y]
        var tx = values[Matrix.MTRANS_X]
        var ty = values[Matrix.MTRANS_Y]
        val scaledW = dw * sx
        val scaledH = dh * sy

        // Keep image centered if smaller than view, otherwise clamp to edges
        tx = if (scaledW <= width) (width - scaledW) / 2f
             else tx.coerceIn(width - scaledW, 0f)
        ty = if (scaledH <= height) (height - scaledH) / 2f
             else ty.coerceIn(height - scaledH, 0f)

        values[Matrix.MTRANS_X] = tx
        values[Matrix.MTRANS_Y] = ty
        m.setValues(values)
        return m
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        // Skip single-gesture detector while a pinch is in progress
        if (!scaleGestureDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        }
        return true
    }
}
