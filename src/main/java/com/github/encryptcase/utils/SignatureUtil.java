package com.github.encryptcase.utils;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class SignatureUtil {

    /**
     * 生成签名
     *
     * @param input      原文
     * @param algorithm  算法
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String getSignature(String input, String algorithm, PrivateKey privateKey) throws Exception{
        // 获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        // 初始化签名
        signature.initSign(privateKey);
        // 传入原文
        signature.update(input.getBytes(StandardCharsets.UTF_8));
        // 开启签名
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }


    /**
     * 校验签名
     *
     * @param input         原文
     * @param algorithm     算法
     * @param publicKey     公钥
     * @param signatureData 签名
     * @return
     * @throws Exception
     */
    public static boolean verifySignature(String input, String algorithm, PublicKey publicKey, String signatureData) throws Exception {
        // 获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        // 初始化签名
        signature.initVerify(publicKey);
        // 传入原文
        signature.update(input.getBytes());
        // 校验数据
        return signature.verify(Base64.getDecoder().decode(signatureData.getBytes()));
    }
}
