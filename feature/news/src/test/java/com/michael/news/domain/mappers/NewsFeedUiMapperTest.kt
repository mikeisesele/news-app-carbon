package com.michael.news.domain.mappers

import com.michael.news.presentation.model.NewsFeedUiModel
import com.michael.testfakedatafactory.TestFakeDataFactory
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.Test

class NewsFeedUiMapperTest {
    @Test
    fun `given a list of NewsFeedDomainModel when mapped to ui model, then return a list of NewsFeedUiModel`() {
        // Given
        val fakeNewsDomainModelList = TestFakeDataFactory.fakeNewsDomainModelList

        // When
        val result = fakeNewsDomainModelList.toUiModel()

        // Then
        assert(result.size == fakeNewsDomainModelList.size)
        result.forEachIndexed { index, newsFeedUiModel ->
            assert(newsFeedUiModel.title == fakeNewsDomainModelList[index].title)
            assert(newsFeedUiModel.description == fakeNewsDomainModelList[index].description)
            assert(newsFeedUiModel.imageUrl == fakeNewsDomainModelList[index].imageUrl)
            assert(newsFeedUiModel.pubDate == fakeNewsDomainModelList[index].pubDate)
        }

        result.forEach {
            it.shouldBeInstanceOf<NewsFeedUiModel>()
        }
    }
}
