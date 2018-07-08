package ferraz.github.demo.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ferraz.github.demo.api.models.RepoSubscriber
import ferraz.github.demo.databinding.ListItemSubscriberBinding

class RepoSubscriberAdapter(val data: List<RepoSubscriber>): RecyclerView.Adapter<RepoSubscriberAdapter.RepoSubscriberViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoSubscriberViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemSubscriberBinding = ListItemSubscriberBinding.inflate(layoutInflater, parent, false)

        return RepoSubscriberViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: RepoSubscriberViewHolder, position: Int) =  holder.bind(data[position])

    inner class RepoSubscriberViewHolder(private val binding: ListItemSubscriberBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RepoSubscriber) {
            binding.subscriber = data
            binding.executePendingBindings()
        }
    }
}