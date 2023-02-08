package team.standalone.core.common.extension

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import com.google.android.material.imageview.ShapeableImageView

fun ShapeableImageView.toGrayscale() {
    val matrix = ColorMatrix().apply {
        setSaturation(0f)
    }
    colorFilter = ColorMatrixColorFilter(matrix)
}