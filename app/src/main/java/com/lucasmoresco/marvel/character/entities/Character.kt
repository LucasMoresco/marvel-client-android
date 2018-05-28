package com.lucasmoresco.marvel.character.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
        val code: Int,
        val status: String,
        val copyright: String,
        val attributionText: String,
        val attributionHTML: String,
        val etag: String,
        val data: Data
) : Parcelable

@Parcelize
data class Data(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Result>
) : Parcelable

@Parcelize
data class Result(
        val id: Int,
        val name: String,
        val description: String,
        val modified: String,
        val thumbnail: Thumbnail,
        val resourceURI: String,
        val comics: Comics,
        val series: Series,
        val stories: Stories,
        val events: Events,
        val urls: List<Url>
) : Parcelable

@Parcelize
data class Events(
        val available: Int,
        val collectionURI: String,
        val items: List<Item>,
        val returned: Int
) : Parcelable

@Parcelize
data class Item(
        val resourceURI: String,
        val name: String,
        val type: String

) : Parcelable

@Parcelize
data class Url(
        val type: String,
        val url: String
) : Parcelable

@Parcelize
data class Thumbnail(
        val path: String,
        val extension: String
) : Parcelable

@Parcelize
data class Stories(
        val available: Int,
        val collectionURI: String,
        val items: List<Item>,
        val returned: Int
) : Parcelable

@Parcelize
data class Comics(
        val available: Int,
        val collectionURI: String,
        val items: List<Item>,
        val returned: Int
) : Parcelable

@Parcelize
data class Series(
        val available: Int,
        val collectionURI: String,
        val items: List<Item>,
        val returned: Int
) : Parcelable

enum class Size(private val size: String) {
    /** 50x75px  */
    PORTRAIT_SMALL("portrait_small"),
    /** 100x150px  */
    PORTRAIT_MEDIUM("portrait_medium"),
    /** 150x225px  */
    PORTRAIT_XLARGE("portrait_xlarge"),
    /** 168x252px  */
    PORTRAIT_FANTASTIC("portrait_fantastic"),
    /** 300x450px  */
    PORTRAIT_UNCANNY("portrait_uncanny"),
    /** 216x324px  */
    PORTRAIT_INCREDIBLE("portrait_incredible"),
    /** 65x45px  */
    STANDARD_SMALL("standard_small"),
    /** 100x100px  */
    STANDARD_MEDIUM("standard_medium"),
    /** 140x140px  */
    STANDARD_LARGE("standard_large"),
    /** 200x200px  */
    STANDARD_XLARGE("standard_xlarge"),
    /** 250x250px  */
    STANDARD_FANTASTIC("standard_fantastic"),
    /** 180x180px  */
    STANDARD_AMAZING("standard_amazing"),
    /** 120x90px  */
    LANDSCAPE_SMALL("landscape_small"),
    /** 175x130px  */
    LANDSCAPE_MEDIUM("landscape_medium"),
    /** 190x140px  */
    LANDSCAPE_LARGE("landscape_large"),
    /** 270x200px  */
    LANDSCAPE_XLARGE("landscape_xlarge"),
    /** 250x156px  */
    LANDSCAPE_AMAZING("landscape_amazing"),
    /** 464x261px  */
    LANDSCAPE_INCREDIBLE("landscape_incredible"),
    DETAIL("detail"),
    FULLSIZE("fullsize");

}