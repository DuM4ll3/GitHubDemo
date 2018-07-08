package ferraz.github.demo.viewModels

import ferraz.github.demo.api.models.Repo
import ferraz.github.demo.api.models.RepoResponse
import ferraz.github.demo.managers.ManagerProvider
import ferraz.github.demo.managers.RepoManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RepoViewModelImpl {
    var onResult: (List<Repo>) -> Unit
    var onFailure: (Throwable) -> Unit
}

class RepoViewModel(private val manager: RepoManager = ManagerProvider.provideRepoManager()) : RepoViewModelImpl {

    override var onResult: (List<Repo>) -> Unit = {}
    override var onFailure: (Throwable) -> Unit = {}

    fun searchRepos(name: String) {
        manager.listRepos(name, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(::handleResult, ::handleError)
    }

    private fun handleResult(result: RepoResponse) {
        onResult(result.items)
    }

    private fun handleError(error: Throwable) {
        onFailure(error)
    }
}