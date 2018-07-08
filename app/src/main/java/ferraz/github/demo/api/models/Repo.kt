package ferraz.github.demo.api.models

data class Repo (
        val name: String,
        val full_name: String,
        val owner: Owner,
        val description: String,
        val forks_count: Int
) {
    data class Owner (
            val avatar_url: String
    )
}