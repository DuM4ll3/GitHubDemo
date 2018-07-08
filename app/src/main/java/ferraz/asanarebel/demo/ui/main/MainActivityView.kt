package ferraz.asanarebel.demo.ui.main

import ferraz.asanarebel.demo.api.models.Repo
import ferraz.asanarebel.demo.databinding.ListItemBinding

interface MainActivityView {
    val repoClicked: (ListItemBinding, Repo) -> Unit
}