package au.com.princyj.notifications

import au.com.princyj.router.Route
import au.com.princyj.router.RouteActionType
import au.com.princyj.router.RouteHandler
import au.com.princyj.router.URLMatcher
import java.net.URL

class RedHandler : RouteHandler {
    override fun handles(url: URL): Boolean {
        return URLMatcher.pathMatches("/red", url)
    }

    override fun action(route: Route): RouteActionType = RouteActionType.Navigation(NotificationsFragment::class.java)
}
