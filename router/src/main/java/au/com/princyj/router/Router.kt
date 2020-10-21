package au.com.princyj.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

private const val EXTRA_NAME_RESULT = "EXTRA_NAME_RESULT"

class Router(handlers: List<RouteHandler>, tabSwitchHandler: (route: Route) -> Unit = {}) {
    private val routeHandlers: List<RouteHandler>?
    private val tabSwitchActionHandlerCallback: (route: Route) -> Unit

    init {
        this.routeHandlers = handlers
        this.tabSwitchActionHandlerCallback = tabSwitchHandler
    }

    fun routeToFragment(route: Route) {
        executeAction(handleRoute(route), route)
    }

    fun routeToFragmentWithResultOK(route: Route) {
        executeAction(handleRoute(route), route, true)
    }

    private fun handleRoute(route: Route): RouteActionType {
        val handler = routeHandlers?.first { it.handles(route.url) }
        return handler?.action(route)!!
    }

    private fun launchFragmentInstance(
        fragment: Fragment,
        route: Route
    ) {
        route.fragmentManager.beginTransaction()
            .replace(route.containerViewIdRes, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun launchFragmentWithResultOK(
        fragment: Fragment, route: Route
    ) {
        val parentFragment = route.fragmentManager.findFragmentById(route.containerViewIdRes)!!
        route.fragmentManager.beginTransaction()
            .replace(route.containerViewIdRes, fragment)
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

    private fun executeAction(
        action: RouteActionType,
        route: Route,
        withOKResult: Boolean = false
    ) {
        when (action) {
            is RouteActionType.Navigation -> handleNavigation(route, action, withOKResult)
            is RouteActionType.TabSwitch -> handleTabSwitch(route)
            is RouteActionType.Sequence -> executeActions(action.actions, route)
        }
    }

    private fun executeActions(actions: List<RouteActionType>, route: Route) {
        var newRoute: Route?
        if (actions.isEmpty()) return
        val mutableList = actions.toMutableList()

        val next = mutableList.first()
        mutableList.remove(next)
//        if (next is RouteActionType.Navigation) {
//            val f = next.destination
//            val parentFragment = route.fragmentManager.findFragmentById(route.containerViewIdRes)!!
//        }

        executeAction(next, route)
//        newRoute = Route(route.url, route.bundle, route.fragmentManager, route.containerViewIdRes)

        executeActions(mutableList, route)

    }

    private fun handleTabSwitch(route: Route) {
        tabSwitchActionHandlerCallback.invoke(route)
    }

    private fun handleNavigation(
        route: Route,
        actionType: RouteActionType.Navigation,
        withOKResult: Boolean
    ) {
        val fragment = actionType.destination.newInstance()
        route.bundle?.let { fragment.arguments = it }
        if (fragment is DialogFragment) {
            launchDialogFragment(fragment, route.fragmentManager)
        } else {
            if (withOKResult) {
                launchFragmentWithResultOK(fragment, route)
            } else {
                launchFragmentInstance(fragment, route)
            }
        }
    }

    private fun launchDialogFragment(fragment: DialogFragment, fragmentManager: FragmentManager) {
        fragment.show(fragmentManager, fragment.tag)
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
