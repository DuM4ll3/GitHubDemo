package ferraz.asanarebel.demo.viewModels

import ferraz.asanarebel.demo.api.models.Repo
import ferraz.asanarebel.demo.api.models.RepoResponse
import ferraz.asanarebel.demo.managers.ManagerProvider
import ferraz.asanarebel.demo.managers.RepoManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RepoViewModelImpl {
    var getList: (List<Repo>) -> Unit
    var failure: (Throwable) -> Unit
}

class RepoViewModel(private val manager: RepoManager = ManagerProvider.provideRepoManager()) : RepoViewModelImpl {

    override var getList: (List<Repo>) -> Unit = {}
    override var failure: (Throwable) -> Unit = {}

    fun searchRepos(name: String) {
        manager.listRepos(name, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(::handleResult, ::handleError)
    }

    private fun handleResult(result: RepoResponse) {
        getList(result.items)
    }

    private fun handleError(error: Throwable) {
        failure(error)
    }
}