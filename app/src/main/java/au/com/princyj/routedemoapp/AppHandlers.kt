package au.com.princyj.routedemoapp

import au.com.princyj.notifications.NotificationsFragment
import au.com.princyj.router.Route
import au.com.princyj.router.RouteActionType
import au.com.princyj.router.RouteHandler
import au.com.princyj.router.URLMatcher
import java.net.URL

class AccentHandler : RouteHandler {

    override fun handles(url: URL): Boolean {
        return URLMatcher.pathMatches("/accent", url)
    }

    override fun action(route: Route): RouteActionType =
        RouteActionType.Navigation(DashboardFragment::class.java)

}

class BlueHandler : RouteHandler {

    override fun handles(url: URL): Boolean {
        return URLMatcher.pathMatches("/blue", url)
    }

    override fun action(route: Route): RouteActionType =
        RouteActionType.Navigation(HomeFragment::class.java)

}

class AccentChildHandler : RouteHandler {

    override fun handles(url: URL): Boolean {
        return URLMatcher.pathMatches("/accent_child", url)
    }

    override fun action(route: Route): RouteActionType =
        RouteActionType.Navigation(DashboardChildFragment::class.java)

}

class HomeBlueHandler : RouteHandler {

    override fun handles(url: URL): Boolean {
        return URLMatcher.pathMatches("/homeblue", url)
    }

    override fun action(route: Route): RouteActionType = RouteActionType.TabSwitch
}

class GreenHandler : RouteHandler {
    override fun handles(url: URL): Boolean {
        return URLMatcher.pathMatches("/green/(.*)+", url)
    }

    override fun action(route: Route): RouteActionType =
        RouteActionType.Sequence(
            listOf(
                RouteActionType.Navigation(NotificationsFragment::class.java),
                RouteActionType.TabSwitch,
                RouteActionType.Navigation(NotificationsDialogFragment::class.java)
            )
        )

}
