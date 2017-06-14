package org.sert2521.gamename

enum class LogLevel {
    /** Priority constant for the log method; use Log.v. */
    VERBOSE,
    /** Priority constant for the log method; use Log.d. */
    DEBUG,
    /** Priority constant for the log method; use Log.i. */
    INFO,
    /** Priority constant for the log method; use Log.w. */
    WARN,
    /** Priority constant for the log method; use Log.e. */
    ERROR
}

private val autoTag: String get() {
    Thread.currentThread().stackTrace
            .filter { it.fileName != "LogKt" }
            .forEach { return it.className }
    return ""
}

fun logV(tag: String? = null, message: String) = log(LogLevel.VERBOSE, tag, message)
fun logD(tag: String? = null, message: String) = log(LogLevel.DEBUG, tag, message)
fun logI(tag: String? = null, message: String) = log(LogLevel.INFO, tag, message)
fun logW(tag: String? = null, message: String) = log(LogLevel.WARN, tag, message)
fun logE(tag: String? = null, message: String) = log(LogLevel.ERROR, tag, message)

private fun log(level: LogLevel, tag: String?, message: String) {
    if (!isLoggable(level)) return
    println("$level/${if (tag == null || tag.isEmpty()) autoTag else tag}: $message")
}

private fun isLoggable(level: LogLevel) = level >= LOG_LEVEL
