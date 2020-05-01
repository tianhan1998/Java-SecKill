package cn.th.seckill.rabbit;

import cn.th.seckill.entity.OrderInfo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderProvider {

    private static final String order_queue ="insert_order_queue";
    private static final String order_topic_exchange="order_topic_exchange";
    private static final String insert_order_routing_key="order.insert";
    private static final String delete_order_routing_key="order.delete";

    @Resource
    RabbitTemplate template;

    public void sendOrder(OrderInfo order){
        template.convertAndSend(order_topic_exchange,insert_order_routing_key,order);
    }
    public void deleteOrder(Long id){
        template.convertAndSend(order_topic_exchange,delete_order_routing_key,id);
    }

}
