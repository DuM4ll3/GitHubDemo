package ferraz.asanarebel.demo.ui.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import ferraz.asanarebel.demo.R
import kotlinx.android.synthetic.main.activity_item_details.*
import java.lang.Exception

class ItemDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val imageUrl = intent.extras.getString(MainNavigator.IMAGE_URL)
        val subsUrl = intent.extras.getString(MainNavigator.SUBS_URL)

        collapsing_toolbar.title = intent.extras.getString(MainNavigator.ITEM_NAME)
        description.text = intent.extras.getString(MainNavigator.ITEM_SUBTITLE)
        // Picasso keeps a weak reference to the target so it needs to be stored in a field
        val target = object: Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                banner.setImageBitmap(bitmap)
            }
        }

        Picasso.get()
                .load(imageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(target)
    }
}
