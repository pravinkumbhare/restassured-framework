package com.demo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Centralized logger utility.
 * Use: LoggerUtil.getLogger().info("message");
 */
public class LoggerUtil {

    // Returns logger for the calling class dynamically
    public static Logger getLogger() {
        // This ensures logger is tied to the caller’s class name
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }
}
