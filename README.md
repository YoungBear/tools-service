# 在线工具箱 (Online Tools)

一个基于 Spring Boot + Vue 3 的在线工具系统，提供编码解码、日期时间转换、格式化、加解密等多种实用工具。

## 功能特性

- **编码解码**: Base64、URL、HTML、Unicode
- **日期时间**: 时间戳转换、日期格式互转、时区转换、日期间隔计算
- **格式化**: JSON格式化、JSON压缩、XML格式化、SQL美化
- **加解密**: MD5、SHA-1、SHA-256、AES、DES、RSA
- **其他工具**: 颜色转换、进制转换、UUID生成、JWT解析、Cron表达式解析、正则测试
- **IP限流**: 滑动窗口算法防止DDOS攻击
- **国际化**: 支持中文/English

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.5.12 |
| Java版本 | OpenJDK 21 |
| 构建工具 | Apache Maven |
| 前端框架 | Vue 3 + Element Plus |
| 模板引擎 | Thymeleaf |
| 架构 | DDD（领域驱动设计）|

## 项目结构

```
tools-service/
├── src/main/java/com/tools/
│   ├── domain/                    # 领域层 - 充血模型
│   │   ├── encoder/              # 编码解码
│   │   ├── datetime/            # 日期时间
│   │   ├── formatter/           # 格式化
│   │   ├── crypto/             # 加解密
│   │   └── common/             # 通用工具
│   ├── application/             # 应用层
│   ├── infrastructure/          # 基础设施层
│   │   └── ratelimit/          # 滑动窗口限流
│   └── web/                    # 接口层
│       ├── controller/          # REST API
│       └── dto/                # 数据传输对象
└── src/main/resources/
    ├── templates/index.html     # 前端页面
    └── messages*.properties     # 国际化配置
```

## 快速开始

### 环境要求

- JDK 21+
- Maven 3.9+

### 构建运行

```bash
# 克隆项目后，进入目录
cd tools-service

# 编译打包
mvn clean package -DskipTests

# 运行
java -jar target/tools-service-1.0.0.jar
```

### 访问

- **首页**: http://localhost:18888
- **API端点**: http://localhost:18888/api/*

## API 文档

### 编码解码

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/encode/base64` | Base64编码 |
| POST | `/api/decode/base64` | Base64解码 |
| POST | `/api/encode/url` | URL编码 |
| POST | `/api/decode/url` | URL解码 |
| POST | `/api/encode/html` | HTML转义 |
| POST | `/api/decode/html` | HTML反转义 |
| POST | `/api/encode/unicode` | Unicode编码 |
| POST | `/api/decode/unicode` | Unicode解码 |

### 日期时间

| 方法 | 路径 | 描述 |
|------|------|------|
| GET/POST | `/api/datetime/timestamp` | 获取当前时间戳 |
| POST | `/api/datetime/timestamp-to-date` | 时间戳转日期（自动识别秒/毫秒）|
| POST | `/api/datetime/date-to-timestamp` | 日期转时间戳（支持纯日期格式）|
| POST | `/api/datetime/convert-format` | 日期格式转换 |
| POST | `/api/datetime/convert-timezone` | 时区转换 |
| POST | `/api/datetime/days-between` | 计算日期间隔 |

### 格式化

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/format/json` | JSON格式化 |
| POST | `/api/format/json/compress` | JSON压缩 |
| POST | `/api/format/xml` | XML格式化 |
| POST | `/api/format/sql` | SQL美化 |

### 加解密

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/crypto/md5` | MD5哈希 |
| POST | `/api/crypto/sha1` | SHA-1哈希 |
| POST | `/api/crypto/sha256` | SHA-256哈希 |
| POST | `/api/crypto/aes/encrypt` | AES加密 |
| POST | `/api/crypto/aes/decrypt` | AES解密 |
| POST | `/api/crypto/des/encrypt` | DES加密 |
| POST | `/api/crypto/des/decrypt` | DES解密 |
| POST | `/api/crypto/rsa/encrypt` | RSA加密 |
| POST | `/api/crypto/rsa/decrypt` | RSA解密 |
| GET | `/api/crypto/rsa/generate-key` | 生成RSA密钥对 |

### 通用工具

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/common/color/hex-to-rgb` | HEX转RGB |
| POST | `/api/common/color/rgb-to-hex` | RGB转HEX |
| POST | `/api/common/color/rgb-to-hsl` | RGB转HSL |
| POST | `/api/common/color/hsl-to-rgb` | HSL转RGB |
| POST | `/api/common/convert/binary-to-decimal` | 二进制转十进制 |
| POST | `/api/common/convert/decimal-to-binary` | 十进制转二进制 |
| POST | `/api/common/convert/hex-to-decimal` | 十六进制转十进制 |
| POST | `/api/common/convert/decimal-to-hex` | 十进制转十六进制 |
| GET | `/api/common/uuid/generate` | 生成UUID |
| POST | `/api/common/jwt/parse` | JWT解析 |
| POST | `/api/common/cron/parse` | Cron解析 |
| POST | `/api/common/regex/test` | 正则测试 |

### 请求响应示例

**请求**:
```bash
curl -X POST http://localhost:18888/api/encode/base64 \
  -H "Content-Type: application/json" \
  -d '{"input":"Hello World"}'
```

**成功响应**:
```json
{
  "success": true,
  "data": "SGVsbG8gV29ybGQ=",
  "timestamp": 1743696000000
}
```

**失败响应**:
```json
{
  "success": false,
  "error": {
    "code": "TOOL_EXECUTION_FAILED",
    "message": "Input cannot be null"
  },
  "timestamp": 1743696000000
}
```

**限流响应** (HTTP 429):
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

## 配置说明

配置文件位于 `src/main/resources/application.yml`:

```yaml
server:
  port: 18888

spring:
  messages:
    basename: messages
    default-locale: zh_CN

tools:
  ratelimit:
    enabled: true      # 是否启用限流
    window-size: 60   # 时间窗口大小(秒)
    max-requests: 60  # 窗口内最大请求数
```

## 限流策略

采用**滑动窗口算法**实现IP维度的请求限流：

- 窗口大小: 60秒
- 最大请求: 60次/分钟
- 存储方式: 内存 (ConcurrentHashMap)
- 集群支持: 不支持 (如需集群部署需引入Redis)

## 国际化

支持中文和英文，通过页面右上角切换语言。

- 中文: `messages.properties`
- 英文: `messages_en.properties`

## License

MIT License
