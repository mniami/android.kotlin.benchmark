package android.benchmark.ui.fragments.volunteer.details.project

import android.benchmark.R
import android.benchmark.domain.ImageMetadata
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ProjectImageAdapter(val context: Context, val layoutInflater: LayoutInflater, val onClickListener: (View, ImageMetadata) -> Unit) {
    fun instantiateItem(container: ViewGroup, image: ImageMetadata): Any {
        val itemView = layoutInflater.inflate(R.layout.project_image_item, container, false)

        itemView?.let {
            var imageView = it.findViewById(R.id.image_view) as ImageView
            imageView?.let {
                it.tag = image
                it.setOnClickListener { onClickListener.invoke(it, it.tag as ImageMetadata) }
                Picasso.with(it.context).load(image.url).into(it)
            }
        }

        container.addView(itemView)

        return itemView
    }
}