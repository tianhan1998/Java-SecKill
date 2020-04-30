package cn.th.seckill;

import cn.th.seckill.service.OrdersService;
import cn.th.seckill.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = SeckillProjectApplication.class)
class SeckillProjectApplicationTests {

    @Resource
    RedisUtils utils;
    @Resource
    OrdersService service;
    @Test
    void contextLoads() {
//        System.out.println(service.selectAllOrdersByUserId(1L, pageNum));
    }

    @Test
    void setToken(){
        utils.setListToken(10000,1);
    }

}
