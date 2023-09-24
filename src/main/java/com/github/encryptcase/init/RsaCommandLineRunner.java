package com.github.encryptcase.init;

import com.github.encryptcase.utils.RsaUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class RsaCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String algorithm = "RSA";
        RsaUtil.generateKeyToFile(algorithm,"id_rsa.pub","id_rsa");
    }
}
