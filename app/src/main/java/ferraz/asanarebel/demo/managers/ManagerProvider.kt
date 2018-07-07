package ferraz.asanarebel.demo.managers

object ManagerProvider {

    fun provideRepoManager(): RepoManager {
        return RepoManager()
    }
}