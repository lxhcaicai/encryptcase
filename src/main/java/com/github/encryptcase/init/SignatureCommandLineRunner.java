package com.github.encryptcase.init;

import com.github.encryptcase.utils.RsaUtil;
import com.github.encryptcase.utils.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;

@Order(3)
@Slf4j
@Component
public class SignatureCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // 原文
        // 6999:表示购物的价格
        // 10:表示购物的数量
        String input = "6999" + "10";
        // 加密算法
        String algorithm = "RSA";
        // 保存密钥到本地文件中
        // 读取私钥
        PrivateKey privateKey = RsaUtil.getPrivateKey(algorithm,"id_rsa");
        // 读取公钥
        PublicKey publicKey = RsaUtil.getPublicKey(algorithm, "id_rsa.pub");
        // 获取数字签名
        String signatureData = SignatureUtil.getSignature(input,"SHA256withRSA", privateKey);
        log.info("signatureData = {}", signatureData);
        // 校验数字签名
        boolean b = SignatureUtil.verifySignature(input, "SHA256withRSA", publicKey,signatureData);
        System.out.println("b = " + b);
    }
}
