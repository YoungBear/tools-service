package com.tools.application;

import com.tools.domain.common.result.ToolResult;
import com.tools.domain.crypto.CryptoService;
import com.tools.domain.crypto.CryptoServiceImpl;

/**
 * 加解密应用服务 - 用例编排
 */
public class CryptoApplicationService {

    private final CryptoService cryptoService;

    public CryptoApplicationService() {
        this.cryptoService = new CryptoServiceImpl();
    }

    public ToolResult md5(String input) {
        return cryptoService.md5(input);
    }

    public ToolResult sha1(String input) {
        return cryptoService.sha1(input);
    }

    public ToolResult sha256(String input) {
        return cryptoService.sha256(input);
    }

    public ToolResult aesEncrypt(String input, String key) {
        return cryptoService.aesEncrypt(input, key);
    }

    public ToolResult aesDecrypt(String input, String key) {
        return cryptoService.aesDecrypt(input, key);
    }

    public ToolResult desEncrypt(String input, String key) {
        return cryptoService.desEncrypt(input, key);
    }

    public ToolResult desDecrypt(String input, String key) {
        return cryptoService.desDecrypt(input, key);
    }

    public ToolResult rsaEncrypt(String input, String publicKey) {
        return cryptoService.rsaEncrypt(input, publicKey);
    }

    public ToolResult rsaDecrypt(String input, String privateKey) {
        return cryptoService.rsaDecrypt(input, privateKey);
    }

    public ToolResult generateRsaKeyPair() {
        return cryptoService.generateRsaKeyPair();
    }
}
