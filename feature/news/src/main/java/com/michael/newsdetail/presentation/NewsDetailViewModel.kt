package com.michael.newsdetail.presentation

import com.michael.base.contract.BaseViewModel
import com.michael.base.contract.ViewEvent
import com.michael.base.providers.DispatcherProvider
import com.michael.easylog.ifNullSetDefault
import com.michael.models.NewsFeedDomainModel
import com.michael.news.domain.NewsFeedRepository
import com.michael.news.domain.contract.NewsFeedSideEffect
import com.michael.newsdetail.domain.contract.NewsDetailState
import com.michael.newsdetail.domain.contract.NewsDetailViewAction
import com.michael.newsdetail.domain.mapper.toDetailUiModel
import com.michael.ui.extensions.collectBy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
internal class NewsDetailViewModel @Inject constructor(
    private val newsRepository: NewsFeedRepository,
    dispatcherProvider: DispatcherProvider
)  : BaseViewModel<NewsDetailState, NewsDetailViewAction>(
    NewsDetailState.initialState, dispatcherProvider
) {
    override fun onViewAction(viewAction: NewsDetailViewAction) {
        when (viewAction) {
             is NewsDetailViewAction.GetNewsDetail -> getNewsDetail(viewAction.id)
        }
    }

    private fun getNewsDetail(id: Int) {
        launch {
            newsRepository.getNewsDetail(id).collectBy(
                onStart = ::onLoading,
                onEach = ::processNewsDetail,
                onError = ::onError
            )
        }
    }

    private fun processNewsDetail(newsDetail: NewsFeedDomainModel) {
        updateState { state ->
            state.copy(
                isLoading = false,
                errorState = null,
                newsDetail = newsDetail.toDetailUiModel()
            )
        }
    }

    private fun onLoading() {
        updateState { state ->
            state.copy(
                isLoading = true,
                errorState = null
            )
        }
    }

    private fun onError(error: Throwable) {

        val errorMessage = error.message.ifNullSetDefault { error.localizedMessage }

        dispatchViewEvent(ViewEvent.Effect(NewsFeedSideEffect.ShowToast(errorMessage)))

    }
}