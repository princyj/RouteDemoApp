package au.com.princyj.router

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.net.URL

interface RouteHandler {
    fun handles(url: URL): Boolean
    fun action(route: Route): RouteAction
}

data class Route(
    val url: URL,
    val bundle: Bundle?,
    val activity: AppCompatActivity,
    @IdRes val containerViewIdRes: Int,
    val presentationType: PresentationType = PresentationType.DEFAULT
)

enum class PresentationType {
    DEFAULT,
    CHILD
}


sealed class RouteAction {
    data class Navigation(val fragment: Class<out Fragment>) : RouteAction()
    object REDIRECT : RouteAction()
}

class URLMatcher {
    companion object {
        fun pathMatches(pattern: String, url: URL): Boolean =
            url.path.matches(pattern.toRegex())
    }
}
