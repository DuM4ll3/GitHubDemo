package ferraz.github.demo.viewModels

import ferraz.github.demo.api.models.Repo
import ferraz.github.demo.api.models.RepoResponse
import ferraz.github.demo.api.models.RepoSubscriber
import ferraz.github.demo.managers.ManagerProvider
import ferraz.github.demo.managers.RepoManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RepoViewModelImpl {
    var onReposResult: (List<Repo>) -> Unit
    var onRepoSubscribersResult: (List<RepoSubscriber>) -> Unit
    var onFailure: (Throwable) -> Unit
}

class RepoViewModel(private val manager: RepoManager = ManagerProvider.provideRepoManager()) : RepoViewModelImpl {

    override var onReposResult: (List<Repo>) -> Unit = {}
    override var onRepoSubscribersResult: (List<RepoSubscriber>) -> Unit = {}
    override var onFailure: (Throwable) -> Unit = {}

    fun searchRepos(name: String) {
        manager.listRepos(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(::handleResult, ::handleError)
    }

    fun getRepoSubscribers(repoFullName: String) {
        val login = repoFullName.split("/")[0]
        val name = repoFullName.split("/")[1]

        manager.listRepoSubs(login, name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(::handleResult, ::handleError)
    }

    private fun handleResult(items: List<RepoSubscriber>) {
        onRepoSubscribersResult(items)
    }

    private fun handleResult(result: RepoResponse) {
        onReposResult(result.items)
    }

    private fun handleError(error: Throwable) {
        onFailure(error)
    }
}