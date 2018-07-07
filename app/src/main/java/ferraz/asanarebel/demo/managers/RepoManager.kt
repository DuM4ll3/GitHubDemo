package ferraz.asanarebel.demo.managers

import ferraz.asanarebel.demo.api.GithubService
import ferraz.asanarebel.demo.api.models.RepoResponse
import io.reactivex.Observable

class RepoManager(private val service: GithubService = GithubService.create()) {

    fun listRepos(name: String, page: Int): Observable<RepoResponse> {
        return service.searchRepos(name, page, 15)
    }
}