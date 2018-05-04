package imdb.assignment.umesh0492.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide


class BindingUtils private constructor() {

    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String) {
        val context = imageView.context
        Glide.with(context).load(url).into(imageView)
    }
}
