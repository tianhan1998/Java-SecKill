package cn.th.seckill.rabbit;

import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.mapper.OrdersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class OrderConsumer {

    @Resource
    OrdersMapper mapper;

    private static final String INSERT_ORDER_QUEUE ="insert_order_queue";
    private static final String DELETE_ORDER_QUEUE ="delete_order_queue";
    private static final String ORDER_TOPIC_EXCHANGE ="order_topic_exchange";
    private static final String INSERT_ORDER_ROUTING_KEY ="order.insert";
    private static final String DELETE_ORDER_ROUTING_KEY ="order.delete";

    @RabbitListener(queues = INSERT_ORDER_QUEUE)
    private void insertOrder(OrderInfo order){
        mapper.insertOrderInfo(order);
        log.info("插入订单消费者接收到了消息---->"+order+"---->插入成功");
    }

    @RabbitListener(queues = DELETE_ORDER_QUEUE)
    private void deleteOrder(Long id){
        mapper.deleteOrderById(id);
        log.info("删除订单消费者接收到了消息---->"+id+"---->删除成功");
    }

}
