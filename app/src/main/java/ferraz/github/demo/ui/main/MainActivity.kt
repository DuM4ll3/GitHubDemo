package ferraz.github.demo.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.jakewharton.rxbinding2.widget.queryTextChanges
import ferraz.github.demo.R
import ferraz.github.demo.api.models.Repo
import ferraz.github.demo.databinding.ListItemBinding
import ferraz.github.demo.ui.adapters.RepoAdapter
import ferraz.github.demo.utils.Utils
import ferraz.github.demo.viewModels.RepoViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MainActivityView {

    // this function shall be implemented in the MainNavigator
    override var repoClicked: (ListItemBinding, Repo) -> Unit = { view, item -> }

    private val navigator = MainNavigator()
    private val repoViewModel = RepoViewModel()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navigator.activity = this

        listView.layoutManager = LinearLayoutManager(this)
        setupSearch()
    }

    private fun setupSearch() {

        searchView.queryTextChanges()
                .skip(1)
                .filter { it.isNotBlank() }
                .map { it.toString() }
                .doOnNext {
                    searchText.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE
                }
                .debounce(800, TimeUnit.MILLISECONDS)
                // manipulate the UI on the mainThread because of the 'distinctUntilChanged'
                .observeOn(AndroidSchedulers.mainThread())
                // If the query is the same as previous just hide the progress
                .distinctUntilChanged { t1, t2 ->
                    progressBar.visibility = if (t1.contentEquals(t2)) View.INVISIBLE else View.VISIBLE
                    t1.contentEquals(t2)
                }
                .switchMap {
                    repoViewModel.searchRepos(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .doOnEach {
                    progressBar.visibility = View.GONE
                    Utils.hideKeyboard(this)
                }
                .subscribe { renderList(it.items) }
                .addTo(disposable)

        repoViewModel.onFailure = { Log.d("Error", it.localizedMessage) }
    }

    private fun renderList(items: List<Repo>) {
        noResultsView.visibility = if (items.isEmpty()) View.VISIBLE else View.INVISIBLE

        val adapter = RepoAdapter(items)
        listView.adapter = adapter

        adapter.itemClicked = { view, item ->
            // so the keyboard is not shown when navigating back from details activity
            listView.requestFocus()
            repoClicked(view, item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // avoid memory leaks
        navigator.activity = null
        disposable.clear()
    }
}