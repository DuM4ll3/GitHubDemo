package ferraz.github.demo.viewModels

import ferraz.github.demo.api.models.RepoResponse
import ferraz.github.demo.api.models.RepoSubscriber
import ferraz.github.demo.managers.ManagerProvider
import ferraz.github.demo.managers.RepoManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RepoViewModelImpl {
    var onRepoSubscribersResult: (List<RepoSubscriber>) -> Unit
    var onFailure: (Throwable) -> Unit
}

class RepoViewModel(private val manager: RepoManager = ManagerProvider.provideRepoManager()) : RepoViewModelImpl {

    override var onRepoSubscribersResult: (List<RepoSubscriber>) -> Unit = {}
    override var onFailure: (Throwable) -> Unit = {}

    /*
     * returning an observable to make use of the searchView's rx capabilities
     * for illustration purposes
     */
    fun searchRepos(name: String): Observable<RepoResponse> {
        return manager.listRepos(name)
    }

    /*
     * result/error being handled within the viewModel
     */
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

    private fun handleError(error: Throwable) {
        onFailure(error)
    }
}