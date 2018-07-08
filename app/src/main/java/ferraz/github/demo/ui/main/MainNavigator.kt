package ferraz.github.demo.ui.main

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import ferraz.github.demo.api.models.Repo
import ferraz.github.demo.databinding.ListItemBinding

class MainNavigator {

    companion object {
        const val IMAGE_URL = "IMAGE_URL"
        const val SUBS_URL = "SUBS_URL"
        const val ITEM_NAME = "ITEM_TITLE"
        const val ITEM_SUBTITLE = "ITEM_SUBTITLE"
    }

    var activity: MainActivity? = null
        set(value) {
            field = value
            activity?.repoClicked = { view, item ->
                showItemDetails(view, item)
            }
        }

    private fun showItemDetails(view: ListItemBinding, item: Repo) {

        val act = activity
        if (act is Activity) {
            val intent = Intent(activity, ItemDetailsActivity::class.java)
            intent.putExtra(IMAGE_URL, item.owner.avatar_url)
            intent.putExtra(SUBS_URL, item.subscribers_url)
            intent.putExtra(ITEM_NAME, item.name)
            intent.putExtra(ITEM_SUBTITLE, item.description)

            val avatar = Pair<View, String>(view.avatar, "item_avatar")
            val title = Pair<View, String>(view.title, "item_title")
            val subtitle = Pair<View, String>(view.subtitle, "item_subtitle")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, avatar, title, subtitle)
            act.startActivity(intent, options.toBundle())
        }
    }
}