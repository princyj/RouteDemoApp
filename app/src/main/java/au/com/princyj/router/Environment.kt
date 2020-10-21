package au.com.princyj.router

open class Environment private constructor(val router: Router) {
    companion object {
        lateinit var shared: Environment
            private set

        fun initialise(router: Router) {
            if (Companion::shared.isInitialized) throw IllegalStateException("Environment is already initialised")
            shared = Environment(router)
        }
    }
}
