package ferraz.github.demo.ui.adapters

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ferraz.github.demo.api.models.Repo
import ferraz.github.demo.databinding.ListItemBinding

interface RepoAdapterImpl {
    var itemClicked: (ListItemBinding, Repo) -> Unit
}

class RepoAdapter(val data: List<Repo>): RecyclerView.Adapter<RepoAdapter.RepoViewHolder>(), RepoAdapterImpl {

    override var itemClicked: (ListItemBinding, Repo) -> Unit = { view, item -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = ListItemBinding.inflate(layoutInflater, parent, false)

        return RepoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class RepoViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { itemClicked(binding, data[layoutPosition]) }
        }

        fun bind(data: Repo) {
            binding.repo = data
            binding.executePendingBindings()
        }
    }
}

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:src")
    fun loadImage(view: ImageView, url: String) {
        Picasso.get()
                .load(url)
                .into(view)
    }
}