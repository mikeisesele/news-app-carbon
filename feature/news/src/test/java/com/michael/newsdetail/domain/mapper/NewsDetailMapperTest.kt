package com.michael.newsdetail.domain.mapper

import com.michael.newsdetail.presentation.model.NewsDetailUiModel
import com.michael.testfakedatafactory.TestFakeDataFactory
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.Test

class NewsDetailMapperTest {

    @Test
    fun `given a NewsFeedDomainModel when mapped, it should return an instance of NewsDetailUiModel`() {
        // Given
        val fakeNewsDomainModel = TestFakeDataFactory.fakeNewsDomainModel

        // When
        val result = fakeNewsDomainModel.toDetailUiModel()

        // Then
        result.shouldBeInstanceOf<NewsDetailUiModel>()
        with(result) {
            articleId shouldBe fakeNewsDomainModel.articleId
            creator shouldBe fakeNewsDomainModel.creator
            category shouldBe fakeNewsDomainModel.category

            description shouldBe fakeNewsDomainModel.description
            imageUrl shouldBe fakeNewsDomainModel.imageUrl
            keywords shouldBe fakeNewsDomainModel.keywords
            link shouldBe fakeNewsDomainModel.link
            pubDate shouldBe fakeNewsDomainModel.pubDate

            sourceIcon shouldBe fakeNewsDomainModel.sourceIcon
            sourceName shouldBe fakeNewsDomainModel.sourceName
            sourceUrl shouldBe fakeNewsDomainModel.sourceUrl
            title shouldBe fakeNewsDomainModel.title
        }
    }
}
