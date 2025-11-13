// `settings.gradle.kts`
rootProject.name = "backend-luismi"

buildCache {
    local {
        isEnabled = true
        removeUnusedEntriesAfterDays = 7
    }
}
