package com.tools.domain.datetime;

import com.tools.domain.common.result.ToolResult;

/**
 * 日期时间服务接口 - 领域服务
 */
public interface DateTimeService {

    /**
     * 获取当前时间戳
     */
    ToolResult getCurrentTimestamp();

    /**
     * 时间戳转日期字符串
     */
    ToolResult timestampToDate(String timestamp, String format);

    /**
     * 日期字符串转时间戳
     */
    ToolResult dateToTimestamp(String dateStr, String format);

    /**
     * 日期格式转换
     */
    ToolResult convertDateFormat(String dateStr, String fromFormat, String toFormat);

    /**
     * 时区转换
     */
    ToolResult convertTimezone(String dateStr, String format, String fromZone, String toZone);

    /**
     * 计算日期间隔
     */
    ToolResult calculateDaysBetween(String startDate, String endDate, String format);
}
