package au.com.princyj.routedemoapp

import au.com.princyj.router.Router

class Environment private constructor(val router: Router) {
    companion object {
        lateinit var shared: Environment
            private set

        fun initialise(router: Router) {
            if (::shared.isInitialized) throw IllegalStateException("Environment is already initialised")
            shared = Environment(router)
        }
    }
}
