package au.com.princyj.routedemoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    val handlers = listOf(
        RedHandler(),
        MainActivity.AccentChildHandler(),
        MainActivity.BlueHandler()
    )
    val router = Router(handlers)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.router_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        index.text = arguments?.getInt("INDEX").toString()

        val notificationBundle = Bundle()
        notificationBundle.putInt("INDEX", 2)
        notificationBundle.putBoolean("BOOLEAN_VALUE", true)

        val route = Route(
            URL("https://router.com.au/red"),
            notificationBundle,
            activity as AppCompatActivity,
            R.id.container
        )

        nav_to_navigation.setOnClickListener {
            router.routeToFragment(route)
        }

        nav_to_child.setOnClickListener {
            val route = Route(
                URL("https://router.com.au/accent_child"),
                this.arguments,
                this.activity as AppCompatActivity,
                R.id.container,
                PresentationType.CHILD
            )
            router.routeToFragment(route)
        }

        Log.d("child", arguments?.getBoolean("BOOLEAN_VALUE").toString())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val test = arguments?.getBoolean("BOOLEAN_VALUE")
            Log.d("child", test.toString())
        }
    }
}
