package ferraz.github.demo.api

import ferraz.github.demo.api.models.RepoResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    companion object Factory {

        fun create(): GithubService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder().addInterceptor(logging).build()

            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(httpClient)
                    .build()

            return retrofit.create(GithubService::class.java)
        }
    }

    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String,
                    @Query("page") page: Int,
                    @Query("per_page") perPage: Int): Observable<RepoResponse>
}