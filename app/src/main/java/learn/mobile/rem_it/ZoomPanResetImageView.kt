package learn.mobile.rem_it

import android.content.Context
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

class ZoomPanResetImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    //REM: Matrices: baseMatrix fits the image, gestureMatrix holds user modifications,
    //REM: and currentMatrix is the combined transformation.
    private val baseMatrix = Matrix()
    private val gestureMatrix = Matrix()
    private val currentMatrix = Matrix()

    //REM: Scale factor relative to the baseMatrix (1.0 = initial fit)
    private var currentScale = 1.0f
    private val minScale = 1.0f
    private val maxScale = 3.0f

    //REM: For panning
    private var lastX = 0f
    private var lastY = 0f
    private var mode = Mode.NONE

    private enum class Mode {
        NONE, DRAG, ZOOM
    }

    //REM: ScaleGestureDetector to handle pinch zoom.
    private val scaleDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var factor = detector.scaleFactor
            //REM: Calculate new scale and clamp it.
            val newScale = currentScale * factor
            if (newScale < minScale) {
                factor = minScale / currentScale
            } else if (newScale > maxScale) {
                factor = maxScale / currentScale
            }
            currentScale *= factor
            //REM: Apply scaling to gestureMatrix at the focal point.
            gestureMatrix.postScale(factor, factor, detector.focusX, detector.focusY)
            updateImageMatrix()
            return true
        }
    })

    //REM: GestureDetector for double-tap to reset and other simple gestures.
    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            resetImage()
            return true
        }
    })

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                mode = Mode.DRAG
            }
            MotionEvent.ACTION_MOVE -> {
                if (mode == Mode.DRAG) {
                    val dx = event.x - lastX
                    val dy = event.y - lastY
                    gestureMatrix.postTranslate(dx, dy)
                    lastX = event.x
                    lastY = event.y
                    updateImageMatrix()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                mode = Mode.NONE
            }
        }
        return true
    }

    private fun updateImageMatrix() {
        //REM: Combine the base matrix (which fits the image) with the gesture matrix (user interactions)
        currentMatrix.set(baseMatrix)
        currentMatrix.postConcat(gestureMatrix)
        imageMatrix = currentMatrix
    }

    private fun resetImage() {
        //REM: Reset user modifications and restore the default fit.
        gestureMatrix.reset()
        currentScale = 1.0f
        updateImageMatrix()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //REM: When the view's size is known, compute the baseMatrix so that the image fits (like fitCenter).
        val drawable: Drawable? = drawable
        if (drawable != null) {
            val dWidth = drawable.intrinsicWidth
            val dHeight = drawable.intrinsicHeight
            val scale = min(w / dWidth.toFloat(), h / dHeight.toFloat())
            val dx = (w - dWidth * scale) / 2f
            val dy = (h - dHeight * scale) / 2f

            baseMatrix.reset()
            baseMatrix.setScale(scale, scale)
            baseMatrix.postTranslate(dx, dy)

            //REM: Reset any gesture modifications.
            gestureMatrix.reset()
            currentScale = 1.0f

            updateImageMatrix()
        }
    }
}
