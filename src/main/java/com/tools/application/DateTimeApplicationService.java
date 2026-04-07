package com.tools.application;

import com.tools.domain.common.result.ToolResult;
import com.tools.domain.datetime.DateTimeService;
import com.tools.domain.datetime.DateTimeServiceImpl;

/**
 * 日期时间应用服务 - 用例编排
 */
public class DateTimeApplicationService {

    private final DateTimeService dateTimeService;

    public DateTimeApplicationService() {
        this.dateTimeService = new DateTimeServiceImpl();
    }

    public ToolResult getCurrentTimestamp() {
        return dateTimeService.getCurrentTimestamp();
    }

    public ToolResult timestampToDate(String timestamp, String format) {
        return dateTimeService.timestampToDate(timestamp, format);
    }

    public ToolResult dateToTimestamp(String dateStr, String format) {
        return dateTimeService.dateToTimestamp(dateStr, format);
    }

    public ToolResult convertDateFormat(String dateStr, String fromFormat, String toFormat) {
        return dateTimeService.convertDateFormat(dateStr, fromFormat, toFormat);
    }

    public ToolResult convertTimezone(String dateStr, String format, String fromZone, String toZone) {
        return dateTimeService.convertTimezone(dateStr, format, fromZone, toZone);
    }

    public ToolResult calculateDaysBetween(String startDate, String endDate, String format) {
        return dateTimeService.calculateDaysBetween(startDate, endDate, format);
    }
}
