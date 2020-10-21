package au.com.princyj.routedemoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import au.com.princyj.notifications.BlueBlackHandler
import au.com.princyj.notifications.RedHandler
import au.com.princyj.router.Environment
import au.com.princyj.router.Route
import au.com.princyj.router.Router
import au.com.princyj.router.URLMatcher
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.net.URL

var bottomNavigation: BottomNavigationView? = null

class MainActivity : AppCompatActivity() {

    var count = 0
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nautilus_router)

        initialiseEnvironment()
        val router = Environment.shared.router

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }


        bundle.putInt("INDEX", 0)
        val route = Route(
            URL("https://router.com.au/blue"),
            bundle,
            this.supportFragmentManager,
            R.id.container
        )

        router.routeToFragment(route)

        val dashboardBundle = Bundle()
        dashboardBundle.putInt("INDEX", 1)

        val notificationBundle = Bundle()
        notificationBundle.putInt("INDEX", 2)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    bundle.putInt("COUNT", count)
                    val route = Route(
                        URL("https://router.com.au/blue"),
                        bundle,
                        this.supportFragmentManager,
                        R.id.container
                    )
                    router.routeToFragment(route)
                    bottomNavigation?.isSelected = true
                    true
                }
                R.id.dashboard -> {
                    val route = Route(
                        URL("https://router.com.au/accent"),
                        dashboardBundle,
                        this.supportFragmentManager,
                        R.id.container
                    )
                    router.routeToFragment(route)
                    bottomNavigation?.isSelected = true
                    true
                }
                R.id.notifications -> {
                    val route = Route(
                        URL("https://router.com.au/red"),
                        notificationBundle,
                        this.supportFragmentManager,
                        R.id.container
                    )
                    router.routeToFragment(route)
                    bottomNavigation?.isSelected = true
                    true
                }
                else -> false
            }
        }
    }

    private fun initialiseEnvironment() {
        val handlers = listOf(
            BlueHandler(),
            AccentHandler(),
            RedHandler(),
            BlueBlackHandler(),
            AccentChildHandler(),
            HomeBlueHandler(),
            GreenHandler()
        )
        val router = Router(handlers) {
            if (URLMatcher.pathMatches("/blueblack", it.url)) {
                bottomNavigation?.selectedItemId = R.id.notifications
                bottomNavigation?.isSelected = true
            } else if (URLMatcher.pathMatches("/homeblue", it.url)) {
                bottomNavigation?.selectedItemId = R.id.home
                bottomNavigation?.isSelected = true
            } else if (URLMatcher.pathMatchesAny(listOf("/green", "/green/(.*)+"), it.url)) {
                bottomNavigation?.selectedItemId = R.id.home
                bottomNavigation?.isSelected = true }
        }
        Environment.initialise(router)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}
