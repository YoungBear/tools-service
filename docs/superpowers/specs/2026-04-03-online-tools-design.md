# 在线工具系统设计文档

**版本**: v2.0
**日期**: 2026-04-03 (更新: 2026-04-07)
**状态**: 已完成

---

## 1. 项目概述

### 1.1 项目目标
构建一个提供常用在线工具的Web系统，包括编码解码、日期时间转换、格式化、加解密等多种实用工具。

### 1.2 核心功能需求

| 类别 | 工具列表 |
|------|----------|
| 编码解码 | Base64编解码、URL编解码、HTML转义、Unicode转换 |
| 日期时间 | 时间戳转换、日期格式互转、时区转换、计算日期间隔 |
| 格式化 | JSON格式化、JSON压缩、XML格式化、SQL美化 |
| 加解密 | MD5、SHA-1/SHA-256、AES加密解密、DES加密解密、RSA加密解密 |
| 其他工具 | 颜色转换(HEX/RGB/HSL)、进制转换(2/10/16)、UUID生成、JWT解析、Cron表达式解析、正则表达式测试 |

### 1.3 非功能需求

- **框架**: Spring Boot 3.5.12 + Java 21 + Maven
- **前端**: Vue 3 + Element Plus，前后端不分离（Thymeleaf + AJAX）
- **架构**: DDD（领域驱动设计）充血模型，单模块结构
- **限流**: 滑动窗口算法，防止DDOS攻击
- **国际化**: 支持中英文切换

---

## 2. 技术架构

### 2.1 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 基础框架 | Spring Boot | 3.5.12 |
| Java版本 | OpenJDK | 21 |
| 构建工具 | Apache Maven | 3.9+ |
| 前端框架 | Vue.js | 3.x |
| UI组件库 | Element Plus | 2.x |
| 模板引擎 | Thymeleaf | - |
| 国际化 | Spring i18n | - |

### 2.2 项目结构（单模块 + 包分隔）

```
tools-service/
├── pom.xml
└── src/main/
    └── java/com/tools/
        ├── domain/                    # 领域层 - 充血模型
        │   ├── encoder/              # 编码解码领域
        │   ├── datetime/            # 日期时间领域
        │   ├── formatter/           # 格式化领域
        │   ├── crypto/             # 加解密领域
        │   └── common/             # 通用工具领域
        │       └── result/         # 共享结果对象
        ├── application/             # 应用层 - 用例编排
        ├── infrastructure/           # 基础设施层
        │   └── ratelimit/          # 滑动窗口限流实现
        └── web/                    # 接口层
            ├── controller/          # REST API控制器
            └── dto/                # 数据传输对象
```

### 2.3 DDD分层职责

| 层级 | 包路径 | 职责 | 关键组件 |
|------|--------|------|----------|
| Domain（领域层） | com.tools.domain.* | 业务逻辑、充血模型 | DomainService、Entity、ValueObject |
| Application（应用层） | com.tools.application | 用例编排、事务边界 | ApplicationService |
| Infrastructure（基础设施层） | com.tools.infrastructure.* | 限流、持久化适配 | RateLimiter、Config |
| Interface（接口层） | com.tools.web.* | 接收请求、返回响应 | Controller、DTO |

---

## 3. 功能模块设计

### 3.1 编码解码模块 (com.tools.domain.encoder)

| 工具 | 输入 | 输出 |
|------|------|------|
| Base64编码 | 原始字符串 | Base64字符串 |
| Base64解码 | Base64字符串 | 原始字符串 |
| URL编码 | 原始字符串 | URL编码字符串 |
| URL解码 | URL编码字符串 | 原始字符串 |
| HTML转义 | 含HTML标签文本 | 转义后文本 |
| HTML反转义 | 转义文本 | 原始文本 |
| Unicode编码 | 中文文本 | Unicode序列 |
| Unicode解码 | Unicode序列 | 中文文本 |

### 3.2 日期时间模块 (com.tools.domain.datetime)

| 工具 | 说明 |
|------|------|
| 时间戳转换 | Unix时间戳 ↔ 日期时间字符串 |
| 日期格式互转 | 多种日期格式间转换 |
| 时区转换 | 不同时区间的日期时间转换 |
| 日期间隔计算 | 计算两个日期间的天数、工作日 |

### 3.3 格式化模块 (com.tools.domain.formatter)

| 工具 | 说明 |
|------|------|
| JSON格式化 | 格式化JSON，缩进2/4空格可配置 |
| JSON压缩 | 压缩JSON去除空白 |
| XML格式化 | 格式化XML，缩进可配置 |
| SQL美化 | 格式化SQL语句 |

### 3.4 加解密模块 (com.tools.domain.crypto)

| 工具 | 算法 | 说明 |
|------|------|------|
| MD5 | MD5 | 消息摘要，不可逆 |
| SHA-1 | SHA-1 | 安全哈希，不可逆 |
| SHA-256 | SHA-256 | 安全哈希，不可逆 |
| AES加密 | AES | 对称加密，需密钥 |
| AES解密 | AES | 对称解密，需密钥 |
| DES加密 | DES | 对称加密，需密钥 |
| DES解密 | DES | 对称解密，需密钥 |
| RSA加密 | RSA | 非对称加密，公钥加密 |
| RSA解密 | RSA | 非对称加密，私钥解密 |

### 3.5 其他工具模块 (com.tools.domain.common)

| 工具 | 说明 |
|------|------|
| 颜色转换 | HEX ↔ RGB ↔ HSL 互转 |
| 进制转换 | 二进制/十进制/十六进制互转 |
| UUID生成 | 生成标准UUID |
| JWT解析 | 解析JWT Token各部分 |
| Cron解析 | 解析Cron表达式含义 |
| 正则测试 | 正则表达式匹配测试 |

---

## 4. API设计

### 4.1 RESTful API规范

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | /api/encode/base64 | Base64编码 |
| POST | /api/decode/base64 | Base64解码 |
| POST | /api/crypto/md5 | MD5哈希 |
| POST | /api/crypto/aes/encrypt | AES加密 |
| POST | /api/crypto/aes/decrypt | AES解密 |
| POST | /api/format/json | JSON格式化 |
| GET | /api/datetime/timestamp | 获取当前时间戳 |
| POST | /api/datetime/convert | 日期时间转换 |

### 4.2 统一响应格式

```json
{
  "success": true,
  "data": {},
  "message": "操作成功",
  "timestamp": 1743696000000
}
```

错误响应：
```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMIT_EXCEEDED",
    "message": "请求过于频繁，请稍后再试"
  },
  "timestamp": 1743696000000
}
```

---

## 5. 限流设计

### 5.1 滑动窗口限流算法

**原理**: 将时间窗口划分为多个小段，记录每个小段的请求次数，动态计算当前窗口内的总请求数。

**配置参数**:
- `窗口大小`: 60秒
- `子窗口数量`: 60个（每子窗口1秒）
- `最大请求数`: 60次/分钟

### 5.2 实现策略

- **存储**: 内存存储（ConcurrentHashMap），不持久化
- **粒度**: 按IP进行限流
- **集群**: 不支持，如需集群需引入Redis
- **响应**: 超出限制返回HTTP 429状态码

### 5.3 限流异常

| 场景 | 响应码 | 错误信息 |
|------|--------|----------|
| 超限 | 429 | 请求过于频繁，请稍后再试 |
| IP解析失败 | 200 | 使用默认限流阈值 |

---

## 6. 前端设计

### 6.1 页面结构

```
┌─────────────────────────────────────────────────┐
│  Header (Logo + 语言切换)                        │
├──────────────┬──────────────────────────────────┤
│              │                                  │
│  侧边导航     │        工具卡片区                 │
│  (分类列表)   │    ┌─────┐ ┌─────┐ ┌─────┐     │
│              │    │工具1 │ │工具2 │ │工具3 │     │
│  · 编码解码   │    └─────┘ └─────┘ └─────┘     │
│  · 日期时间   │                                  │
│  · 格式化     │                                  │
│  · 加解密     │                                  │
│  · 其他工具   │                                  │
│              │                                  │
├──────────────┴──────────────────────────────────┤
│  Footer (版权信息)                               │
└─────────────────────────────────────────────────┘
```

### 6.2 工具卡片交互

每个工具卡片包含：
1. 工具名称（多语言）
2. 输入区域（根据工具类型自适应）
3. 操作按钮
4. 输出区域
5. 复制/清空快捷操作

### 6.3 国际化

- **默认语言**: 中文
- **支持语言**: 中文(zh_CN)、英文(en_US)
- **切换方式**: Header右上角下拉切换
- **技术方案**: vue-i18n + 后端Spring i18n

---

## 7. 启动方式

### 7.1 环境要求

- JDK 21
- Maven 3.9+

### 7.2 构建步骤

```bash
# 1. 进入项目根目录
cd tools-service

# 2. 编译打包
mvn clean package -DskipTests

# 3. 运行
java -jar target/tools-service-1.0.0.jar
```

### 7.3 访问地址

- 本地开发: http://localhost:8080
- API端点: http://localhost:8080/api/*

---

## 8. 配置说明

### 8.1 application.yml 关键配置

```yaml
server:
  port: 8080

spring:
  messages:
    basename: messages
    default-locale: zh_CN

tools:
  ratelimit:
    enabled: true
    window-size: 60
    max-requests: 60
```

---

## 9. UML类图

详见 `docs/uml/architecture.puml`

### 9.1 核心类说明

| 类名 | 包路径 | 职责 |
|------|--------|------|
| EncoderService | com.tools.domain.encoder | Base64/URL/HTML/Unicode编解码 |
| CryptoService | com.tools.domain.crypto | 加解密服务 |
| DateTimeService | com.tools.domain.datetime | 日期时间处理 |
| FormatterService | com.tools.domain.formatter | JSON/XML/SQL格式化 |
| CommonService | com.tools.domain.common | 通用工具服务 |
| SlidingWindowRateLimiter | com.tools.infrastructure.ratelimit | 滑动窗口限流器 |
| ToolController | com.tools.web.controller | API控制器 |

---

## 10. 版本历史

| 版本 | 日期 | 变更内容 |
|------|------|----------|
| v1.0 | 2026-04-03 | 初始设计，多模块结构 |
| v2.0 | 2026-04-07 | 改为单模块结构，使用包名区分层次 |
