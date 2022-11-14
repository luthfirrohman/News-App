package com.luthfirrohman.newspedia.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull
@Parcelize
@Entity(tableName = "news")
class NewsEntity(

    @field:ColumnInfo(name = "title")
    @field:PrimaryKey
    val title: String,

    @field:ColumnInfo(name = "publishedAt")
    val publishedAt: String? = "unknown date-published",

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String? = null,

    @field:ColumnInfo(name = "url")
    val url: String? = null,

    @field:ColumnInfo(name = "description")
    val description: String? = null,

    @field:ColumnInfo(name = "source")
    val source: String? = null,

    @field:ColumnInfo(name = "author")
    val author: String? = null,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
):Parcelable