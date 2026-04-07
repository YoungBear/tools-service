package com.tools.domain.crypto;

import com.tools.domain.common.result.ToolResult;

/**
 * 加解密服务接口 - 领域服务
 */
public interface CryptoService {

    /**
     * MD5哈希
     */
    ToolResult md5(String input);

    /**
     * SHA-1哈希
     */
    ToolResult sha1(String input);

    /**
     * SHA-256哈希
     */
    ToolResult sha256(String input);

    /**
     * AES加密
     */
    ToolResult aesEncrypt(String input, String key);

    /**
     * AES解密
     */
    ToolResult aesDecrypt(String input, String key);

    /**
     * DES加密
     */
    ToolResult desEncrypt(String input, String key);

    /**
     * DES解密
     */
    ToolResult desDecrypt(String input, String key);

    /**
     * RSA加密（公钥）
     */
    ToolResult rsaEncrypt(String input, String publicKey);

    /**
     * RSA解密（私钥）
     */
    ToolResult rsaDecrypt(String input, String privateKey);

    /**
     * 生成RSA密钥对
     */
    ToolResult generateRsaKeyPair();
}
