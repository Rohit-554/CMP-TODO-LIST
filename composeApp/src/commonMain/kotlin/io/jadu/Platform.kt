package io.jadu

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform