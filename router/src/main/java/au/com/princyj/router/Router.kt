package au.com.princyj.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

private const val EXTRA_NAME_RESULT = "EXTRA_NAME_RESULT"

class Router(handlers: List<RouteHandler>) {
    private val routeHandlers: List<RouteHandler>?


    init {
        this.routeHandlers = handlers
    }

    fun routeToFragment(route: Route) {
        val fragment = handleRoute(route)?.newInstance()
        route.bundle?.let { fragment?.arguments = it }
        fragment?.let {
                launchFragmentInstance(
                    it,
                    route.fragmentManager,
                    route.containerViewIdRes
                )
            }
    }

    fun routeToFragmentWithResultOK(route: Route) {
        val fragment = handleRoute(route)?.newInstance()
        route.bundle?.let { fragment?.arguments = it }
        fragment?.let {
            launchFragmentWithResultOK(
                it,
                route.fragmentManager,
                route.containerViewIdRes
            )
        }
    }

    private fun launchFragmentInstance(fragment: Fragment, fragmentManager: FragmentManager, @IdRes containerViewIdRes: Int) {
        fragmentManager.beginTransaction()
            .replace(containerViewIdRes, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun launchFragmentWithResultOK(fragment: Fragment, fragmentManager: FragmentManager, @IdRes containerViewIdRes: Int) {
        val parentFragment = fragmentManager.findFragmentById(containerViewIdRes)!!
        fragmentManager.beginTransaction()
            .replace(containerViewIdRes, fragment)
            .addToBackStack(null)
            .commit()
        fragment.setTargetFragment(parentFragment, 1)
    }

    fun returnResultOnOK(fragment: Fragment, data: Bundle) {
        val intent = Intent()
        intent.putExtras(data)
        fragment.targetFragment?.onActivityResult(1, Activity.RESULT_OK, intent)
        fragment.fragmentManager?.popBackStackImmediate()
    }

    private fun handleRoute(route: Route): Class<out Fragment>? {
        val handler = routeHandlers?.first { it.handles(route.url) }

        return when (val action = handler?.action(route)) {
            is RouteActionType.Navigation -> action.destination
            else -> null
        }
    }
//
//    val routerEntityList = mutableListOf<RouterEntity>()
//
//    fun registerRouterEntity(routerEntity: RouterEntity):Boolean{
//        val oldRouter = routerEntityList.firstOrNull{it.routePath.equals(routerEntity.routePath)}
//        if(oldRouter==null) {
//            routerEntityList.add(routerEntity)
//            return true
//        }
//        else{
//            return false
//        }
//    }
//
//    private fun getRouteNavigationType(route: Route) : RouteActionType? {
//        val routerEntity = routerEntityList.firstOrNull{URLMatcher.pathMatches(it.routePath, route.url)}
//        return routerEntity?.navigationType
//    }

}
