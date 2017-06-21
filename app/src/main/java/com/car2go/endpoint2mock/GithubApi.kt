package com.car2go.endpoint2mock

import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

data class Repository(val name: String)

/**
 * Loads repositories from GitHub.
 */
interface GithubApi {

    /**
     * @return [Observable] which emits list of repositories of car2go company.
     *
     * There is an annotation, so it can be mocked.
     */
    @MockedEndpoint
    @GET("/users/{user}/repos")
    fun getRepositories(@Path("user") user: String): Observable<List<Repository>>

    /**
     * @return [Observable] which emits list of repositories of a particular user.
     *
     * There is no annotation, so it can NOT be mocked and will continue to deliver requests to
     * original URL.
     */
    @GET("/users/dmitry-zaitsev/repos")
    fun getUserRepositories(): Observable<List<Repository>>

}