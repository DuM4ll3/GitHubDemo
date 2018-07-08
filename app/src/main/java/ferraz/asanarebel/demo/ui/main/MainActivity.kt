package ferraz.asanarebel.demo.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.jakewharton.rxbinding2.widget.queryTextChanges
import ferraz.asanarebel.demo.R
import ferraz.asanarebel.demo.api.models.Repo
import ferraz.asanarebel.demo.databinding.ListItemBinding
import ferraz.asanarebel.demo.ui.adapters.RepoAdapter
import ferraz.asanarebel.demo.viewModels.RepoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MainActivityView {

    override var repoClicked: (ListItemBinding, Repo) -> Unit = { view, item -> }

    private val navigator = MainNavigator()
    private val repoViewModel = RepoViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(R.style.AppTheme)
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