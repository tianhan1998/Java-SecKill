package cn.th.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("cn.th.seckill.mapper")
@EnableRabbit
@EnableCaching
@SpringBootApplication
public class SeckillProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillProjectApplication.class, args);
    }

}
