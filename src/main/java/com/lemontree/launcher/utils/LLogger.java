package com.lemontree.launcher.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LLogger {

    private final Logger logger;

    public LLogger(Class<?> clazz) {
        logger = Logger.getLogger(clazz.getName());
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter(clazz.getName()));
        logger.setUseParentHandlers(false); // 禁用默认的 ConsoleHandler
        logger.addHandler(consoleHandler);
    }

    public void setGlobalLogLevel(Level level) {
        logger.setLevel(level);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void warning(String msg) {
        logger.warning(msg);
    }

    public void severe(String msg) {
        logger.severe(msg);
    }

    private static class ColorFormatter extends Formatter {

        private final String className;
        private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss | ");

        public ColorFormatter(String className) {
            this.className = className;
        }

        @Override
        public String format(LogRecord record) {
            String color = getColorForLevel(record.getLevel().getName());
            String levelName = record.getLevel().getName();
            String msg = record.getMessage();

            // 时间前缀
            String prefixTime = ConsoleColor.RESET + ConsoleColor.WHITE + "[" + ConsoleColor.GREEN +
                    formatter.format(new Date(record.getMillis())) + record.getMillis() + ConsoleColor.WHITE + "]";

            // 等级前缀
            String prefixLevel = prefixTime + ConsoleColor.RESET + ConsoleColor.WHITE +
                    "[" + ConsoleFont.BOLD + color + levelName + ConsoleColor.RESET + ConsoleColor.WHITE + "] ";

            // 类名前缀
            String classPrefix = ConsoleColor.WHITE + "[" + ConsoleColor.MAGENTA + this.className + ConsoleColor.WHITE + "]";

            return classPrefix + prefixLevel + ConsoleColor.RESET + msg + "\n";
        }

        private String getColorForLevel(String level) {
            return switch (level.toLowerCase()) {
                case "warning" -> ConsoleColor.YELLOW;
                case "severe" -> ConsoleColor.RED;
                default -> ConsoleColor.CYAN;
            };
        }
    }

    // ANSI颜色和字体样式定义
    private interface ConsoleColor {
        String RESET = "\u001B[0m";
        String BLACK = "\u001B[30m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String BLUE = "\u001B[34m";
        String MAGENTA = "\u001B[35m";
        String CYAN = "\u001B[36m";
        String WHITE = "\u001B[37m";
    }

    private interface ConsoleFont {
        String BOLD = "\u001B[1m";
        String ITALIC = "\u001B[3m";
        String UNDERLINE = "\u001B[4m";
        String INVERSE = "\u001B[7m";
    }
}
