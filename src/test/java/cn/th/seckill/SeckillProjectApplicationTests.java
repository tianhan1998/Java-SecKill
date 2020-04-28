package cn.th.seckill;

import cn.th.seckill.service.OrdersService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SeckillProjectApplicationTests {

    @Resource
    OrdersService service;
    @Test
    void contextLoads() {
//        System.out.println(service.selectAllOrdersByUserId(1L, pageNum));
    }

}
