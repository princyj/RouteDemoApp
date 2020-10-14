package au.com.princyj.routedemoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import au.com.princyj.notifications.RedHandler
import au.com.princyj.router.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.net.URL

var bottomNavigation: BottomNavigationView? = null

class MainActivity : AppCompatActivity() {

    var count = 0
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nautilus_router)

        val toolbar =  findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val handlers = listOf(BlueHandler(), AccentHandler(), RedHandler())
        val router = Router(handlers)


        bundle.putInt("INDEX", 0)
        val route = Route(URL("https://router.com.au/blue"), bundle, this, R.id.container)

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
                    val route = Route(URL("https://router.com.au/blue"), bundle, this, R.id.container)
                    router.routeToFragment(route)
                    true
                }
                R.id.dashboard -> {
                    val route = Route(URL("https://router.com.au/accent"), dashboardBundle, this, R.id.container)
                    router.routeToFragment(route)
                    true
                }
                R.id.notifications -> {
                    val route = Route(URL("https://router.com.au/red"), notificationBundle, this, R.id.container)
                    router.routeToFragment(route)
                    true
                }
                else -> false
            }
        }
    }

    class AccentHandler : RouteHandler {

        override fun handles(url: URL): Boolean {
            return URLMatcher.pathMatches("/accent", url)
        }

        override fun action(route: Route): RouteAction = RouteAction.Navigation(DashboardFragment::class.java)

    }

    class BlueHandler : RouteHandler {

        override fun handles(url: URL): Boolean {
            return URLMatcher.pathMatches("/blue", url)
        }

        override fun action(route: Route): RouteAction = RouteAction.Navigation(HomeFragment::class.java)

    }

    class AccentChildHandler : RouteHandler {

        override fun handles(url: URL): Boolean {
            return URLMatcher.pathMatches("/accent_child", url)
        }

        override fun action(route: Route): RouteAction = RouteAction.Navigation(DashboardChildFragment::class.java)

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}
