package com.github.encryptcase.init;

import cn.hutool.core.collection.CollUtil;
import com.github.encryptcase.domain.User;
import com.github.encryptcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(1)
@Component
public class DefaultCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        List<User> userList = userService.findAll();
        if (CollUtil.isEmpty(userList)) {
            User user1 = new User();
            user1.setUsername("zhangsan");
            user1.setPassword("123456");
            userService.addUser(user1);
            User user2 = new User();
            user2.setUsername("lisi");
            user2.setPassword("123456");
            userService.addUser(user2);
        }
    }
}
