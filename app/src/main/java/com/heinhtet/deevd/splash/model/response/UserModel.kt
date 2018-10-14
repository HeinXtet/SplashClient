package com.heinhtet.deevd.splash.model.response

import com.squareup.moshi.Json

/**
 * Created by Hein Htet on 10/13/18.
 */
data class UserModel(
        @Json(name = "id") var id: String,
        @Json(name = "updated_at") var updatedAt: String,
        @Json(name = "first_name") var firstName: String,
        @Json(name = "last_name") var lastName: String,
        @Json(name = "twitter_username") var twitterUserName: String?,
        @Json(name = "portfolio_url") var portfolioUrl: String?,
        @Json(name = "bio") var bio: String?,
        @Json(name = "location") var location: String?,
        @Json(name = "total_like") var totalLike: Int?,
        @Json(name = "total_photos") var totalPhoto: Int?,
        @Json(name = "total_collections") var totalCollections: Int?,
        @Json(name = "followed_by_user") var followedByUser: Boolean,
        @Json(name = "downloads") var download: Long?,
        @Json(name = "uploads_remaining") var uploadsRemaining: Int?,
        @Json(name = "instagram_username") var instagramUsername: String?,
        @Json(name = "self") var self: String?,
        @Json(name = "links") var links: UserModelLinks,
        @Json(name = "username") var userName: String?,
        @Json(name = "email") var email: String?
)

data class UserModelLinks(
        @Json(name = "self") var self: String,
        @Json(name = "html") var html: String,
        @Json(name = "photos") var photos: String,
        @Json(name = "likes") var likes: String,
        @Json(name = "portfolio") var portfolio: String
)