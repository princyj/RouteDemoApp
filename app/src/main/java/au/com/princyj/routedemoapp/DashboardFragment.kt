package au.com.princyj.routedemoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import au.com.princyj.notifications.RedHandler
import au.com.princyj.router.PresentationType
import au.com.princyj.router.Route
import au.com.princyj.router.Router
import kotlinx.android.synthetic.main.router_dashboard.*
import java.net.URL

class DashboardFragment : Fragment() {

    val TAG = "Dashboard"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.router_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        index.text = arguments?.getInt("INDEX").toString()

        val handlers = listOf(
            MainActivity.BlueHandler(),
            MainActivity.AccentHandler(), RedHandler()
        )
        val router = Router(handlers)

        val notificationBundle = Bundle()
        notificationBundle.putInt("INDEX", 2)

        nav_to_navigation.setOnClickListener {
            val route = Route(URL("https://router.com.au/red"), notificationBundle, activity as AppCompatActivity, R.id.container, PresentationType.CHILD)
            router.routeToFragment(route)
        }
    }
}
