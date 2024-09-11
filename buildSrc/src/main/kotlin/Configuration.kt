object Configuration {
    val defaultConfig: DefaultConfig = DefaultConfig()
}

data class DefaultConfig(
    val packageName: String = "com.michael.baseapp",
    val targetSdk: Int = 34,
    val compileSdk: Int = 34,
    val minSdk: Int = 26,
    val versionCode: Int =  1,
    val versionName: String = "1.0",
)
