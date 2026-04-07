# 在线工具系统实现计划

> **版本**: v2.0 (单模块结构)
> **日期**: 2026-04-03 (更新: 2026-04-07)

**Goal:** 构建一个包含编码解码、日期时间、格式化、加解密等功能的在线工具系统

**Architecture:** DDD分层架构（通过包名区分），前后端不分离（Thymeleaf + AJAX），滑动窗口限流

**Tech Stack:** Spring Boot 3.5.12, Java 21, Maven, Vue 3, Element Plus, Thymeleaf

---

## 项目结构

```
tools-service/
├── pom.xml
└── src/main/
    └── java/com/tools/
        ├── domain/                    # 领域层 - 充血模型
        │   ├── encoder/              # 8个编码解码工具
        │   ├── datetime/             # 6个日期时间工具
        │   ├── formatter/            # 4个格式化工具
        │   ├── crypto/               # 10个加解密工具
        │   ├── common/               # 13个通用工具
        │   │   └── result/           # ToolResult共享对象
        │   ├── application/          # 5个应用服务
        │   ├── infrastructure/       # 限流基础设施
        │   │   └── ratelimit/       # 滑动窗口限流
        │   └── web/                  # 接口层
        │       ├── controller/       # ToolController
        │       └── dto/              # ToolResponseDTO
    └── resources/
        ├── application.yml
        ├── messages.properties
        ├── messages_en.properties
        └── templates/
            └── index.html
```

---

## 启动方式

```bash
# 编译打包
mvn clean package -DskipTests

# 运行
java -jar target/tools-service-1.0.0.jar
```

### 访问地址
- 首页: http://localhost:8080
- API: http://localhost:8080/api/*

---

## 功能清单

### Domain层 (com.tools.domain.*)

| 类 | 功能 |
|----|------|
| EncoderService/Impl | Base64、URL、HTML、Unicode编解码 |
| CryptoService/Impl | MD5、SHA、AES、DES、RSA加解密 |
| DateTimeService/Impl | 时间戳、日期格式、时区、日期间隔 |
| FormatterService/Impl | JSON、XML、SQL格式化 |
| CommonService/Impl | 颜色、进制、UUID、JWT、Cron、正则 |

### Application层 (com.tools.application)

| 类 | 委托给 |
|----|--------|
| EncoderApplicationService | Domain.encoder |
| CryptoApplicationService | Domain.crypto |
| DateTimeApplicationService | Domain.datetime |
| FormatterApplicationService | Domain.formatter |
| CommonApplicationService | Domain.common |

### Infrastructure层 (com.tools.infrastructure.ratelimit)

| 类 | 功能 |
|----|------|
| RateLimiter | 限流接口 |
| SlidingWindowRateLimiter | 滑动窗口实现 |
| RateLimiterConfig | Spring配置 |

### Web层 (com.tools.web)

| 类 | 功能 |
|----|------|
| WebApplication | Spring Boot启动类 |
| ToolController | 40+ REST API端点 |
| ToolResponseDTO | 统一响应格式 |

---

## 实施检查清单

- [x] 创建项目结构
- [x] 实现Domain层（5个领域服务）
- [x] 实现Application层（5个应用服务）
- [x] 实现Infrastructure层（限流）
- [x] 实现Web层（Controller + DTO）
- [x] 配置国际化
- [x] 创建前端页面
- [x] 编译测试
- [x] 打包部署
