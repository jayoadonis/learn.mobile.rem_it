package learn.mobile.rem_it

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView

class ZoomableImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    private var scaleFactor = 1.0f
    private val minScale = 0.5f
    private val maxScale = 3.0f

    // Use ScaleGestureDetector to detect pinch gestures.
    private val scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // Multiply the current scale factor by the gesture's scale factor.
            scaleFactor *= detector.scaleFactor
            // Limit the scale factor to the defined range.
            scaleFactor = scaleFactor.coerceIn(minScale, maxScale)
            // Apply scaling to the ImageView.
            scaleX = scaleFactor
            scaleY = scaleFactor
            return true
        }
    })

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Pass all touch events to the ScaleGestureDetector.
        scaleGestureDetector.onTouchEvent(event)
        // Return true to indicate the event was handled.
        return true
    }
}
