package cn.th.seckill.service.impl;

import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.mapper.GoodsMapper;
import cn.th.seckill.mapper.OrdersMapper;
import cn.th.seckill.rabbit.OrderProvider;
import cn.th.seckill.rabbit.SecGoodProvider;
import cn.th.seckill.service.OrdersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheManager = "redisOrderCacheManager",cacheNames = "orders")
public class OrdersServiceImpl implements OrdersService {

    @Resource
    OrderProvider orderProvider;
    @Resource
    OrdersMapper ordersMapper;
    @Resource
    SecGoodProvider secGoodProvider;
    @Resource(name = "stringRedisTemplate")
    RedisTemplate<String, String> stringRedisTemplate;
    @Resource(name = "redisTemplate")
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<OrderInfo> selectAllOrders() {
        return ordersMapper.selectAllOrders();
    }

    @Override
    @Cacheable
    public OrderInfo selectOrderById(Long id) {
        return ordersMapper.selectOrderById(id);
    }

    @Override
    public boolean insertOrder(OrderInfo info) {
        Long now = null;
        Boolean result = stringRedisTemplate.hasKey("stock:" + info.getGoodsId());
        if (result != null) {
            now = stringRedisTemplate.opsForValue().decrement("stock:" + info.getGoodsId());
            log.info("redis商品id---->" + info.getGoodsId() + "库存-1成功，现在库存---->" + now);
        }
//        else if(result==null){
//            Integer stock=goodsMapper.selectStockCountByGoodId(info.getGoodsId());
//            stringRedisTemplate.opsForValue().set("stock:"+info.getGoodsId(),stock.toString());
//            log.info("redis商品id---->"+info.getGoodsId()+"插入库存成功,当前库存---->"+stock);
//            if(stock>0) {
//                now = stringRedisTemplate.opsForValue().decrement("stock:" + info.getGoodsId());
//            }
//        }
        if (now != null && now < 0) {
            log.error("库存为0或null，无法购买");
            return false;
        } else if (now == null) {
            log.error("查询库存出错");
            return false;
        }

        /*
        提供者发送信息
         */
        secGoodProvider.decrementStock(info.getGoodsId());
        orderProvider.sendOrder(info);
        redisTemplate.opsForValue().set("checkToken:goodsId:"+info.getGoodsId()+":userId:"+info.getUserId(),"used");
        return true;
//        if (goodsMapper.decStockCount(info.getGoodsId()) > 0) {
//            log.info("商品id---->" + info.getGoodsId() + "数据库库存-1成功");
//            boolean b = ordersMapper.insertOrderInfo(info) > 0;
//            if (b) {
//                return true;
//            } else {
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                log.error("数据库插入订单失败，回滚");
//                return false;
//            }
//        } else if(result!=null&&now!=null&&now>0){
//            stringRedisTemplate.opsForValue().increment("stock:" + info.getGoodsId());
//            log.error("数据库减库存失败，redis重新+1");
//            return false;
//        }else {
//            log.error("库存为0或null，无法购买");
//            return false;
//        }
    }

    @Override
    @CacheEvict(key = "#info.id")
    public boolean deleteOrder(OrderInfo info) {
        Boolean exist = stringRedisTemplate.hasKey("stock:" + info.getGoodsId());
        Boolean delete = redisTemplate.delete("checkToken:goodsId:" + info.getGoodsId() + ":userId:" + info.getUserId());
        log.info("tokenCheck清除成功--->" + info.getGoodsId() + "--->" + info.getUserId() + "--->" + info.getToken());
        Long redis = redisTemplate.opsForList().rightPush("token:goodsId:" + info.getGoodsId(), info.getToken());
        log.info("token:goodsId:" + info.getGoodsId() + "---->回插token---->" + info.getToken() + "成功");
        if (delete != null && delete && redis > 0) {
            if (exist != null && exist) {
                stringRedisTemplate.opsForValue().increment("stock:" + info.getGoodsId());
                log.info("redis商品id---->" + info.getGoodsId() + "库存+1成功");
            }
            /*
            发消息删数据库订单
             */
            secGoodProvider.incrementStock(info.getGoodsId());
            orderProvider.deleteOrder(info.getId());
            return true;
        } else {
            log.error("token:goodsId:" + info.getGoodsId() + "---->回插token---->" + info.getToken() + "失败");
            return false;
        }
    }


    @Override
    @CacheEvict
    public int updateOrderStatusById(Long id) {
        return ordersMapper.updateOrderStatusById(id);
    }

    @Override
    public PageInfo<OrderInfo> selectAllOrdersByUserId(Long id, int pageNum) {
        PageHelper.startPage(pageNum,5);
        return new PageInfo<>(ordersMapper.selectAllOrdersByUserId(id));
    }
}
