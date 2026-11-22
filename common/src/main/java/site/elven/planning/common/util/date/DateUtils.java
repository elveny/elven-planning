package site.elven.planning.common.util.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DateUtils {
    // 定义支持的常见日期格式（按优先级排列）
    private static final List<String> SUPPORTED_DATE_PATTERNS = Arrays.asList(
            "yyyy-MM-dd",
            "yyyy/MM/dd",
            "dd-MM-yyyy",
            "dd/MM/yyyy",
            "yyyyMMdd",
            "yyyy.MM.dd",
            "MM-dd-yyyy",
            "MM/dd/yyyy",
            "MMM dd, yyyy",
            "dd MMM yyyy",
            "yyyy年MM月dd日"
    );

    // 缓存 formatter 实例以提高性能
    private static final List<DateTimeFormatter> DATE_FORMATTERS = SUPPORTED_DATE_PATTERNS.stream()
            .map(DateTimeFormatter::ofPattern)
            .toList();

    // 默认日期格式
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** 常用格式：yyyy-MM-dd HH:mm:ss，线程安全，可静态复用 */
    public static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());

    /** 紧凑格式：yyyyMMddHHmmss，适合 resetOffsetByTimestamp 的 timestamp 参数 */
    public static final DateTimeFormatter COMPACT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneId.systemDefault());

    /**
     * 解析日期字符串
     *
     * @param dateStr 日期字符串
     * @return 解析后的 LocalDate 对象，如果解析失败则返回 null
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        String str = dateStr.trim();

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(str, formatter);
            } catch (DateTimeParseException ignored) {
                // 尝试下一个格式
            }
        }
        return null; // 所有格式都不匹配
    }

    /**
     * 检查日期字符串是否有效
     *
     * @param dateStr 日期字符串
     * @return true 表示有效，false 表示无效
     */
    public static boolean isValidDate(String dateStr) {
        return parseDate(dateStr) != null;
    }

    /**
     * 获取匹配的日期格式
     *
     * @param dateStr 日期字符串
     * @return 匹配的日期格式，如果没有匹配的格式则返回 null
     */
    public static String matchedDatePattern(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        String str = dateStr.trim();

        for (int i = 0; i < DATE_FORMATTERS.size(); i++) {
            try {
                LocalDate.parse(str, DATE_FORMATTERS.get(i));
                return SUPPORTED_DATE_PATTERNS.get(i);
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }


    public static String formatDate(LocalDate date) {
        if (date == null) return null;
        return date.format(DEFAULT_DATE_FORMATTER);
    }

    public static String formatDate(LocalDate date, String pattern) {
        if (date == null || pattern == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    /** 毫秒时间戳 → "yyyy-MM-dd HH:mm:ss.SSS" */
    public static String millisToString(long millis) {
        return DEFAULT_FORMATTER.format(Instant.ofEpochMilli(millis));
    }

    /** 毫秒时间戳 → "yyyyMMddHHmmss"（RocketMQ 部分 API 需要这个格式） */
    public static String millisToCompactString(long millis) {
        return COMPACT_FORMATTER.format(Instant.ofEpochMilli(millis));
    }

    /** "yyyy-MM-dd HH:mm:ss" → 毫秒时间戳 */
    public static long stringToMillis(String text) {
        return LocalDateTime.parse(text, DEFAULT_FORMATTER)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    /** 毫秒时间戳 → Date */
    public static Date millisToDate(long millis) {
        return Date.from(Instant.ofEpochMilli(millis));
    }

    /** Date → 毫秒时间戳 */
    public static long dateToMillis(Date date) {
        return date.toInstant().toEpochMilli();
    }

    // ---------- 秒级与毫秒级互转（RocketMQ 常见坑） ----------

    /** 秒级时间戳 → 毫秒级。RocketMQ 的 storeTimestamp 是毫秒，但部分外部系统给的是秒 */
    public static long secondsToMillis(long seconds) {
        return seconds * 1000L;
    }

    /** 毫秒级 → 秒级 */
    public static long millisToSeconds(long millis) {
        return millis / 1000L;
    }
}
