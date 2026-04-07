package com.tools.domain.datetime;

import com.tools.domain.common.result.ToolResult;
import com.tools.domain.common.result.ToolResultFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 日期时间服务实现 - 充血模型
 */
public class DateTimeServiceImpl implements DateTimeService {

    @Override
    public ToolResult getCurrentTimestamp() {
        long timestamp = System.currentTimeMillis();
        return ToolResultFactory.ok(String.valueOf(timestamp));
    }

    @Override
    public ToolResult timestampToDate(String timestamp, String format) {
        if (timestamp == null || timestamp.isEmpty()) {
            return ToolResultFactory.fail("Timestamp cannot be null or empty");
        }
        try {
            long ts = Long.parseLong(timestamp);
            LocalDateTime dateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(ts), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    format != null && !format.isEmpty() ? format : "yyyy-MM-dd HH:mm:ss");
            return ToolResultFactory.ok(dateTime.format(formatter));
        } catch (Exception e) {
            return ToolResultFactory.fail("Invalid timestamp format");
        }
    }

    @Override
    public ToolResult dateToTimestamp(String dateStr, String format) {
        if (dateStr == null || dateStr.isEmpty()) {
            return ToolResultFactory.fail("Date string cannot be null or empty");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    format != null && !format.isEmpty() ? format : "yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
            long timestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            return ToolResultFactory.ok(String.valueOf(timestamp));
        } catch (Exception e) {
            return ToolResultFactory.fail("Invalid date format");
        }
    }

    @Override
    public ToolResult convertDateFormat(String dateStr, String fromFormat, String toFormat) {
        if (dateStr == null || dateStr.isEmpty()) {
            return ToolResultFactory.fail("Date string cannot be null or empty");
        }
        try {
            DateTimeFormatter fromFormatter = DateTimeFormatter.ofPattern(fromFormat);
            DateTimeFormatter toFormatter = DateTimeFormatter.ofPattern(toFormat);
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, fromFormatter);
            return ToolResultFactory.ok(dateTime.format(toFormatter));
        } catch (Exception e) {
            return ToolResultFactory.fail("Date format conversion failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult convertTimezone(String dateStr, String format, String fromZone, String toZone) {
        if (dateStr == null || dateStr.isEmpty()) {
            return ToolResultFactory.fail("Date string cannot be null or empty");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    format != null && !format.isEmpty() ? format : "yyyy-MM-dd HH:mm:ss");
            ZonedDateTime fromDateTime = ZonedDateTime.parse(dateStr,
                    formatter.withZone(ZoneId.of(fromZone)));
            ZonedDateTime toDateTime = fromDateTime.withZoneSameInstant(ZoneId.of(toZone));
            return ToolResultFactory.ok(toDateTime.format(formatter));
        } catch (Exception e) {
            return ToolResultFactory.fail("Timezone conversion failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult calculateDaysBetween(String startDate, String endDate, String format) {
        if (startDate == null || endDate == null) {
            return ToolResultFactory.fail("Start date and end date cannot be null");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    format != null && !format.isEmpty() ? format : "yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            long days = Math.abs(ChronoUnit.DAYS.between(start, end));
            return ToolResultFactory.ok(String.valueOf(days));
        } catch (Exception e) {
            return ToolResultFactory.fail("Days calculation failed: " + e.getMessage());
        }
    }
}
