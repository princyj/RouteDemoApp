package au.com.princyj.router

import android.app.Activity
import androidx.fragment.app.Fragment
import java.net.URL

interface RouteHandler {
    fun <T> getRouteActivityOrFragment(): Class<out T>
    fun handles(url: URL): Boolean
    fun action(route: Route): RouteAction
}

class Route(val url: URL, val fragment: Fragment)

enum class RouteAction {
    NAVIGATION
}

class URLMatcher {
    companion object {
        fun pathMatches(pattern: String, url: URL) : Boolean =
            url.path.matches(pattern.toRegex())
    }
}
