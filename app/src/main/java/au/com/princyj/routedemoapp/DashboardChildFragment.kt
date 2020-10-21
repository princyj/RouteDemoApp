package au.com.princyj.routedemoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import au.com.princyj.router.Environment
import au.com.princyj.router.Route
import kotlinx.android.synthetic.main.dashboard_child.*
import java.net.URL

class DashboardChildFragment : Fragment() {
    val router = Environment.shared.router

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dashboard_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = Bundle()
        bundle.putBoolean("BOOLEAN_VALUE", true)
        nav_to_navigation.setOnClickListener {
            router.returnResultOnOK(this, bundle)
        }

        tab_switch_to_home.setOnClickListener {
            val route = this.targetFragment?.parentFragmentManager?.let { it1 ->
                Route(
                    URL("https://router.com.au/homeblue"),
                    null,
                    it1,
                    R.id.container
                )
            }
            route?.let { router.routeToFragment(it) }
        }
    }
}
