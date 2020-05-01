package cn.th.seckill.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SecGoodProvider {
    public static final String secGoods_topic_exchange="secGoods_topic_exchange";
    public static final String inc_order_queue="inc_order_queue";
    public static final String dec_order_queue="dec_order_queue";
    public static final String inc_routing_key="secGood.inc";
    public static final String dec_routing_key="secGood.dec";

    @Resource
    RabbitTemplate rabbitTemplate;

    public void decrementStock(Long id){
        rabbitTemplate.convertAndSend(secGoods_topic_exchange,dec_routing_key,id);

    }
    public void incrementStock(Long id){
        rabbitTemplate.convertAndSend(secGoods_topic_exchange,inc_routing_key,id);
    }
}
