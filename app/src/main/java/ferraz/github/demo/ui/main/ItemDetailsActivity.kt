package ferraz.github.demo.ui.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import ferraz.github.demo.R
import ferraz.github.demo.ui.adapters.RepoSubscriberAdapter
import ferraz.github.demo.viewModels.RepoViewModel
import kotlinx.android.synthetic.main.activity_item_details.*
import java.lang.Exception

class ItemDetailsActivity : AppCompatActivity() {

    private var imageUrl = ""
    private val repoViewModel = RepoViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        imageUrl = intent.extras.getString(MainNavigator.IMAGE_URL)
        val fullName = intent.extras.getString(MainNavigator.ITEM_FULL_NAME)

        recyclerView.layoutManager = LinearLayoutManager(this)

        repoViewModel.getRepoSubscribers(fullName)
        repoViewModel.onRepoSubscribersResult = {
            recyclerView.adapter = RepoSubscriberAdapter(it)
            collapsingToolbar.title = collapsingToolbar.title.toString() + " #${it.size} subs"
        }

        renderUI()
    }

    private fun renderUI() {
        collapsingToolbar.title = intent.extras.getString(MainNavigator.ITEM_NAME)
        description.text = intent.extras.getString(MainNavigator.ITEM_SUBTITLE)

        renderBanner()
    }

    private fun renderBanner() {
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
