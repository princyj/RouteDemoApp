package au.com.princyj.routedemoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import au.com.princyj.notifications.RedHandler
import au.com.princyj.router.Router
import kotlinx.android.synthetic.main.dashboard_child.*

class DashboardChildFragment : Fragment() {

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
        return inflater.inflate(R.layout.dashboard_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = Bundle()
        bundle.putBoolean("BOOLEAN_VALUE", true)
        nav_to_navigation.setOnClickListener {
            router.resultAndOK(this, bundle)
        }
    }
}
