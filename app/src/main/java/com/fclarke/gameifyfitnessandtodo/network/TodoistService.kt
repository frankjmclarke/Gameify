package com.fclarke.gameifyfitnessandtodo.network
import com.fclarke.gameifyfitnessandtodo.MainActivity
import com.fclarke.gameifyfitnessandtodo.R
import io.reactivex.Observable
import retrofit2.http.*
//https://developer.todoist.com/sync/v8/#get-all-projects

interface TodoistService {
    @GET("sync") //sync can detect changes since last sync_token
    //@Headers("Authorization: Bearer bead531d604-97e98f33")
    //@Headers({"h1","h2})
    //@Headers("Authorization: Bearer bead531d604-97e98f33"+MainActivity.todoistAuth)
    fun getListFromApi(
        @Header("Authorization") header: String,
        @Query("sync_token") sync_token: String,
        @Query("resource_types") resource_types: String
    ): Observable<ListModel>
    /*
All the data returned is fitted into an observable ListModel
     */
}