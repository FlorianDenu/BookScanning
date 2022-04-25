package com.floriandenu.bookscanning.api

import retrofit2.Response
import retrofit2.http.*

interface VolumeApi {

    @GET("books/v1/volumes")
    suspend fun volumeByIsbn(@Query("q", encoded = true) isbn: String): Response<VolumeResult>?

    data class VolumeResult(
        val kind: String,
        val totalItems: Int,
        val items: List<Volume>
    ) {
        data class Volume(
            val kind: String,
            val id: String,
            val etag: String,
            val selfLink: String,
            val volumeInfo: VolumeInfo,
            val readingModes: ReadingModes,
            val pageCount: Int,
            val printType: String,
            val maturityRating: String,
            val allowAnonLogging: Boolean,
            val contentVersion: String,
            val language: String,
            val previewLink: String,
            val infoLink: String,
            val canonicalVolumeLink: String,
            val saleInfo: SaleInfo,
            val accessInfo: AccessInfo,
        ) {
            data class VolumeInfo(
                val title: String,
                val authors: List<String>,
                val publisher: String,
                val publishedDate: String,
                val description: String,
                val industryIdentifiers: List<IndustryIdentifiers>
            ) {
                data class IndustryIdentifiers(
                    val type: String,
                    val identifier: String
                )
            }

            data class ReadingModes(
                val text: Boolean,
                val image: Boolean
            )

            data class SaleInfo(
                val country: String,
                val saleability: String,
                val isEbook: Boolean
            )

            data class AccessInfo(
                val country: String,
                val viewability: String,
                val embeddable: Boolean,
                val publicDomain: Boolean,
                val textToSpeechPermission: String,
                val epub: Epub,
                val pdf: Pdf,
                val webReaderLink: String,
                val accessViewStatus: String,
                val quoteSharingAllowed: Boolean,
                val searchInfo: SearchInfo
            ) {
                data class Epub(
                    val isAvailable: Boolean
                )

                data class Pdf(
                    val isAvailable: Boolean
                )

                data class SearchInfo(
                    val textSnippet: String
                )
            }
        }
    }
}