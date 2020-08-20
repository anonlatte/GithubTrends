package com.anonlatte.trends.ui.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.anonlatte.trends.R
import com.anonlatte.trends.databinding.ListItemRepositoryBinding
import com.anonlatte.trends.db.model.Repository

class RepositoriesAdapter :
    RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    private var repositoriesList = emptyList<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_repository, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repositoriesList[position]

        holder.itemView.setOnClickListener {
            openWebViewLink(holder.itemView.context, item.url)
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int = repositoriesList.size

    fun setRepositoriesList(repositories: List<Repository>) {
        repositoriesList = repositories
        notifyDataSetChanged()
    }

    private fun openWebViewLink(context: Context, link: String?) {
        if (link != null) {
            Navigation.findNavController(context as Activity, R.id.nav_host_fragment)
                .navigate(R.id.action_homeFragment_to_webViewFragment, bundleOf("link" to link))
        }
    }

    class ViewHolder(private val binding: ListItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository?) {
            binding.repository = repository
            binding.executePendingBindings()
        }
    }
}
