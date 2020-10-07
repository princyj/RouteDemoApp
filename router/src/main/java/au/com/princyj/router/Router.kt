package au.com.princyj.router

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Router(handlers: List<RouteHandler>) {
    private val routeHandlers: List<RouteHandler>?
    init {
        this.routeHandlers = handlers
    }

    fun routeToFragment(route: Route, activity: AppCompatActivity, @IdRes containerViewIdRes: Int) {
        val fragment = handleRoute(route)?.newInstance()
        fragment?.let {
            activity.supportFragmentManager.beginTransaction()
                .replace(containerViewIdRes, it)
                .commit()
        }
    }

//    fun routeToActivity(route: Route) {
//        val fragment = handleRoute(route)?.newInstance()
//        fragment?.let {
//            activity.supportFragmentManager.beginTransaction()
//                .replace(containerViewIdRes, it)
//                .commit()
//        }
//    }

    private fun handleRoute(route: Route) : Class<out Fragment>? {
        val handler = routeHandlers?.first { it.handles(route.url) }

        return when (handler?.action(route)) {
            RouteAction.NAVIGATION -> handler.getRouteActivityOrFragment()
            else -> null
        }
    }
}
