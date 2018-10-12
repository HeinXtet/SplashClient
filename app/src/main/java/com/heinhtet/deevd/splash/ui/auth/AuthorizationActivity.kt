package com.heinhtet.deevd.splash.ui.auth

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import butterknife.BindAnim
import butterknife.BindView
import com.heinhtet.deevd.splash.R
import com.heinhtet.deevd.splash.base.baseview.BaseActivity
import com.heinhtet.deevd.splash.utils.log.L
import com.heinhtet.deevd.splash.R.id.progressBar
import com.heinhtet.deevd.splash.R.id.webView
import com.heinhtet.deevd.splash.base.baseutils.*
import com.heinhtet.deevd.splash.ui.main.MainViewModel


/**
 * Created by Hein Htet on 10/12/18.
 */
class AuthorizationActivity : BaseActivity() {

    @BindView(R.id.progressBar)
    lateinit var progressBar: ProgressBar
    @BindView(R.id.webView)
    lateinit var webView: WebView
    private val TAG = "AuthorizationAct : "
    private lateinit var authViewModel: AuthViewModel


    override fun getLayoutId(): Int {
        return R.layout.authorization_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        authViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this)).get(AuthViewModel::class.java)
        authViewModel.oauth.observe(this, Observer {
            if (it != null) {
                L.i(TAG, "success OAuth $it")
                authViewModel.getMe(it.accessToken)
            }
        })
        authViewModel.userModel.observe(this, Observer {
            if (it != null) {
                L.i(TAG, "success Me $it")
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = webViewClient
        webView.webChromeClient = webChromeClient
        webView.loadUrl(AUTH_URL)
    }


    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar.progress = newProgress
            if (newProgress == 100) {
                progressBar.visibility = View.GONE

            } else {
                progressBar.visibility = View.VISIBLE
            }
        }
    }
    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            L.i(TAG, "loading Url $url")
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view?.loadUrl(request?.url.toString())
                L.i(TAG, "loading Url above LOLLIPOP ${request?.url.toString()}")
                return true
            }
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.visibility = View.VISIBLE
            progressBar.progress = 0
        }

        override fun onPageFinished(view: WebView?, url: String) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
            progressBar.progress = 100
            L.i(TAG, " url loading finished $url")
            if (url.contains("$REDIRTECT_URI?code")) {
                val code = AUTH_REGEX.matchEntire(url)?.groups?.get(2)?.value
                if (code != null) {
                    authViewModel.oAuth(code)
                }
            }
        }
    }
}