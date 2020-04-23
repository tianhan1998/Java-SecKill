package cn.th.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.th.seckill.mapper")
@SpringBootApplication
public class SeckillProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillProjectApplication.class, args);
    }

}
