package ferraz.github.demo.managers

object ManagerProvider {

    fun provideRepoManager(): RepoManager {
        return RepoManager()
    }
}