package team.standalone.core.ui.bindingadapter

import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import team.standalone.core.common.extension.toGrayscale
import team.standalone.core.ui.util.BindingAdapterUtil
import java.io.File

object ShapeableImageViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.APP_TINT
        ]
    )
    fun bindTint(shapeableImageView: ShapeableImageView, @ColorInt color: Int) {
        shapeableImageView.setColorFilter(color)
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.IMAGE_URL,
            BindingAdapterUtil.IMAGE_PlACEHOLDER,
            BindingAdapterUtil.IMAGE_ERROR
        ],
        requireAll = true
    )
    fun bindImageUrl(
        shapeableImageView: ShapeableImageView,
        imageUrl: String?,
        imagePlaceholder: Drawable?,
        imageError: Drawable?,
    ) {
        Glide.with(shapeableImageView.context)
            .load(imageUrl)
            .placeholder(imagePlaceholder)
            .error(imageError)
            .into(shapeableImageView)
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.IMAGE_URL,
            BindingAdapterUtil.IMAGE_PlACEHOLDER,
            BindingAdapterUtil.IMAGE_ERROR,
            BindingAdapterUtil.IMAGE_ENABLED
        ],
        requireAll = true
    )
    fun bindImageUrl(
        shapeableImageView: ShapeableImageView,
        imageUrl: String?,
        imagePlaceholder: Drawable?,
        imageError: Drawable?,
        imageEnabled: Boolean,
    ) {
        Glide.with(shapeableImageView.context)
            .load(imageUrl)
            .placeholder(imagePlaceholder)
            .error(imageError)
            .into(shapeableImageView)

        if (imageEnabled) {
            shapeableImageView.colorFilter = null
        } else {
            shapeableImageView.toGrayscale()
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.IMAGE_FILE,
            BindingAdapterUtil.IMAGE_PlACEHOLDER,
            BindingAdapterUtil.IMAGE_ERROR,
        ],
        requireAll = true
    )
    fun bindImageFile(
        shapeableImageView: ShapeableImageView,
        imageFile: File?,
        imagePlaceholder: Drawable?,
        imageError: Drawable?,
    ) {
        Glide.with(shapeableImageView.context)
            .load(imageFile)
            .placeholder(imagePlaceholder)
            .error(imageError)
            .into(shapeableImageView)
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.IMAGE_FILE,
            BindingAdapterUtil.IMAGE_PlACEHOLDER,
            BindingAdapterUtil.IMAGE_ERROR,
            BindingAdapterUtil.IMAGE_ENABLED
        ],
        requireAll = true
    )
    fun bindImageFile(
        shapeableImageView: ShapeableImageView,
        imageFile: File?,
        imagePlaceholder: Drawable?,
        imageError: Drawable?,
        imageEnabled: Boolean
    ) {
        Glide.with(shapeableImageView.context)
            .load(imageFile)
            .placeholder(imagePlaceholder)
            .error(imageError)
            .into(shapeableImageView)

        if (imageEnabled) {
            shapeableImageView.colorFilter = null
        } else {
            shapeableImageView.toGrayscale()
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.IMAGE_DRAWABLE,
            BindingAdapterUtil.IMAGE_PlACEHOLDER,
            BindingAdapterUtil.IMAGE_ERROR,
        ],
        requireAll = true
    )
    fun bindImageDrawable(
        shapeableImageView: ShapeableImageView,
        imageDrawable: Drawable?,
        imagePlaceholder: Drawable?,
        imageError: Drawable?,
    ) {
        Glide.with(shapeableImageView.context)
            .load(imageDrawable)
            .placeholder(imagePlaceholder)
            .error(imageError)
            .into(shapeableImageView)
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.IMAGE_DRAWABLE,
            BindingAdapterUtil.IMAGE_PlACEHOLDER,
            BindingAdapterUtil.IMAGE_ERROR,
            BindingAdapterUtil.IMAGE_ENABLED,
        ],
        requireAll = true
    )
    fun bindImageDrawable(
        shapeableImageView: ShapeableImageView,
        imageDrawable: Drawable?,
        imagePlaceholder: Drawable?,
        imageError: Drawable?,
        imageEnabled: Boolean,
    ) {
        Glide.with(shapeableImageView.context)
            .load(imageDrawable)
            .placeholder(imagePlaceholder)
            .error(imageError)
            .into(shapeableImageView)

        if (imageEnabled) {
            shapeableImageView.colorFilter = null
        } else {
            shapeableImageView.toGrayscale()
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.RESOURCE_ID,
            BindingAdapterUtil.RESOURCE_PLACEHOLDER,
            BindingAdapterUtil.RESOURCE_ERROR,
            BindingAdapterUtil.RESOURCE_ENABLED,
        ],
        requireAll = true
    )
    fun bindResourceId(
        shapeableImageView: ShapeableImageView,
        resourceId: Int?,
        resourcePlaceholder: Drawable?,
        resourceError: Drawable?,
        resourceEnabled: Boolean,
    ) {
        Glide.with(shapeableImageView.context)
            .load(resourceId)
            .placeholder(resourcePlaceholder)
            .error(resourceError)
            .into(shapeableImageView)

        if (resourceEnabled) {
            shapeableImageView.colorFilter = null
        } else {
            shapeableImageView.toGrayscale()
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            BindingAdapterUtil.IMAGE_BASE64,
            BindingAdapterUtil.IMAGE_PlACEHOLDER,
            BindingAdapterUtil.IMAGE_ERROR,
            BindingAdapterUtil.IMAGE_ENABLED,
        ],
        requireAll = true
    )
    fun bindImageBase64(
        shapeableImageView: ShapeableImageView,
        imageBase64: String?,
        imagePlaceholder: Drawable?,
        imageError: Drawable?,
        imageEnabled: Boolean,
    ) {
        if (imageBase64 != null) {
            val imageByteArray = Base64.decode(imageBase64, Base64.DEFAULT)
            Glide.with(shapeableImageView.context)
                .load(imageByteArray)
                .placeholder(imagePlaceholder)
                .error(imageError)
                .into(shapeableImageView)
        } else {
            Glide.with(shapeableImageView.context)
                .load(imagePlaceholder)
                .placeholder(imagePlaceholder)
                .error(imageError)
                .into(shapeableImageView)
        }

        if (imageEnabled) {
            shapeableImageView.colorFilter = null
        } else {
            shapeableImageView.toGrayscale()
        }
    }
}