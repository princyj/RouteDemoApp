package au.com.princyj.routedemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import au.com.princyj.notifications.NotificationsFragment
import au.com.princyj.notifications.RedHandler
import au.com.princyj.router.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.net.URL

var bottomNavigation: BottomNavigationView? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nautilus_router)


        val handlers = listOf(BlueHandler(), AccentHandler(), RedHandler())
        val router = Router(handlers)

        val route = Route(URL("https://router.com.au/blue"), HomeFragment())
        router.routeToFragment(route, this, R.id.container)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val route = Route(URL("https://router.com.au/blue"), HomeFragment())
                    router.routeToFragment(route, this, R.id.container)
                    true
                }
                R.id.dashboard -> {
                    val route = Route(URL("https://router.com.au/accent"), DashboardFragment())
                    router.routeToFragment(route, this, R.id.container)
                    true
                }
                R.id.notifications -> {
                    val route = Route(URL("https://router.com.au/red"), NotificationsFragment())
                    router.routeToFragment(route, this, R.id.container)
                    true
                }
                else -> false
            }
        }


    }

    class AccentHandler : RouteHandler {

        override fun <T> getRouteActivityOrFragment(): Class<out T> {
            return DashboardFragment().javaClass as Class<out T>
        }

        override fun handles(url: URL): Boolean {
            return URLMatcher.pathMatches("/accent", url)
        }

        override fun action(route: Route): RouteAction = RouteAction.NAVIGATION

    }

    class BlueHandler : RouteHandler {

        override fun <T> getRouteActivityOrFragment(): Class<out T> {
            return HomeFragment().javaClass as Class<out T>
        }

        override fun handles(url: URL): Boolean {
            return URLMatcher.pathMatches("/blue", url)
        }

        override fun action(route: Route): RouteAction = RouteAction.NAVIGATION

    }
}
