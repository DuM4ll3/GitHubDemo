package ferraz.github.demo.ui.main

import ferraz.github.demo.api.models.Repo
import ferraz.github.demo.databinding.ListItemBinding

interface MainActivityView {
    val repoClicked: (ListItemBinding, Repo) -> Unit
}