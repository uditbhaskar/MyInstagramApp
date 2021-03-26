package com.mindorks.bootcamp.instagram.ui.home

import androidx.lifecycle.MutableLiveData
import com.mindorks.bootcamp.instagram.data.model.Post
import com.mindorks.bootcamp.instagram.data.model.User
import com.mindorks.bootcamp.instagram.data.repository.PostRepository
import com.mindorks.bootcamp.instagram.data.repository.UserRepository
import com.mindorks.bootcamp.instagram.ui.base.BaseViewModel
import com.mindorks.bootcamp.instagram.utils.common.Resource
import com.mindorks.bootcamp.instagram.utils.network.NetworkHelper
import com.mindorks.bootcamp.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        private val userRepository: UserRepository,
        private val postRepository: PostRepository,
        private val allPostList: ArrayList<Post>,
        private val paginator: PublishProcessor<Pair<String?, String?>>
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()

    private val user: User = userRepository.getCurrentUser()!! // should not be used without logged in user

    init {
        compositeDisposable.add(
                paginator
                        .onBackpressureDrop()
                        .doOnNext {
                            loading.postValue(true)
                        }
                        .concatMapSingle { pageIds ->
                            return@concatMapSingle postRepository
                                    .fetchHomePostList(pageIds.first, pageIds.second, user)
                                    .subscribeOn(Schedulers.io())
                                    .doOnError {
                                        handleNetworkError(it)
                                    }
                        }
                        .subscribe(
                                {
                                    allPostList.addAll(it)
                                    loading.postValue(false)
                                    posts.postValue(Resource.success(it))
                                },
                                {
                                    handleNetworkError(it)
                                }
                        )
        )
    }

    override fun onCreate() {
        loadMorePosts()
    }

    private fun loadMorePosts() {
        val firstPostId = if (allPostList.isNotEmpty()) allPostList[0].id else null
        val lastPostId = if (allPostList.size > 1) allPostList[allPostList.size - 1].id else null

        if (checkInternetConnectionWithMessage()) paginator.onNext(Pair(firstPostId, lastPostId))
    }

    fun onLoadMore() {
        if (loading.value !== null && loading.value == false) loadMorePosts()
    }
}