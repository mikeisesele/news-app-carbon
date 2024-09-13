package com.michael.testfakedatafactory

import com.michael.models.NewsFeedDomainModel
import com.michael.network.model.NewsFeedApiResult
import com.michael.network.model.NewsFeedResponse
import com.michael.news.presentation.model.NewsFeedUiModel
import com.michael.newsdetail.presentation.model.NewsDetailUiModel

internal object TestFakeDataFactory {

    val fakeNewsDetailUiModel = NewsDetailUiModel(
        articleId = "ai-2024-01",
        category = listOf("Technology", "AI"),
        creator = listOf("Jane Doe", "John Smith"),
        description = "A comprehensive look at the latest advancements in artificial intelligence, exploring new technologies and their impact.",
        imageUrl = "https://example.com/ai_advancements.jpg",
        keywords = listOf("AI", "Technology", "Innovation"),
        link = "https://example.com/ai_advancements_article",
        pubDate = "2024-09-11",
        sourceIcon = "https://example.com/tech_source_icon.png",
        sourceName = "Tech Source",
        sourceUrl = "https://example.com",
        title = "Exploring the Future of Artificial Intelligence"
    )

    // Single fake NewsFeedUiModel instance
    val fakeNewsUiModel = NewsFeedUiModel(
        articleId = "exampleId1",
        category = listOf("Technology", "Business"),
        creator = listOf("John Doe", "Jane Smith"),
        description = "A detailed description of the latest advancements in technology and business strategies.",
        imageUrl = "https://example.com/image1.jpg",
        keywords = listOf("Tech", "Business", "Innovation"),
        pubDate = "2024-09-11",
        title = "Tech and Business Innovations"
    )

    // List of fake NewsFeedUiModel instances
    val fakeNewsUiModelList = listOf(
        NewsFeedUiModel(
            articleId = "exampleId2",
            category = listOf("Health", "Science"),
            creator = listOf("Alice Brown", "Bob Green"),
            description = "An insightful article about recent breakthroughs in medical science.",
            imageUrl = "https://example.com/image2.jpg",
            keywords = listOf("Health", "Science", "Breakthrough"),
            pubDate = "2024-09-10",
            title = "Medical Breakthroughs in 2024"
        ),
        NewsFeedUiModel(
            articleId = "exampleId3",
            category = listOf("Sports", "Entertainment"),
            creator = listOf("Charlie Blue"),
            description = "The top highlights from the recent sports events and entertainment news.",
            imageUrl = "https://example.com/image3.jpg",
            keywords = listOf("Sports", "Entertainment", "Highlights"),
            pubDate = "2024-09-09",
            title = "Top Sports and Entertainment Highlights"
        ),
        NewsFeedUiModel(
            articleId = "exampleId4",
            category = listOf("Politics", "Economy"),
            creator = listOf("David White", "Eve Black"),
            description = "A comprehensive overview of the current political and economic climate.",
            imageUrl = "https://example.com/image4.jpg",
            keywords = listOf("Politics", "Economy", "Climate"),
            pubDate = "2024-09-08",
            title = "Current Political and Economic Climate"
        )
    )

    // Single fake NewsFeedDomainModel instance
    val fakeNewsDomainModel = NewsFeedDomainModel(
        aiOrg = "OpenAI",
        aiRegion = "US",
        aiTag = "Technology",
        articleId = "123456",
        category = listOf("Technology", "Innovation"),
        content = "This is a detailed content about advancements in AI and technology.",
        country = listOf("US", "Canada"),
        creator = listOf("John Doe", "Jane Doe"),
        description = "An article detailing advancements in AI.",
        duplicate = false,
        imageUrl = "https://example.com/ai_image.jpg",
        keywords = listOf("AI", "Technology", "Innovation"),
        language = "English",
        link = "https://example.com/ai_article",
        pubDate = "2024-09-11",
        pubDateTZ = "2024-09-11T21:00:00Z",
        sentiment = "Positive",
        sentimentStats = "Sentiment Analysis Stats",
        sourceIcon = "https://example.com/icon.png",
        sourceId = "source123",
        sourceName = "Tech Source",
        sourcePriority = 1,
        sourceUrl = "https://example.com",
        title = "AI and Technology Advancements"
    )

    // List of fake NewsFeedDomainModel instances
    val fakeNewsDomainModelList = listOf(
        NewsFeedDomainModel(
            aiOrg = "Google AI",
            aiRegion = "Global",
            aiTag = "Health",
            articleId = "7891011",
            category = listOf("Health", "AI"),
            content = "AI's impact on healthcare is growing with significant advancements.",
            country = listOf("US", "UK"),
            creator = listOf("Alice Brown"),
            description = "How AI is revolutionizing healthcare systems worldwide.",
            duplicate = false,
            imageUrl = "https://example.com/healthcare_ai.jpg",
            keywords = listOf("AI", "Healthcare", "Innovation"),
            language = "English",
            link = "https://example.com/healthcare_ai",
            pubDate = "2024-09-10",
            pubDateTZ = "2024-09-10T18:30:00Z",
            sentiment = "Neutral",
            sentimentStats = "Sentiment Analysis Stats",
            sourceIcon = "https://example.com/health_icon.png",
            sourceId = "source456",
            sourceName = "Health News",
            sourcePriority = 2,
            sourceUrl = "https://example.com",
            title = "AI's Role in Healthcare"
        ),
        NewsFeedDomainModel(
            aiOrg = "DeepMind",
            aiRegion = "EU",
            aiTag = "Research",
            articleId = "1121314",
            category = listOf("Science", "AI"),
            content = "DeepMind's latest research on AI models and their applications in science.",
            country = listOf("Germany", "France"),
            creator = listOf("Bob Green"),
            description = "Latest research and development in AI from DeepMind.",
            duplicate = true,
            imageUrl = "https://example.com/deepmind_research.jpg",
            keywords = listOf("AI", "Science", "Research"),
            language = "German",
            link = "https://example.com/deepmind_research",
            pubDate = "2024-09-09",
            pubDateTZ = "2024-09-09T15:00:00Z",
            sentiment = "Positive",
            sentimentStats = "Sentiment Analysis Stats",
            sourceIcon = "https://example.com/science_icon.png",
            sourceId = "source789",
            sourceName = "Science News",
            sourcePriority = 1,
            sourceUrl = "https://example.com",
            title = "DeepMind's AI Research"
        )
    )

    // List of fake NewsFeedDomainModel instances
    val fakeMoreNewsDomainModelList = listOf(
        NewsFeedDomainModel(
            aiOrg = "Google AI",
            aiRegion = "Global",
            aiTag = "Health",
            articleId = "7891011",
            category = listOf("Health", "AI"),
            content = "AI's impact on healthcare is growing with significant advancements.",
            country = listOf("US", "UK"),
            creator = listOf("Alice Brown"),
            description = "How AI is revolutionizing healthcare systems worldwide.",
            duplicate = false,
            imageUrl = "https://example.com/healthcare_ai.jpg",
            keywords = listOf("AI", "Healthcare", "Innovation"),
            language = "English",
            link = "https://example.com/healthcare_ai",
            pubDate = "2024-09-10",
            pubDateTZ = "2024-09-10T18:30:00Z",
            sentiment = "Neutral",
            sentimentStats = "Sentiment Analysis Stats",
            sourceIcon = "https://example.com/health_icon.png",
            sourceId = "source456",
            sourceName = "Health News",
            sourcePriority = 2,
            sourceUrl = "https://example.com",
            title = "AI's Role in Healthcare"
        ),
        NewsFeedDomainModel(
            aiOrg = "DeepMind",
            aiRegion = "EU",
            aiTag = "Research",
            articleId = "1121314",
            category = listOf("Science", "AI"),
            content = "DeepMind's latest research on AI models and their applications in science.",
            country = listOf("Germany", "France"),
            creator = listOf("Bob Green"),
            description = "Latest research and development in AI from DeepMind.",
            duplicate = true,
            imageUrl = "https://example.com/deepmind_research.jpg",
            keywords = listOf("AI", "Science", "Research"),
            language = "German",
            link = "https://example.com/deepmind_research",
            pubDate = "2024-09-09",
            pubDateTZ = "2024-09-09T15:00:00Z",
            sentiment = "Positive",
            sentimentStats = "Sentiment Analysis Stats",
            sourceIcon = "https://example.com/science_icon.png",
            sourceId = "source789",
            sourceName = "Science News",
            sourcePriority = 1,
            sourceUrl = "https://example.com",
            title = "DeepMind's AI Research"
        )
    )

     // Fake single NewsFeedApiResult instance
    val fakeNewsFeedApiResult = NewsFeedApiResult(
        ai_org = "OpenAI",
        ai_region = "US",
        ai_tag = "Artificial Intelligence",
        article_id = "ai-2024-01",
        category = listOf("Technology", "AI"),
        content = "This article covers the latest advancements in artificial intelligence, including new breakthroughs and applications.",
        country = listOf("US"),
        creator = listOf("Jane Doe", "John Smith"),
        description = "An in-depth look at recent innovations in AI technology.",
        duplicate = false,
        image_url = "https://example.com/ai_innovation.jpg",
        keywords = listOf("AI", "Innovation", "Technology"),
        language = "English",
        link = "https://example.com/ai_innovation_article",
        pubDate = "2024-09-11",
        pubDateTZ = "2024-09-11T21:00:00Z",
        sentiment = "Positive",
        sentiment_stats = "Sentiment analysis shows a positive outlook with a 90% positive sentiment score.",
        source_icon = "https://example.com/ai_icon.png",
        source_id = "source-001",
        source_name = "Tech Innovations",
        source_priority = 1,
        source_url = "https://example.com",
        title = "Latest Advances in Artificial Intelligence",
        video_url = "https://example.com/ai_video.mp4"
    )

    // Fake NewsFeedResponse containing the single NewsFeedApiResult
    val fakeNewsFeedResponse = NewsFeedResponse(
        nextPage = "https://newsdata.io/api/1/latest?apikey=your_api_key&page=2",
        results = listOf(fakeNewsFeedApiResult),
        status = "success",
        totalResults = 1
    )
}

