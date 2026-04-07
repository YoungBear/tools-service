package com.tools.domain.crypto;

import com.tools.domain.common.result.ToolResult;
import com.tools.domain.common.result.ToolResultFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 加解密服务实现 - 充血模型
 */
public class CryptoServiceImpl implements CryptoService {

    @Override
    public ToolResult md5(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            String hash = bytesToHex(digest);
            return ToolResultFactory.ok(hash);
        } catch (NoSuchAlgorithmException e) {
            return ToolResultFactory.fail("MD5 algorithm not found");
        }
    }

    @Override
    public ToolResult sha1(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            String hash = bytesToHex(digest);
            return ToolResultFactory.ok(hash);
        } catch (NoSuchAlgorithmException e) {
            return ToolResultFactory.fail("SHA-1 algorithm not found");
        }
    }

    @Override
    public ToolResult sha256(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            String hash = bytesToHex(digest);
            return ToolResultFactory.ok(hash);
        } catch (NoSuchAlgorithmException e) {
            return ToolResultFactory.fail("SHA-256 algorithm not found");
        }
    }

    @Override
    public ToolResult aesEncrypt(String input, String key) {
        if (input == null || key == null) {
            return ToolResultFactory.fail("Input and key cannot be null");
        }
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] keyBytes16 = new byte[16];
            System.arraycopy(keyBytes, 0, keyBytes16, 0, Math.min(keyBytes.length, 16));
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes16, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return ToolResultFactory.ok(Base64.getEncoder().encodeToString(encrypted));
        } catch (Exception e) {
            return ToolResultFactory.fail("AES encryption failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult aesDecrypt(String input, String key) {
        if (input == null || key == null) {
            return ToolResultFactory.fail("Input and key cannot be null");
        }
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] keyBytes16 = new byte[16];
            System.arraycopy(keyBytes, 0, keyBytes16, 0, Math.min(keyBytes.length, 16));
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes16, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(input));
            return ToolResultFactory.ok(new String(decrypted, StandardCharsets.UTF_8));
        } catch (Exception e) {
            return ToolResultFactory.fail("AES decryption failed: Invalid key or ciphertext");
        }
    }

    @Override
    public ToolResult desEncrypt(String input, String key) {
        if (input == null || key == null) {
            return ToolResultFactory.fail("Input and key cannot be null");
        }
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] keyBytes8 = new byte[8];
            System.arraycopy(keyBytes, 0, keyBytes8, 0, Math.min(keyBytes.length, 8));
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes8, "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return ToolResultFactory.ok(Base64.getEncoder().encodeToString(encrypted));
        } catch (Exception e) {
            return ToolResultFactory.fail("DES encryption failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult desDecrypt(String input, String key) {
        if (input == null || key == null) {
            return ToolResultFactory.fail("Input and key cannot be null");
        }
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] keyBytes8 = new byte[8];
            System.arraycopy(keyBytes, 0, keyBytes8, 0, Math.min(keyBytes.length, 8));
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes8, "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(input));
            return ToolResultFactory.ok(new String(decrypted, StandardCharsets.UTF_8));
        } catch (Exception e) {
            return ToolResultFactory.fail("DES decryption failed: Invalid key or ciphertext");
        }
    }

    @Override
    public ToolResult rsaEncrypt(String input, String publicKey) {
        if (input == null || publicKey == null) {
            return ToolResultFactory.fail("Input and public key cannot be null");
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey key = keyFactory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return ToolResultFactory.ok(Base64.getEncoder().encodeToString(encrypted));
        } catch (Exception e) {
            return ToolResultFactory.fail("RSA encryption failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult rsaDecrypt(String input, String privateKey) {
        if (input == null || privateKey == null) {
            return ToolResultFactory.fail("Input and private key cannot be null");
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(input));
            return ToolResultFactory.ok(new String(decrypted, StandardCharsets.UTF_8));
        } catch (Exception e) {
            return ToolResultFactory.fail("RSA decryption failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult generateRsaKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
            String result = "Public Key:\n" + publicKey + "\n\nPrivate Key:\n" + privateKey;
            return ToolResultFactory.ok(result);
        } catch (NoSuchAlgorithmException e) {
            return ToolResultFactory.fail("RSA key generation failed");
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
