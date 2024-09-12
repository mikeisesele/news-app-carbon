package com.michael.news.presentation

import app.cash.turbine.test
import com.michael.base.contract.ViewEvent
import com.michael.base.model.MessageState
import com.michael.base.providers.StringProvider
import com.michael.news.domain.NewsFeedRepository
import com.michael.news.domain.contract.NewsFeedSideEffect
import com.michael.news.domain.contract.NewsFeedViewAction
import com.michael.news.domain.mappers.toUiModel
import com.michael.testfakedatafactory.TestFakeDataFactory.fakeNewsDomainModel
import com.michael.testfakedatafactory.TestFakeDataFactory.fakeNewsDomainModelList
import com.michael.testing.BaseTest
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.string.shouldNotBeEmpty
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

internal class NewsFeedViewModelTest : BaseTest() {
    private lateinit var newsFeedViewModel: NewsFeedViewModel
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
        newsFeedViewModel = NewsFeedViewModel(
            dispatcherProvider = dispatcherProvider,
            newsFeedRepository = newsRepository,
            stringProvider = stringProvider,
        )
    }

    @Test
    fun `Given viewModel is initialized Then state is updated accordingly`() = startTest {
        initializeViewModel()
        newsFeedViewModel.state.test {
            with(awaitItem()) {
                searchQuery.shouldBeEmpty()
                newsFeedList.shouldBeEmpty()
                searchQueryResponse.shouldBeEmpty()
            }
        }
    }

    @Test
    fun `Given UpdateSearchQuery is called Then state is updated accordingly`() = startTest {
        initializeViewModel()
        newsFeedViewModel.state.test {
            newsFeedViewModel.onViewAction(NewsFeedViewAction.UpdateSearchQuery("query"))
            awaitItem() // initial state
            with(awaitItem()) {// news list has been loaded
                searchQuery.shouldNotBeEmpty()
                searchQuery.shouldBe("query")
            }
        }
    }

    @Test
    fun `Given search is initialized Then repository should return expected data`() = startTest {
        initializeViewModel()
        newsFeedViewModel.state.test {
            awaitItem() // initial state
            newsFeedViewModel.onViewAction(NewsFeedViewAction.UpdateSearchQuery("query"))
            awaitItem() // search query has been updated
            newsFeedViewModel.onViewAction(NewsFeedViewAction.SearchNewsFeed)
            awaitItem() // search query loading state
            with(awaitItem()) {// news list has been loaded
                searchQueryResponse.shouldNotBeEmpty()
                searchQuery.shouldNotBeEmpty()
                searchQueryResponse.shouldBe(fakeNewsDomainModelList.map { it.toUiModel() })
            }
        }

    }

    @Test
    fun `Given GetNewsFeed is called when success Then repository should return expected data`() =
        startTest {
            initializeViewModel()
            newsFeedViewModel.state.test {
                awaitItem() // initial state
                newsFeedViewModel.onViewAction(NewsFeedViewAction.GetNewsFeed)
                awaitItem() // loading state
                with(awaitItem()) { // news list has been loaded
                    newsFeedList.shouldNotBeEmpty()
                    newsFeedList.shouldBe(fakeNewsDomainModelList.map { it.toUiModel() }) // mapping is successful
                }
            }

        }

    @Test
    fun `Given fGetNewsFeed is called when error Then repository should return error data`() =
        startTest {
            coEvery {
                newsRepository.getNewsFeed()
            } returns flow {
                throw IllegalStateException("error") // force repo response
            }
            initializeViewModel()
            newsFeedViewModel.state.test {
                awaitItem() // initial state
                newsFeedViewModel.onViewAction(NewsFeedViewAction.GetNewsFeed)
                awaitItem() // loading state
                awaitItem() // error state updated
            }
            newsFeedViewModel.events.test {
                with(awaitItem()) {
                    shouldBeInstanceOf<ViewEvent.Effect>()
                    effect.shouldBe(
                        NewsFeedSideEffect.ShowErrorMessage(
                            errorMessageState = MessageState.Inline(errorMessage)
                        ),
                    )
                }
            }
        }
}