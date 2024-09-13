package com.michael.newsdetail.presentation

import app.cash.turbine.test
import com.michael.base.contract.ViewEvent
import com.michael.base.model.MessageState
import com.michael.base.providers.StringProvider
import com.michael.news.domain.NewsFeedRepository
import com.michael.newsdetail.domain.contract.NewsDetailSideEffect
import com.michael.newsdetail.domain.contract.NewsDetailViewAction
import com.michael.newsdetail.domain.mapper.toDetailUiModel
import com.michael.testfakedatafactory.TestFakeDataFactory.fakeNewsDomainModel
import com.michael.testfakedatafactory.TestFakeDataFactory.fakeNewsDomainModelList
import com.michael.testing.BaseTest
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class NewsDetailViewModelTest :BaseTest() {
    private lateinit var newsDetailViewModel: NewsDetailViewModel

    private val errorMessage = "error"

    private val newsRepository: NewsFeedRepository = mockk {
        coEvery { getNewsFeed() } returns flowOf(
            fakeNewsDomainModelList,
        )
        coEvery { searchNewsFeed(any()) } returns flowOf(fakeNewsDomainModelList)

        coEvery { getNewsDetail(any()) } returns flowOf(
            fakeNewsDomainModel,
        )
        coEvery { searchNewsFeed(any()) } returns flowOf(fakeNewsDomainModelList)
    }


    private val stringProvider: StringProvider = mockk {
        every { getString(any()) } returns errorMessage
    }

    private fun initializeViewModel() {
        newsDetailViewModel = NewsDetailViewModel(
            dispatcherProvider = dispatcherProvider,
            newsRepository = newsRepository,
            stringProvider = stringProvider,
        )
    }

    @Test
    fun `Given viewModel is initialized Then state is updated accordingly`() = startTest {
        initializeViewModel()
        newsDetailViewModel.state.test {
            with(awaitItem()) {
                isLoading.shouldBeFalse()
                errorState.shouldBeNull()
                newsDetail.shouldBeNull()
            }
        }
    }

    @Test
    fun `Given GetNewsFeed is called when success Then repository should return expected data`() =
        startTest {
            initializeViewModel()
            newsDetailViewModel.state.test {
                awaitItem() // initial state
                newsDetailViewModel.onViewAction(NewsDetailViewAction.GetNewsDetail("123456"))
                awaitItem() // loading state
                with(awaitItem()) { // news list has been loaded
                    newsDetail.shouldNotBeNull()
                    newsDetail.shouldBe(fakeNewsDomainModel.toDetailUiModel()) // mapping is successful
                    newsDetail.articleId shouldBe "123456"
                }
            }
        }

    @Test
    fun `Given GetNewsFeed is called when error Then repository should return error data`() =
        startTest {
            coEvery {
                newsRepository.getNewsDetail(any())
            } returns flow {
                throw IllegalStateException("error") // force repo response
            }
            initializeViewModel()
            newsDetailViewModel.state.test {
                awaitItem() // initial state
                newsDetailViewModel.onViewAction(NewsDetailViewAction.GetNewsDetail("123456"))
                awaitItem() // loading state
                with(awaitItem()) { // error from repo
                    isLoading.shouldBeFalse()
                }
            }
            newsDetailViewModel.events.test {
                with(awaitItem()) {
                    shouldBeInstanceOf<ViewEvent.Effect>()
                    effect.shouldBe(
                        NewsDetailSideEffect.ShowErrorMessage(
                            errorMessageState = MessageState.Inline(errorMessage)
                        ),
                    )
                }
            }
        }

}