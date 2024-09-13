package com.michael.newsdetail.presentation

import com.michael.base.contract.BaseViewModel
import com.michael.base.contract.ViewEvent
import com.michael.base.model.MessageState
import com.michael.base.providers.DispatcherProvider
import com.michael.base.providers.StringProvider
import com.michael.common.cleanMessage
import com.michael.easylog.ifNullSetDefault
import com.michael.easylog.logInline
import com.michael.easylog.logInlineNullable
import com.michael.feature.news.R
import com.michael.models.NewsFeedDomainModel
import com.michael.news.domain.NewsFeedRepository
import com.michael.news.domain.contract.NewsFeedSideEffect
import com.michael.newsdetail.domain.contract.NewsDetailSideEffect
import com.michael.newsdetail.domain.contract.NewsDetailState
import com.michael.newsdetail.domain.contract.NewsDetailViewAction
import com.michael.newsdetail.domain.mapper.toDetailUiModel
import com.michael.ui.extensions.collectBy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
internal class NewsDetailViewModel @Inject constructor(
    private val newsRepository: NewsFeedRepository,
    dispatcherProvider: DispatcherProvider,
    private val stringProvider: StringProvider
) : BaseViewModel<NewsDetailState, NewsDetailViewAction>(
    NewsDetailState.initialState, dispatcherProvider
) {
    override fun onViewAction(viewAction: NewsDetailViewAction) {
        when (viewAction) {
            is NewsDetailViewAction.GetNewsDetail -> getNewsDetail(viewAction.id)
        }
    }

    private fun getNewsDetail(id: String) {
        launch {
            newsRepository.getNewsDetail(id).collectBy(
                onStart = ::onLoading,
                onEach = ::processNewsDetail,
                onError = ::onError
            )
        }
    }

    private fun processNewsDetail(newsDetail: NewsFeedDomainModel) {
        newsDetail.logInline("newsDetail")
        updateState { state ->
            state.copy(
                isLoading = false,
                newsDetail = newsDetail.toDetailUiModel()
            )
        }
    }

    private fun onLoading() {
        updateState { state ->
            state.copy(
                isLoading = true,
            )
        }
    }

    private fun onError(error: Throwable) {
        error.logInlineNullable()
        updateState { state ->
            state.copy(
                isLoading = false,
            )
        }

        val errorMessage = error.message?.cleanMessage().ifNullSetDefault {
            error.localizedMessage?.cleanMessage()?.ifEmpty {
                stringProvider.getString(
                    R.string.something_went_wrong
                )
            }
        }.orEmpty()

        dispatchViewEvent(
            ViewEvent.Effect(
                NewsDetailSideEffect.ShowErrorMessage(
                    errorMessageState = MessageState.Inline(errorMessage)
                )
            )
        )

    }
}