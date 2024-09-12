package com.michael.news.domain.mappers

import com.michael.models.NewsFeedDomainModel
import com.michael.testfakedatafactory.TestFakeDataFactory.fakeNewsFeedApiResult
import com.michael.testfakedatafactory.TestFakeDataFactory.fakeNewsFeedResponse
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.Test

class NewsFeedRepositoryMapperTest {
    @Test
    fun `given a list of NewsFeedResponse when mapped to domain entity, then return a list of NewsFeedDomainModel`() {
        // Given
        val newsFeedResponseList = fakeNewsFeedResponse

        // When
        val result = newsFeedResponseList.toEntity()

        // Then
        assert(result.size == newsFeedResponseList.results.size)
        result.forEachIndexed { index, newsFeedDomainModel ->
            assert(newsFeedDomainModel.title == newsFeedResponseList.results[index].title)
            assert(newsFeedDomainModel.description == newsFeedResponseList.results[index].description)
            assert(newsFeedDomainModel.duplicate == newsFeedResponseList.results[index].duplicate)
            assert(newsFeedDomainModel.sourceName == newsFeedResponseList.results[index].source_name)
            assert(newsFeedDomainModel.pubDate == newsFeedResponseList.results[index].pubDate)
            assert(newsFeedDomainModel.articleId == newsFeedResponseList.results[index].article_id)
        }

        result.forEach {
            it.shouldBeInstanceOf<NewsFeedDomainModel>()
        }



    }
}
