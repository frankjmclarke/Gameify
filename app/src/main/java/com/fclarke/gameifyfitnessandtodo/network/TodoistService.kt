package com.fclarke.gameifyfitnessandtodo.network

import io.reactivex.Observable
import retrofit2.http.*

//https://developer.todoist.com/sync/v8/#get-all-projects

interface TodoistService {
    //@Headers("Authorization: Bear_er bead-blah-blah")
    //@Headers({"h1","h2})
    @GET("completed/get_all") //sync can detect changes since last sync_token
    fun getChanged(
        @Header("Authorization") header: String,
        @Query("limit") limit: Int,
        @Query("since") since: String
    ): Observable<AllCompletedItems>
    /*
       @GET("sync")
       fun getAllProjects(
           @Header("Authorization") header: String,
           @Query("sync_token") sync_token: String,
           @Query("resource_types") resource_types: String
       ): Observable<AllProjects>

       @GET("sync")
       fun getProjectItems(
           @Header("Authorization") header: String,
           @Query("project_id") project_id: String
       ): Observable<AllProjects>

    @GET("sync")
    fun getCompleted(
        @Header("Authorization") header: String,
        @Query("since") since: String
    ): Observable<AllProjects>
*/
    /*
All the data returned is fitted into an observable ListModel
     */

}