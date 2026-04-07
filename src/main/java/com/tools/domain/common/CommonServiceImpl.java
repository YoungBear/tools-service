package com.tools.domain.common;

import com.tools.domain.common.result.ToolResult;
import com.tools.domain.common.result.ToolResultFactory;

import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 通用工具服务实现 - 充血模型
 */
public class CommonServiceImpl implements CommonService {

    @Override
    public ToolResult hexToRgb(String hex) {
        if (hex == null || !hex.matches("^#[0-9A-Fa-f]{6}$")) {
            return ToolResultFactory.fail("Invalid HEX color format. Use #RRGGBB");
        }
        try {
            int r = Integer.parseInt(hex.substring(1, 3), 16);
            int g = Integer.parseInt(hex.substring(3, 5), 16);
            int b = Integer.parseInt(hex.substring(5, 7), 16);
            return ToolResultFactory.ok(String.format("rgb(%d, %d, %d)", r, g, b));
        } catch (Exception e) {
            return ToolResultFactory.fail("HEX to RGB conversion failed");
        }
    }

    @Override
    public ToolResult rgbToHex(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            return ToolResultFactory.fail("RGB values must be between 0 and 255");
        }
        return ToolResultFactory.ok(String.format("#%02x%02x%02x", r, g, b));
    }

    @Override
    public ToolResult rgbToHsl(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            return ToolResultFactory.fail("RGB values must be between 0 and 255");
        }
        float rNorm = r / 255f;
        float gNorm = g / 255f;
        float bNorm = b / 255f;

        float cMax = Math.max(rNorm, Math.max(gNorm, bNorm));
        float cMin = Math.min(rNorm, Math.min(gNorm, bNorm));
        float delta = cMax - cMin;

        float h = 0, s = 0, l = (cMax + cMin) / 2;

        if (delta != 0) {
            s = l > 0.5 ? delta / (2 - cMax - cMin) : delta / (cMax + cMin);
            if (cMax == rNorm) {
                h = ((gNorm - bNorm) / delta) % 6;
            } else if (cMax == gNorm) {
                h = (bNorm - rNorm) / delta + 2;
            } else {
                h = (rNorm - gNorm) / delta + 4;
            }
            h *= 60;
            if (h < 0) h += 360;
        }

        return ToolResultFactory.ok(String.format("hsl(%.0f, %.0f%%, %.0f%%)", h, s * 100, l * 100));
    }

    @Override
    public ToolResult hslToRgb(int h, int s, int l) {
        if (s < 0 || s > 100 || l < 0 || l > 100) {
            return ToolResultFactory.fail("H: 0-360, S/L: 0-100");
        }
        float sNorm = s / 100f;
        float lNorm = l / 100f;

        float c = (1 - Math.abs(2 * lNorm - 1)) * sNorm;
        float x = c * (1 - Math.abs((h / 60f) % 2 - 1));
        float m = lNorm - c / 2;

        float rPrime, gPrime, bPrime;
        if (h < 60) { rPrime = c; gPrime = x; bPrime = 0; }
        else if (h < 120) { rPrime = x; gPrime = c; bPrime = 0; }
        else if (h < 180) { rPrime = 0; gPrime = c; bPrime = x; }
        else if (h < 240) { rPrime = 0; gPrime = x; bPrime = c; }
        else if (h < 300) { rPrime = x; gPrime = 0; bPrime = c; }
        else { rPrime = c; gPrime = 0; bPrime = x; }

        int r = Math.round((rPrime + m) * 255);
        int g = Math.round((gPrime + m) * 255);
        int b = Math.round((bPrime + m) * 255);

        return ToolResultFactory.ok(String.format("rgb(%d, %d, %d)", r, g, b));
    }

    @Override
    public ToolResult binaryToDecimal(String binary) {
        if (binary == null || !binary.matches("^[01]+$")) {
            return ToolResultFactory.fail("Invalid binary format. Use only 0 and 1");
        }
        try {
            int decimal = Integer.parseInt(binary, 2);
            return ToolResultFactory.ok(String.valueOf(decimal));
        } catch (Exception e) {
            return ToolResultFactory.fail("Binary to decimal conversion failed");
        }
    }

    @Override
    public ToolResult decimalToBinary(String decimal) {
        if (decimal == null || !decimal.matches("^\\d+$")) {
            return ToolResultFactory.fail("Invalid decimal format. Use only digits");
        }
        try {
            int value = Integer.parseInt(decimal);
            String binary = Integer.toBinaryString(value);
            return ToolResultFactory.ok(binary);
        } catch (Exception e) {
            return ToolResultFactory.fail("Decimal to binary conversion failed");
        }
    }

    @Override
    public ToolResult hexToDecimal(String hex) {
        if (hex == null || !hex.matches("^[0-9A-Fa-f]+$")) {
            return ToolResultFactory.fail("Invalid hexadecimal format");
        }
        try {
            int decimal = Integer.parseInt(hex, 16);
            return ToolResultFactory.ok(String.valueOf(decimal));
        } catch (Exception e) {
            return ToolResultFactory.fail("Hex to decimal conversion failed");
        }
    }

    @Override
    public ToolResult decimalToHex(String decimal) {
        if (decimal == null || !decimal.matches("^\\d+$")) {
            return ToolResultFactory.fail("Invalid decimal format");
        }
        try {
            int value = Integer.parseInt(decimal);
            String hex = Integer.toHexString(value).toUpperCase();
            return ToolResultFactory.ok(hex);
        } catch (Exception e) {
            return ToolResultFactory.fail("Decimal to hex conversion failed");
        }
    }

    @Override
    public ToolResult generateUuid() {
        return ToolResultFactory.ok(UUID.randomUUID().toString());
    }

    @Override
    public ToolResult parseJwt(String token) {
        if (token == null || token.isEmpty()) {
            return ToolResultFactory.fail("Token cannot be null or empty");
        }
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return ToolResultFactory.fail("Invalid JWT format. Expected 3 parts separated by dots");
            }
            String header = new String(java.util.Base64.getUrlDecoder().decode(parts[0]));
            String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
            return ToolResultFactory.ok("Header:\n" + header + "\n\nPayload:\n" + payload);
        } catch (Exception e) {
            return ToolResultFactory.fail("JWT parsing failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult parseCron(String cronExpression) {
        if (cronExpression == null || cronExpression.isEmpty()) {
            return ToolResultFactory.fail("Cron expression cannot be null or empty");
        }
        String[] parts = cronExpression.trim().split("\\s+");
        if (parts.length < 5) {
            return ToolResultFactory.fail("Invalid cron expression. Expected at least 5 fields");
        }
        StringBuilder result = new StringBuilder();
        result.append("Minute: ").append(parts[0]).append("\n");
        result.append("Hour: ").append(parts[1]).append("\n");
        result.append("Day of month: ").append(parts[2]).append("\n");
        result.append("Month: ").append(parts[3]).append("\n");
        result.append("Day of week: ").append(parts[4]).append("\n");
        if (parts.length == 6) {
            result.append("Second: ").append(parts[5]);
        }
        return ToolResultFactory.ok(result.toString());
    }

    @Override
    public ToolResult testRegex(String pattern, String input) {
        if (pattern == null || pattern.isEmpty()) {
            return ToolResultFactory.fail("Pattern cannot be null or empty");
        }
        try {
            Pattern regex = Pattern.compile(pattern);
            if (input == null || input.isEmpty()) {
                return ToolResultFactory.ok("Pattern is valid but input is empty");
            }
            boolean matches = regex.matcher(input).matches();
            java.util.regex.Matcher matcher = regex.matcher(input);
            java.util.List<String> found = new java.util.ArrayList<>();
            while (matcher.find()) {
                found.add(matcher.group());
            }
            String result = String.format("Matches: %s\nMatch count: %d\nMatches: %s",
                    matches, found.size(), found);
            return ToolResultFactory.ok(result);
        } catch (PatternSyntaxException e) {
            return ToolResultFactory.fail("Invalid regex pattern: " + e.getMessage());
        }
    }
}
