package com.github.encryptcase.web;

import com.github.encryptcase.utils.RsaUtil;
import com.github.encryptcase.utils.SignatureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;

@Api(tags = "购物模块")
@RestController
public class ShoppingController {

    @ApiModelProperty(value = "购物")
    @PostMapping(value = "/buy")
    public String buy(Integer price, Integer num,String signature) {
        try {
            // 胡哦去公钥
            PublicKey publicKey = RsaUtil.getPublicKey("RSA", "id_rsa.pub");
            // 校验数字签名
            boolean b = SignatureUtil.verifySignature(String.valueOf(num).concat(String.valueOf(price)), "SHA256withRSA",publicKey, signature);
            if (b) {
                return "购物成功(*^▽^*)";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "购物失败o(╥﹏╥)o";
    }
}
