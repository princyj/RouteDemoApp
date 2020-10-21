package au.com.princyj.router

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.net.URL

interface RouteHandler {
    fun handles(url: URL): Boolean
    fun action(route: Route): RouteActionType
}

data class Route(
    val url: URL,
    val bundle: Bundle?,
    val fragmentManager: FragmentManager,
    @IdRes val containerViewIdRes: Int
)

data class RouterEntity(val routePath: String, val navigationType: RouteActionType)

sealed class RouteActionType {
    data class Navigation(val destination: Class<out Fragment>) : RouteActionType()
    object TabSwitch : RouteActionType()
    class Sequence(val actions: List<RouteActionType>) : RouteActionType()
}

//redirect -> Bolt(not logged in) -> redirect to Login screen


class URLMatcher {
    companion object {
        fun pathMatches(pattern: String, url: URL): Boolean =
            url.path.matches(pattern.toRegex())

        fun pathMatchesAny(patterns: List<String>, url: URL): Boolean {
            patterns.forEach {
                if (pathMatches(it, url)) {
                    return true
                }
            }
            return false
        }

    }
}
