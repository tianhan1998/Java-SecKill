package cn.th.seckill.rabbit;

import cn.th.seckill.mapper.GoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class SecGoodConsumer {
    public static final String secGoods_topic_exchange="secGoods_topic_exchange";
    public static final String inc_order_queue="inc_order_queue";
    public static final String dec_order_queue="dec_order_queue";
    public static final String inc_routing_key="secGood.inc";
    public static final String dec_routing_key="secGood.dec";

    @Resource
    GoodsMapper goodsMapper;

    @RabbitListener(queues = dec_order_queue)
    public void decrementStock(Long id){
        goodsMapper.decStockCount(id);
        log.info("减库存消费者收到信息---->"+id);
    }
    @RabbitListener(queues = inc_order_queue)
    public void incrementStock(Long id){
        goodsMapper.incStockCount(id);
        log.info("增库存消费者收到信息---->"+id);
    }
}
