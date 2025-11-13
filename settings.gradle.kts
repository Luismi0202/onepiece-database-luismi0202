rootProject.name = "backend-luismi"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

buildCache {
    local {
        isEnabled = true
    }
}

gradle.startParameter.isBuildCacheEnabled = true
