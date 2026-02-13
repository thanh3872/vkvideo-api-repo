
package com.thanh3872

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element

class VKVideoProvider : MainAPI() {
    override var mainUrl = "https://m.vkvideo.ru"
    override var name = "VKVideoAP1I"
    override val supportedTypes = setOf(TvType.Movie)

    // Lấy nội dung trang chủ [cite: 71, 74]
    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse? {
        val url = "$mainUrl/@jav2026"
        val document = app.get(url).document
        val home = document.select("a[href*='/video-']").mapNotNull {
            it.toSearchResult()
        }
        return newHomePageResponse(name, home)
    }

    // Xử lý tìm kiếm video [cite: 65, 68]
    override suspend fun search(query: String): List<SearchResponse> {
        val url = "$mainUrl/search?q=$query"
        val document = app.get(url).document
        return document.select("a[href*='/video-']").mapNotNull {
            it.toSearchResult()
        }
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val title = this.select(".v_title").text().ifEmpty { this.text() }
        val href = fixUrl(this.attr("href"))
        val poster = this.select("img").attr("src")
        return newMovieSearchResponse(title, href, TvType.Movie) {
            this.posterUrl = poster
        }
    }

    // Tải trang chi tiết video [cite: 82, 86]
    override suspend fun load(url: String): LoadResponse? {
        val document = app.get(url).document
        val title = document.select("h1").text()
        return newMovieLoadResponse(title, url, TvType.Movie, url)
    }

    // Lấy link video trực tiếp [cite: 94, 98]
    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        // Giả lập trả về link gốc để hệ thống Cloudstream thử nghiệm trích xuất
        callback.invoke(
            ExtractorLink(
                name,
                name,
                data,
                "$mainUrl/",
                Qualities.P1080.value,
                false
            )
        )
        return true
    }
}
