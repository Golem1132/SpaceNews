package pl.golem.spacenews

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform