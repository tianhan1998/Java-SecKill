package cn.th.seckill.service.impl;

import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.entity.Result;
import cn.th.seckill.entity.User;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    @Resource(name = "resultRedisTemplate")
    RedisTemplate<String, Result<Object>> resultRedisTemplate;

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
    @Async
    public void insertOrder(OrderInfo order, User user) {
        Long now = null;
        String token= (String) stringRedisTemplate.opsForValue().get("checkToken:goodsId:"+order.getGoodsId()+":userId:"+user.getId());
        if(token!=null) {
            if(token.equals(order.getToken())) {
                Boolean result = stringRedisTemplate.hasKey("stock:" + order.getGoodsId());
                if (result != null) {
                    now = stringRedisTemplate.opsForValue().decrement("stock:" + order.getGoodsId());
                    log.info("redis商品id---->" + order.getGoodsId() + "库存-1成功，现在库存---->" + now);
                }
                if (now != null && now < 0) {
                    log.error("库存为0或null，无法购买");
                } else if (now == null) {
                    log.error("查询库存出错");
                }
                resultRedisTemplate.opsForList().leftPush("result:goodsId:"+order.getGoodsId()+":userId:"+user.getId(), Result.successResult("下单成功!"));
                secGoodProvider.decrementStock(order.getGoodsId());
                orderProvider.sendOrder(order);
                stringRedisTemplate.opsForValue().set("checkToken:goodsId:" + order.getGoodsId() + ":userId:" + order.getUserId(), "used");
            }else{
                resultRedisTemplate.opsForList().leftPush("result:goodsId:"+order.getGoodsId()+":userId:"+user.getId(), Result.failResult("token错误"));
            }
        }else{
            resultRedisTemplate.opsForList().leftPush("result:goodsId:"+order.getGoodsId()+":userId:"+user.getId(), Result.failResult("未取得token,购买失败"));
        }
    }

    @Override
    @CacheEvict(key = "#info.id")
   public boolean deleteOrder(OrderInfo info) {
        Boolean exist = stringRedisTemplate.hasKey("stock:" + info.getGoodsId());
        Boolean delete = stringRedisTemplate.delete("checkToken:goodsId:" + info.getGoodsId() + ":userId:" + info.getUserId());
        log.info("tokenCheck清除成功--->" + info.getGoodsId() + "--->" + info.getUserId() + "--->" + info.getToken());
        Long redis = stringRedisTemplate.opsForList().rightPush("token:goodsId:" + info.getGoodsId(), info.getToken());
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
