package ferraz.asanarebel.demo.api.models

data class Repo (
        val name: String,
        val owner: Owner,
        val description: String,
        val forks_count: Int,
        val subscribers_url: String
) {
    data class Owner (
            val avatar_url: String
    )
}