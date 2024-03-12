import org.gradle.api.JavaVersion

object Config {
    const val compileSdk = 34
    const val targetSdk = compileSdk
    const val minSdk = 24
    const val versionCode = 55
    const val versionName = "1.0.49"

    const val applicationId = "com.speakingclub.app"
    const val namespacePrefix = applicationId

    const val jvmTarget = "17"

    val sourceCompatibility = JavaVersion.VERSION_17
    val targetCompatibility = sourceCompatibility

    const val composeUiVer = "1.5.0"
    const val composeCompilerVer = "1.5.0"
}