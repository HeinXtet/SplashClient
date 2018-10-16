package com.heinhtet.deevd.splash.model.response

import com.squareup.moshi.Json


/**
 * Created by Hein Htet on 10/14/18.
 */
data class PhotoModel(
        @Json(name = "id") var id: String?,
        @Json(name = "created_at") var createdAt: String?,
        @Json(name = "updated_at") var updatedAt: String?,
        @Json(name = "width") var width: Int?,
        @Json(name = "height") var height: Int?,
        @Json(name = "color") var color: String?,
        @Json(name = "likes") var likes: Int?,
        @Json(name = "liked_by_user") var likedByUser: Boolean?,
        @Json(name = "description") var description: String?,
        @Json(name = "user") var user: User?,
        @Json(name = "current_user_collections") var currentUserCollections: List<CurrentUserCollection?>?,
        @Json(name = "urls") var urls: Urls?,
        @Json(name = "links") var links: PhotoModelLinks?
)

data class Urls(
        @Json(name = "raw") var raw: String?,
        @Json(name = "full") var full: String?,
        @Json(name = "regular") var regular: String?,
        @Json(name = "small") var small: String?,
        @Json(name = "thumb") var thumb: String?
)

data class PhotoModelLinks(
        @Json(name = "self") var self: String?,
        @Json(name = "html") var html: String?,
        @Json(name = "download") var download: String?,
        @Json(name = "download_location") var downloadLocation: String?
)

data class CurrentUserCollection(
        @Json(name = "id") var id: Int?,
        @Json(name = "title") var title: String?,
        @Json(name = "published_at") var publishedAt: String?,
        @Json(name = "updated_at") var updatedAt: String?,
        @Json(name = "curated") var curated: Boolean?,
        @Json(name = "cover_photo") var coverPhoto: Any?,
        @Json(name = "user") var user: Any?
)

data class User(
        @Json(name = "id") var id: String?,
        @Json(name = "username") var username: String?,
        @Json(name = "name") var name: String?,
        @Json(name = "portfolio_url") var portfolioUrl: String?,
        @Json(name = "bio") var bio: String?,
        @Json(name = "location") var location: String?,
        @Json(name = "total_likes") var totalLikes: Int?,
        @Json(name = "total_photos") var totalPhotos: Int?,
        @Json(name = "total_collections") var totalCollections: Int?,
        @Json(name = "instagram_username") var instagramUsername: String?,
        @Json(name = "twitter_username") var twitterUsername: String?,
        @Json(name = "profile_image") var profileImage: ProfileImage?,
        @Json(name = "links") var links: Links?
)

data class Links(
        @Json(name = "self") var self: String?,
        @Json(name = "html") var html: String?,
        @Json(name = "photos") var photos: String?,
        @Json(name = "likes") var likes: String?,
        @Json(name = "portfolio") var portfolio: String?
)

data class ProfileImage(
        @Json(name = "small") var small: String?,
        @Json(name = "medium") var medium: String?,
        @Json(name = "large") var large: String?
)