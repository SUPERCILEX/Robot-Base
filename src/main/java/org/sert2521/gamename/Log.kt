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

fun verbose(tag: String? = null, message: Any) = log(LogLevel.VERBOSE, tag, message)
fun debug(tag: String? = null, message: Any) = log(LogLevel.DEBUG, tag, message)
fun info(tag: String? = null, message: Any) = log(LogLevel.INFO, tag, message)
fun warn(tag: String? = null, message: Any) = log(LogLevel.WARN, tag, message)
fun error(tag: String? = null, message: Any) = log(LogLevel.ERROR, tag, message)

private fun log(level: LogLevel, tag: String?, message: Any) {
    if (!isLoggable(level)) return
    println("$level/${if (tag == null || tag.isEmpty()) autoTag else tag}: $message")
}

private fun isLoggable(level: LogLevel) = level >= LOG_LEVEL
