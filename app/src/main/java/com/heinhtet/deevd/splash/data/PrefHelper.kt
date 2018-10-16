package com.heinhtet.deevd.splash.data

import android.annotation.SuppressLint
import android.arch.paging.PagedList
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.heinhtet.deevd.splash.model.response.OAuthModel
import com.heinhtet.deevd.splash.model.response.PhotoModel
import com.heinhtet.deevd.splash.model.response.UserModel

/**
 * Created by Hein Htet on 8/23/18.
 */
class PrefHelper {
    private val NAME = "ProjectName"
    private lateinit var mContext: Context
    private lateinit var sharePref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val Language = "lan"
    private val USER_MODEL = "UserModel"
    private val HAS_AUTH = "already_login"
    private val AUTH = "auth"
    private val TEST_PHOTO = "tempPhoto"

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mInstance: PrefHelper? = null

        fun instance(): PrefHelper {
            if (mInstance == null) {
                synchronized(PrefHelper::class.java)
                {
                    if (mInstance == null) {
                        mInstance = PrefHelper()
                    }
                }
            }
            return mInstance!!
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun init(context: Context) {
        mContext = context
        sharePref = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        editor = sharePref.edit()
    }

    fun clear() {
        editor.clear().apply()
    }

    fun isMmLanguage(): Boolean {
        return sharePref.getBoolean(Language, true)
    }

    fun setMmLanguage(isMM: Boolean) {
        editor.putBoolean(Language, isMM).apply()
    }

    fun setObj(obj: Any) {
        editor.putString("OBJ", Gson().toJson(obj)).apply()
    }

    fun getObj(): Any {
        return Gson().fromJson<Any>(sharePref.getString("OBJ", ""), Any::class.java)
    }

    fun setUserModel(model: UserModel) {
        editor.putString(USER_MODEL, Gson().toJson(model)).apply()
    }

    fun getUserModel(): UserModel {
        return Gson().fromJson<UserModel>(sharePref.getString(USER_MODEL, ""), UserModel::class.java)
    }

    fun hasAuth(): Boolean {
        return sharePref.getBoolean(HAS_AUTH, false)
    }

    fun setAuth(isAuth: Boolean) {
        editor.putBoolean(HAS_AUTH, isAuth).apply()
    }

    fun setOAuthModel(oAuthModel: OAuthModel) {
        editor.putString(AUTH, Gson().toJson(oAuthModel)).apply()
    }

    fun getOAuthModel(): OAuthModel {
        return Gson().fromJson(sharePref.getString(AUTH, ""), OAuthModel::class.java)
    }

    fun setPhotoModel(list: PagedList<PhotoModel>) {
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(TEST_PHOTO, json).apply()


    }

    fun getPhotoModel(): String? {
        return sharePref.getString(TEST_PHOTO, null)
    }

}