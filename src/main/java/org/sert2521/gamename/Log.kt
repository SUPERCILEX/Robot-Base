package org.sert2521.gamename

import edu.wpi.first.wpilibj.Preferences

/** Checks if a log is loggable with the provided log level from SmartDashboard. */
private fun isLoggable(level: LogLevel) =
        level >= when (Preferences.getInstance().getString("log_level", "info")) {
            "verbose" -> LogLevel.VERBOSE
            "debug" -> LogLevel.DEBUG
            "warn" -> LogLevel.WARN
            "error" -> LogLevel.ERROR
            else -> LogLevel.INFO
        }

enum class LogLevel { VERBOSE, DEBUG, INFO, WARN, ERROR }

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
    if (isLoggable(level)) {
        println("$level/${if (tag == null || tag.isEmpty()) autoTag else tag}: $message")
    }
}
