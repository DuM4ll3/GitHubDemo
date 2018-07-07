package ferraz.asanarebel.demo.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.jakewharton.rxbinding2.widget.queryTextChanges
import ferraz.asanarebel.demo.R
import ferraz.asanarebel.demo.ui.adapters.RepoAdapter
import ferraz.asanarebel.demo.viewModels.RepoViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val repoViewModel by lazy { RepoViewModel() }
    private val disposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposable.add(searchView.queryTextChanges()
                .skip(1)
                .map { it.toString() }
                .doOnNext { Log.d("SearchView", "Loading results...") }
                .debounce(500, TimeUnit.MILLISECONDS)
                .doOnEach { Log.d("SearchView", "Done") }
                .subscribe { repoViewModel.searchRepos(it) })


        listView.layoutManager = LinearLayoutManager(this)

        repoViewModel.getList = { listView.adapter = RepoAdapter(it) }
        repoViewModel.failure = { Log.d("Error", it.localizedMessage) }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}