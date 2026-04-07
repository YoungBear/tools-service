package com.tools.web.controller;

import com.tools.application.*;
import com.tools.domain.common.result.ToolResult;
import com.tools.infrastructure.ratelimit.RateLimiter;
import com.tools.web.dto.ToolResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ToolController {

    private final EncoderApplicationService encoderService;
    private final CryptoApplicationService cryptoService;
    private final DateTimeApplicationService dateTimeService;
    private final FormatterApplicationService formatterService;
    private final CommonApplicationService commonService;
    private final RateLimiter rateLimiter;

    public ToolController(RateLimiter rateLimiter) {
        this.encoderService = new EncoderApplicationService();
        this.cryptoService = new CryptoApplicationService();
        this.dateTimeService = new DateTimeApplicationService();
        this.formatterService = new FormatterApplicationService();
        this.commonService = new CommonApplicationService();
        this.rateLimiter = rateLimiter;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private ResponseEntity<ToolResponseDTO> checkRateLimit(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        if (!rateLimiter.isAllowed(clientIp)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(ToolResponseDTO.error("RATE_LIMIT_EXCEEDED", "Too many requests"));
        }
        return null;
    }

    private ToolResponseDTO toResponse(ToolResult result) {
        if (result.isSuccess()) {
            return ToolResponseDTO.success(result.getData());
        } else {
            return ToolResponseDTO.error("TOOL_EXECUTION_FAILED", result.getErrorMessage());
        }
    }

    @PostMapping("/encode/base64")
    public ResponseEntity<ToolResponseDTO> base64Encode(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(encoderService.base64Encode(body.get("input"))));
    }

    @PostMapping("/decode/base64")
    public ResponseEntity<ToolResponseDTO> base64Decode(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(encoderService.base64Decode(body.get("input"))));
    }

    @PostMapping("/encode/url")
    public ResponseEntity<ToolResponseDTO> urlEncode(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(encoderService.urlEncode(body.get("input"))));
    }

    @PostMapping("/decode/url")
    public ResponseEntity<ToolResponseDTO> urlDecode(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(encoderService.urlDecode(body.get("input"))));
    }

    @PostMapping("/encode/html")
    public ResponseEntity<ToolResponseDTO> htmlEscape(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(encoderService.htmlEscape(body.get("input"))));
    }

    @PostMapping("/decode/html")
    public ResponseEntity<ToolResponseDTO> htmlUnescape(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(encoderService.htmlUnescape(body.get("input"))));
    }

    @PostMapping("/encode/unicode")
    public ResponseEntity<ToolResponseDTO> unicodeEncode(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(encoderService.unicodeEncode(body.get("input"))));
    }

    @PostMapping("/decode/unicode")
    public ResponseEntity<ToolResponseDTO> unicodeDecode(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(encoderService.unicodeDecode(body.get("input"))));
    }

    @PostMapping("/crypto/md5")
    public ResponseEntity<ToolResponseDTO> md5(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(cryptoService.md5(body.get("input"))));
    }

    @PostMapping("/crypto/sha1")
    public ResponseEntity<ToolResponseDTO> sha1(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(cryptoService.sha1(body.get("input"))));
    }

    @PostMapping("/crypto/sha256")
    public ResponseEntity<ToolResponseDTO> sha256(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(cryptoService.sha256(body.get("input"))));
    }

    @PostMapping("/crypto/aes/encrypt")
    public ResponseEntity<ToolResponseDTO> aesEncrypt(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(cryptoService.aesEncrypt(body.get("input"), body.get("key"))));
    }

    @PostMapping("/crypto/aes/decrypt")
    public ResponseEntity<ToolResponseDTO> aesDecrypt(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(cryptoService.aesDecrypt(body.get("input"), body.get("key"))));
    }

    @PostMapping("/crypto/des/encrypt")
    public ResponseEntity<ToolResponseDTO> desEncrypt(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(cryptoService.desEncrypt(body.get("input"), body.get("key"))));
    }

    @PostMapping("/crypto/des/decrypt")
    public ResponseEntity<ToolResponseDTO> desDecrypt(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(cryptoService.desDecrypt(body.get("input"), body.get("key"))));
    }

    @PostMapping("/crypto/rsa/encrypt")
    public ResponseEntity<ToolResponseDTO> rsaEncrypt(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(cryptoService.rsaEncrypt(body.get("input"), body.get("publicKey"))));
    }

    @PostMapping("/crypto/rsa/decrypt")
    public ResponseEntity<ToolResponseDTO> rsaDecrypt(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(cryptoService.rsaDecrypt(body.get("input"), body.get("privateKey"))));
    }

    @GetMapping("/crypto/rsa/generate-key")
    public ResponseEntity<ToolResponseDTO> generateRsaKeyPair(HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(cryptoService.generateRsaKeyPair()));
    }

    @GetMapping("/datetime/timestamp")
    public ResponseEntity<ToolResponseDTO> getCurrentTimestamp(HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(dateTimeService.getCurrentTimestamp()));
    }

    @PostMapping("/datetime/timestamp-to-date")
    public ResponseEntity<ToolResponseDTO> timestampToDate(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(dateTimeService.timestampToDate(body.get("timestamp"), body.get("format"))));
    }

    @PostMapping("/datetime/date-to-timestamp")
    public ResponseEntity<ToolResponseDTO> dateToTimestamp(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(dateTimeService.dateToTimestamp(body.get("dateStr"), body.get("format"))));
    }

    @PostMapping("/datetime/convert-format")
    public ResponseEntity<ToolResponseDTO> convertDateFormat(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(dateTimeService.convertDateFormat(body.get("dateStr"), body.get("fromFormat"), body.get("toFormat"))));
    }

    @PostMapping("/datetime/convert-timezone")
    public ResponseEntity<ToolResponseDTO> convertTimezone(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(dateTimeService.convertTimezone(body.get("dateStr"), body.get("format"), body.get("fromZone"), body.get("toZone"))));
    }

    @PostMapping("/datetime/days-between")
    public ResponseEntity<ToolResponseDTO> calculateDaysBetween(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(dateTimeService.calculateDaysBetween(body.get("startDate"), body.get("endDate"), body.get("format"))));
    }

    @PostMapping("/format/json")
    public ResponseEntity<ToolResponseDTO> formatJson(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        int indent = body.containsKey("indent") ? Integer.parseInt(body.get("indent")) : 2;
        return ResponseEntity.ok(toResponse(formatterService.formatJson(body.get("input"), indent)));
    }

    @PostMapping("/format/json/compress")
    public ResponseEntity<ToolResponseDTO> compressJson(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(formatterService.compressJson(body.get("input"))));
    }

    @PostMapping("/format/xml")
    public ResponseEntity<ToolResponseDTO> formatXml(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        int indent = body.containsKey("indent") ? Integer.parseInt(body.get("indent")) : 2;
        return ResponseEntity.ok(toResponse(formatterService.formatXml(body.get("input"), indent)));
    }

    @PostMapping("/format/sql")
    public ResponseEntity<ToolResponseDTO> formatSql(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(formatterService.formatSql(body.get("input"))));
    }

    @PostMapping("/common/color/hex-to-rgb")
    public ResponseEntity<ToolResponseDTO> hexToRgb(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.hexToRgb(body.get("hex"))));
    }

    @PostMapping("/common/color/rgb-to-hex")
    public ResponseEntity<ToolResponseDTO> rgbToHex(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.rgbToHex(Integer.parseInt(body.get("r")), Integer.parseInt(body.get("g")), Integer.parseInt(body.get("b")))));
    }

    @PostMapping("/common/color/rgb-to-hsl")
    public ResponseEntity<ToolResponseDTO> rgbToHsl(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.rgbToHsl(Integer.parseInt(body.get("r")), Integer.parseInt(body.get("g")), Integer.parseInt(body.get("b")))));
    }

    @PostMapping("/common/color/hsl-to-rgb")
    public ResponseEntity<ToolResponseDTO> hslToRgb(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.hslToRgb(Integer.parseInt(body.get("h")), Integer.parseInt(body.get("s")), Integer.parseInt(body.get("l")))));
    }

    @PostMapping("/common/convert/binary-to-decimal")
    public ResponseEntity<ToolResponseDTO> binaryToDecimal(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.binaryToDecimal(body.get("binary"))));
    }

    @PostMapping("/common/convert/decimal-to-binary")
    public ResponseEntity<ToolResponseDTO> decimalToBinary(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.decimalToBinary(body.get("decimal"))));
    }

    @PostMapping("/common/convert/hex-to-decimal")
    public ResponseEntity<ToolResponseDTO> hexToDecimal(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.hexToDecimal(body.get("hex"))));
    }

    @PostMapping("/common/convert/decimal-to-hex")
    public ResponseEntity<ToolResponseDTO> decimalToHex(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.decimalToHex(body.get("decimal"))));
    }

    @GetMapping("/common/uuid/generate")
    public ResponseEntity<ToolResponseDTO> generateUuid(HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.generateUuid()));
    }

    @PostMapping("/common/jwt/parse")
    public ResponseEntity<ToolResponseDTO> parseJwt(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.parseJwt(body.get("token"))));
    }

    @PostMapping("/common/cron/parse")
    public ResponseEntity<ToolResponseDTO> parseCron(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.parseCron(body.get("expression"))));
    }

    @PostMapping("/common/regex/test")
    public ResponseEntity<ToolResponseDTO> testRegex(@RequestBody Map<String, String> body, HttpServletRequest request) {
        ResponseEntity<ToolResponseDTO> limitCheck = checkRateLimit(request);
        if (limitCheck != null) return limitCheck;
        return ResponseEntity.ok(toResponse(commonService.testRegex(body.get("pattern"), body.get("input"))));
    }
}
