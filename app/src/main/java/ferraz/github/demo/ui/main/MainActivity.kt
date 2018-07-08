package ferraz.github.demo.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.jakewharton.rxbinding2.widget.queryTextChanges
import ferraz.github.demo.R
import ferraz.github.demo.api.models.Repo
import ferraz.github.demo.databinding.ListItemBinding
import ferraz.github.demo.ui.adapters.RepoAdapter
import ferraz.github.demo.viewModels.RepoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MainActivityView {

    override var repoClicked: (ListItemBinding, Repo) -> Unit = { view, item -> }

    private val navigator = MainNavigator()
    private val repoViewModel = RepoViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.activity = this

        searchView.queryTextChanges()
                .skip(1)
                .map { it.toString() }
//                .doOnNext { Log.d("SearchView", "Loading results...") }
                .debounce(500, TimeUnit.MILLISECONDS)
//                .doOnEach { Log.d("SearchView", "Done") }
                .distinctUntilChanged()
                .subscribe { repoViewModel.searchRepos(it) }

        listView.layoutManager = LinearLayoutManager(this)

        repoViewModel.getList = {
            listView.requestFocus()

            val adapter = RepoAdapter(it)
            listView.adapter = adapter
            adapter.itemClicked = { view, item ->
                repoClicked(view, item)
            }
        }
        repoViewModel.failure = { Log.d("Error", it.localizedMessage) }
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.activity = null
    }
}