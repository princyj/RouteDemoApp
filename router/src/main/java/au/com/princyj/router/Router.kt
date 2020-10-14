package au.com.princyj.router

import android.app.Activity
import android.content.Intent
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Router(handlers: List<RouteHandler>) {
    private val routeHandlers: List<RouteHandler>?

    init {
        this.routeHandlers = handlers
    }

    fun routeToFragment(route: Route) {
        val fragment = handleRoute(route)?.newInstance()
        route.bundle?.let { fragment?.arguments = it }
        when (route.presentationType) {
            PresentationType.DEFAULT -> fragment?.let {
                createFragmentInstance(
                    it,
                    route.activity,
                    route.containerViewIdRes
                )
            }
            PresentationType.CHILD -> fragment?.let {
                createChildFragmentInstance(
                    it,
                    route.activity,
                    route.containerViewIdRes
                )
            }
        }
        fragment?.let { createFragmentInstance(it, route.activity, route.containerViewIdRes) }
    }

    private fun createChildFragmentInstance(fragment: Fragment, activity: AppCompatActivity, @IdRes containerViewIdRes: Int) {
        fragment.setTargetFragment(fragment.parentFragment, 1)
        fragment.parentFragment?.let {
            it.childFragmentManager.beginTransaction()
                .replace(containerViewIdRes, fragment)
                .addToBackStack(null)
                .commit()
        }
        activity.supportFragmentManager?.popBackStackImmediate()
        val intent = Intent()
        intent.putExtra("bundle", fragment.arguments)
        fragment.parentFragment?.onActivityResult(1, Activity.RESULT_OK, intent)
    }

    private fun createFragmentInstance(fragment: Fragment, activity: AppCompatActivity, @IdRes containerViewIdRes: Int) {
            activity.supportFragmentManager.beginTransaction()
                .replace(containerViewIdRes, fragment)
                .addToBackStack(null)
                .commit()
    }

    private fun handleRoute(route: Route): Class<out Fragment>? {
        val handler = routeHandlers?.first { it.handles(route.url) }
        val action = handler?.action(route)

        return when (action) {
            is RouteAction.Navigation -> action.fragment
            else -> null
        }
    }
}
