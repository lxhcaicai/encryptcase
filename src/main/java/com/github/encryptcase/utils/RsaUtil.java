package com.github.encryptcase.utils;

import cn.hutool.core.io.FileUtil;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtil {
    public static void generateKeyToFile(String algorithm, String pubPath, String priPath) throws NoSuchAlgorithmException {
        // 创建密钥对生成器对象
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成私钥
        PrivateKey privateKey = keyPair.getPrivate();
        // 生成公钥
        PublicKey publicKey = keyPair.getPublic();
        // 获取私钥的字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();
        // 获取公钥的字节数组
        byte[] publicKeyEncoded = publicKey.getEncoded();
        // 对公钥金额私钥 进行 Base64 编码
        String privateKeyStr = new String(Base64.getEncoder().encode(privateKeyEncoded));
        String publicKeyStr = new String(Base64.getEncoder().encode(publicKeyEncoded));
        // 保存密钥
        File priFile = new File(priPath);
        if (!priFile.exists()) {
            FileUtil.writeString(privateKeyStr, new File(priPath), StandardCharsets.UTF_8);
        }
        File pubFile = new File(pubPath);
        if (!pubFile.exists()) {
            FileUtil.writeString(publicKeyStr, new File(pubPath), StandardCharsets.UTF_8);
        }
    }

    /**
     * 加密数据
     *
     * @param algorithm 算法
     * @param key       密钥
     * @param input     原文
     * @return
     */
    public static String encrypt(String algorithm, Key key, String input, int maxEncryptSize) throws Exception {
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 将原文转化为byte数组
        byte[] data = input.getBytes();
        // 总数据长度
        int total = data.length;
        // 输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        decodeByte(maxEncryptSize, cipher, data, total, baos);
        // 加密
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * 解密数据
     *
     * @param algorithm      算法
     * @param key            密钥
     * @param encrypted      加密的数据
     * @param maxDecryptSize 最大解密长度（需要根据实际情况进行调整）
     * @return
     */
    public static String decrypt(String algorithm, Key key, String encrypted, int maxDecryptSize) throws Exception {
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 由于密文进行了 Base64 编码, 在这里需要进行解码
        byte[] data = Base64.getDecoder().decode(encrypted);
        // 总数据长度
        int total = data.length;
        // 输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 分段处理数据
        decodeByte(maxDecryptSize, cipher, data, total, baos);
        return baos.toString();
    }


    /**
     * 从文件中加载私钥
     *
     * @param algorithm 算法
     * @param priPath   私钥文件路径
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String algorithm, String priPath) throws Exception {
        // 将文件内容转换为字符串
        String privateKeyStr = FileUtil.readString(new File(priPath), StandardCharsets.UTF_8);
        return getPrivateKeyFromString(algorithm, privateKeyStr);
    }

    /**
     * 从字符串中加载私钥
     *
     * @param algorithm 算法
     * @param str       字符串
     * @return 私钥
     */
    public static PrivateKey getPrivateKeyFromString(String algorithm, String str) throws Exception {
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 构建密钥规范 进行 Base64 解码
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(str));
        // 生成私钥
        return keyFactory.generatePrivate(spec);
    }

    /**
     * 从文件中加载公钥
     *
     * @param algorithm 算法
     * @param pubPath   公钥文件路径
     * @return 公钥
     */
    public static PublicKey getPublicKey(String algorithm, String pubPath) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 将文件内容转换为字符串
        String privateKeyStr = FileUtil.readString(new File(pubPath), StandardCharsets.UTF_8);
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 构建密钥规范 进行 Base64 解码
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
        // 生成私钥
        return keyFactory.generatePublic(spec);
    }

    /**
     * 从字符串中加载公钥
     *
     * @param algorithm 算法
     * @param str       字符串
     * @return 公钥
     */
    public static PublicKey getPublicKeyFromString(String algorithm, String str) throws Exception {
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 构建密钥规范 进行 Base64 解码
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(str));
        // 生成私钥
        return keyFactory.generatePublic(spec);
    }

    /**
     * 分段处理数据
     *
     * @param maxSize : 最大处理能力
     * @param cipher  : Cipher对象
     * @param data    : 要处理的byte数组
     * @param total   : 总数据长度
     * @param baos    : 输出流
     */
    public static void decodeByte(int maxSize, Cipher cipher, byte[] data, int total, ByteArrayOutputStream baos) throws Exception {
        // 偏移量
        int offset = 0;
        // 缓冲区
        byte[] buffer;
        // 如果数据没有处理完,就一直继续
        while (total - offset > 0) {
            // 如果剩余的数据 >= 最大处理能力, 就按照最大处理能力来加密数据
            if (total - offset >= maxSize) {
                // 加密数组
                buffer = cipher.doFinal(data, offset, maxSize);
                // 偏移量向右侧偏移最大数据能力个
                offset += maxSize;
            } else {
                // 如果剩余的数据 < 最大处理能力, 就按照剩余的个数来加密数据
                buffer = cipher.doFinal(data, offset, total - offset);
                // 偏移量设置为总数据长度, 这样可以跳出循环
                offset = total;
            }
            // 向输出流写入数据
            baos.write(buffer);
        }
    }
}