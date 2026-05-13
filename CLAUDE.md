# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 构建与运行

```bash
# 构建（跳过测试）
mvn clean package -DskipTests

# 运行
java -jar target/tools-service-1.0.0.jar

# 运行单个测试
mvn test -Dtest=类名#方法名

# 构建并运行测试
mvn clean package
```

## 架构

**DDD 分层：**
- `domain/` - 核心业务逻辑（编码器、日期时间、格式化、加密解密、通用工具）。每个模块有 Service 接口 + Impl。使用 `ToolResult` / `ToolResultFactory` 返回统一响应。
- `application/` - 编排领域服务（EncoderApplicationService 等）
- `infrastructure/` - 滑动窗口算法的限流实现（SlidingWindowRateLimiter）
- `web/` - 控制器（ToolController）+ 数据传输对象（ToolResponseDTO）

**前端：** Thymeleaf 单页（`src/main/resources/templates/index.html`），内置 Vue 3 + Element Plus SPA。

## 关键模式

- 所有工具服务返回 `ToolResult<T>`，包含 `success`、`data`、`error`、`timestamp` 字段
- 使用 `ToolResultFactory.success(data)` 或 `ToolResultFactory.error(code, message)` 构建响应
- 限流基于 IP 的滑动窗口算法，可通过 `application.yml` 中的 `tools.ratelimit.*` 配置
- 国际化：中文 `messages.properties` 和英文 `messages_en.properties`

## API 约定

- 前端请求统一使用 `input` 作为输入字段名
- 后端接口需兼容 `input` 和原有字段名（如 `timestamp`、`dateStr` 等），使用 `body.getOrDefault("input", body.get("原始字段名"))` 获取值
- 时间戳接口支持 GET 和 POST 两种方法
- 时间戳转换自动识别秒（10位）和毫秒（13位）格式
- 日期转时间戳支持纯日期格式（如 `yyyy-MM-dd`）和日期时间格式