package ferraz.github.demo.managers

import ferraz.github.demo.api.GithubService
import ferraz.github.demo.api.models.RepoResponse
import ferraz.github.demo.api.models.RepoSubscriber
import io.reactivex.Observable

class RepoManager(private val service: GithubService = GithubService.create()) {

    fun listRepos(name: String, page: Int): Observable<RepoResponse> {
        return service.searchRepos(name, page, 15)
    }

    fun listRepoSubs(login: String, name: String): Observable<List<RepoSubscriber>> {
        return service.getRepoSubs(login, name)
    }
}