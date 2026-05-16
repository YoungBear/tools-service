# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

**Online Tools Service** - 一个在线工具服务，提供编码解码、加密解密、日期时间、格式化等常用工具。

- 技术栈：Spring Boot 3.5.12 + Java 21
- 端口：18888
- 前端：Thymeleaf 单页（`src/main/resources/templates/index.html`），内置 Vue 3 + Element Plus SPA

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

## 架构（DDD 分层）

```
com.tools
├── domain/                    # 核心业务逻辑
│   ├── encoder/               # 编码解码服务
│   │   ├── EncoderService     # 接口
│   │   └── EncoderServiceImpl # 实现
│   ├── crypto/                # 加密解密服务
│   │   ├── CryptoService
│   │   └── CryptoServiceImpl
│   ├── datetime/              # 日期时间服务
│   │   ├── DateTimeService
│   │   └── DateTimeServiceImpl
│   ├── formatter/             # 格式化服务
│   │   ├── FormatterService
│   │   └── FormatterServiceImpl
│   ├── common/                # 通用工具
│   │   ├── CommonService
│   │   ├── CommonServiceImpl
│   │   └── result/            # 统一响应
│   │       ├── ToolResult      # 响应实体
│   │       └── ToolResultFactory # 响应工厂
├── application/               # 应用服务编排
│   ├── EncoderApplicationService
│   ├── CryptoApplicationService
│   ├── DateTimeApplicationService
│   ├── FormatterApplicationService
│   └── CommonApplicationService
├── infrastructure/             # 基础设施
│   └── ratelimit/             # 限流实现
│       ├── RateLimiter
│       ├── RateLimiterConfig
│       └── SlidingWindowRateLimiter
└── web/                        # Web 层
    ├── controller/
    │   ├── ToolController      # 工具 API
    │   └── I18nController      # 国际化
    ├── dto/
    │   └── ToolResponseDTO
    └── service/
        └── I18nService
```

## 工具模块

### 1. 编码解码（Encoder）
- Base64 编码/解码
- URL 编码/解码
- HTML 转义/反转义
- Unicode 编码/解码

### 2. 加密解密（Crypto）
- MD5 / SHA-1 / SHA-256 哈希
- AES 对称加密/解密
- DES 对称加密/解密
- RSA 非对称加密/解密 + 密钥对生成

### 3. 日期时间（DateTime）
- 获取当前时间戳
- 时间戳 ↔ 日期字符串转换
- 日期格式转换
- 时区转换
- 计算日期间隔

### 4. 格式化（Formatter）
- JSON 格式化/压缩
- XML 格式化
- SQL 美化

### 5. 通用工具（Common）
- 颜色转换：HEX ↔ RGB ↔ HSL
- 进制转换：二进制/十进制/十六进制
- UUID 生成
- JWT 解析
- Cron 表达式解析
- 正则表达式测试

## 关键模式

- 所有工具服务返回 `ToolResult<T>`，包含 `success`、`data`、`error`、`timestamp` 字段
- 使用 `ToolResultFactory.success(data)` 或 `ToolResultFactory.error(code, message)` 构建响应
- 限流：基于 IP 的滑动窗口算法，可通过 `application.yml` 中的 `tools.ratelimit.*` 配置
- 国际化：中文 `messages.properties`、英文 `messages_en.properties`、简体中文 `messages_zh_CN.properties`

## API 约定

- 基础路径：`/api`
- 前端请求统一使用 `input` 作为输入字段名
- 后端接口兼容 `input` 和原有字段名（如 `timestamp`、`dateStr` 等）
- 时间戳接口支持 GET 和 POST 两种方法
- 时间戳转换自动识别秒（10位）和毫秒（13位）格式
- 日期转时间戳支持纯日期格式（如 `yyyy-MM-dd`）和日期时间格式

## 配置文件

`src/main/resources/application.yml`:
```yaml
server:
  port: 18888
  address: 0.0.0.0

spring:
  application:
    name: tools-service
  messages:
    basename: messages
    default-locale: zh-CN

tools:
  ratelimit:
    enabled: true
    window-size: 60      # 窗口大小（秒）
    max-requests: 60     # 窗口内最大请求数
```

## 主要依赖

- Spring Boot 3.5.12
- Spring Boot Web
- Spring Boot Thymeleaf
- Jackson (JSON/XML)
- Java 21
