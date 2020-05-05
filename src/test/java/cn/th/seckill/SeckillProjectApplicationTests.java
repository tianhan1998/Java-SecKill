package cn.th.seckill;

import cn.th.seckill.entity.User;
import cn.th.seckill.mapper.UserMapper;
import cn.th.seckill.service.OrdersService;
import cn.th.seckill.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;

@SpringBootTest(classes = SeckillProjectApplication.class)
class SeckillProjectApplicationTests {

    @Resource
    RedisUtils utils;
    @Resource
    OrdersService service;
    @Resource
    RabbitAdmin admin;
    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() {
//        System.out.println(service.selectAllOrdersByUserId(1L, pageNum));
    }

    @Test
    void setToken(){
        utils.setListToken(20000,1);
    }

    @Test
    void setRabbitConfig(){
//        Queue insertOrder = new Queue("insert_order_queue",true);
//        admin.declareQueue(insertOrder);
//        TopicExchange order_topic_exchange=new TopicExchange("order_topic_exchange");
//        TopicExchange secGoods_topic_exchange=new TopicExchange("secGoods_topic_exchange");
//        admin.declareExchange(secGoods_topic_exchange);
//        admin.declareExchange(order_topic_exchange);
//        Binding binding=BindingBuilder.bind(insertOrder).to(order_topic_exchange).with("order.insert");
//        admin.declareBinding(binding);
//        Queue deleteOrder = new Queue("delete_order_queue",true);
//        admin.declareQueue(insertOrder);
//        Binding binding1=BindingBuilder.bind(insertOrder).to(order_topic_exchange).with("order.delete");
//        admin.declareBinding(binding);
//        Queue inc_order_queue= new Queue("inc_order_queue",true);
//        Queue dec_order_queue= new Queue("dec_order_queue",true);
//        admin.declareQueue(inc_order_queue);
//        admin.declareQueue(dec_order_queue);
//        Binding binding2=BindingBuilder.bind(inc_order_queue).to(secGoods_topic_exchange).with("secGood.inc");
//        Binding binding3=BindingBuilder.bind(dec_order_queue).to(secGoods_topic_exchange).with("secGood.dec");
//        admin.declareBinding(binding);
//        admin.declareBinding(binding2);
    }

    @Test
    void insertUser(){
        int i=10000;
        BigInteger telephone=new BigInteger("10000010001");
        while(i>0){
            User user=new User();
            telephone=telephone.add(new BigInteger("1"));
            user.setPhone(telephone.toString());
            user.setPassword("123456");
            user.setTrueName("test");
            user.setNickname("test");
            userMapper.insertUser(user);
            i--;
        }
    }

}
