package com.heinhtet.deevd.splash.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import butterknife.BindView
import com.heinhtet.deevd.splash.R
import com.heinhtet.deevd.splash.base.baseview.BaseActivity
import com.heinhtet.deevd.splash.extensions.load
import com.heinhtet.deevd.splash.model.response.UserModel
import com.heinhtet.deevd.splash.utils.Auth.Auth
import com.heinhtet.deevd.splash.utils.view.ViewHelper
import kotlinx.android.synthetic.main.activity_home.*
import android.view.WindowManager
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_VISIBLE
import android.os.Build
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.heinhtet.deevd.splash.extensions.h
import com.heinhtet.deevd.splash.ui.home_fragment.HomeFragment
import com.heinhtet.deevd.splash.utils.log.L
import kotlinx.android.synthetic.main.toolbar_layout.*


/**
 * Created by Hein Htet on 10/13/18.
 */
class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return true
    }

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var viewHelper: ViewHelper
    private val TAG = " HomeActivity : "
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.drawer_layout)
    lateinit var drawerLayout: DrawerLayout
    @BindView(R.id.nav_view)
    lateinit var navView: NavigationView
    @BindView(R.id.profile_iv)
    lateinit var profileIv: AppCompatImageView
    @BindView(R.id.appbar)
    lateinit var appbar: AppBarLayout
    private lateinit var userModel: UserModel


    override fun getLayoutId() = R.layout.activity_home


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        viewHelper = ViewHelper(this)
        viewHelper.initStatusBar(this, toolbar = toolbar)
        toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        if (Auth.hasAuth()) {
            initAuthState()
        }
        scrollChange()
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
    }

    override fun onResume() {
        super.onResume()
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            // Note that system bars will only be "visible" if none of the
            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                hideSystemUI()
            }
        }
        hideSystemUI()
    }

    private fun hideSystemUI() {
        if (resources.getBoolean(R.bool.is_land)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE)
            }
        }
    }


    @SuppressLint("ObsoleteSdkInt")
    private fun scrollChange() {
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                // Collapsed
                this.appbar.animate().translationY(0f).start()
                this.nestedScrollView.setPadding(0, 0, 0, 0)
                this.drawerLayout.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_FULLSCREEN

            } else if (verticalOffset == 0) {
                // Fully Expanded - show the status bar
                if (Build.VERSION.SDK_INT >= 16) {
                    this.nestedScrollView.setPadding(0, viewHelper.convertDpToPixel(16f).toInt(), 0, 0)
                    this.appbar.animate().translationY(54f).start()
                    drawerLayout.systemUiVisibility = (View.SYSTEM_UI_FLAG_VISIBLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                } else {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                }
            } else {
            }
        })
    }

    private fun initAuthState() {
        userModel = Auth.getUserModel()
        profileIv.load(R.drawable.pp, true)
        viewHelper.setUpNavigationProfileHeader(navView,userModel)

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}