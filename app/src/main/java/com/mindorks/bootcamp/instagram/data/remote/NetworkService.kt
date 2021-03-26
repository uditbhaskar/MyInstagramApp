package com.mindorks.bootcamp.instagram.data.remote

import com.mindorks.bootcamp.instagram.data.remote.request.DummyRequest
import com.mindorks.bootcamp.instagram.data.remote.request.LoginRequest
import com.mindorks.bootcamp.instagram.data.remote.request.PostLikeModifyRequest
import com.mindorks.bootcamp.instagram.data.remote.response.DummyResponse
import com.mindorks.bootcamp.instagram.data.remote.response.GeneralResponse
import com.mindorks.bootcamp.instagram.data.remote.response.LoginResponse
import com.mindorks.bootcamp.instagram.data.remote.response.PostListResponse
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface NetworkService {


    @POST(Endpoints.DUMMY)
    fun doDummyCall(
            @Body request: DummyRequest,
            @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<DummyResponse>

    /*
     * Example to add other headers
     *
     *  @POST(Endpoints.DUMMY_PROTECTED)
        fun sampleDummyProtectedCall(
            @Body request: DummyRequest,
            @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
            @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
            @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
        ): Single<DummyResponse>
     */

    @POST(Endpoints.LOGIN)
    fun doLoginCall(
            @Body request: LoginRequest,
            @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<LoginResponse>

    @GET(Endpoints.HOME_POSTS_LIST)
    fun doHomePostListCall(
            @Query("firstPostId") firstPostId: String?,
            @Query("lastPostId") lastPostId: String?,
            @Header(Networking.HEADER_USER_ID) userId: String,
            @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String,
            @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<PostListResponse>

    @PUT(Endpoints.POST_LIKE)
    fun doPostLikeCall(
            @Body request: PostLikeModifyRequest,
            @Header(Networking.HEADER_USER_ID) userId: String,
            @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String,
            @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<GeneralResponse>

    @PUT(Endpoints.POST_UNLIKE)
    fun doPostUnlikeCall(
            @Body request: PostLikeModifyRequest,
            @Header(Networking.HEADER_USER_ID) userId: String,
            @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String,
            @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<GeneralResponse>

}