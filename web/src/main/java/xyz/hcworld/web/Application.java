package xyz.hcworld.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类
 * @ClassName: ScaffoldingApplication
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */

@SpringBootApplication(scanBasePackages = "xyz.hcworld")
@MapperScan("xyz.hcworld.mapper")
public class Application {
    /**
     * 启动方法
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
