package cn.th.seckill.service.impl;

import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.mapper.GoodsMapper;
import cn.th.seckill.mapper.OrdersMapper;
import cn.th.seckill.service.OrdersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
@CacheConfig(cacheManager = "redisOrderCacheManager",cacheNames = "orders")
public class OrdersServiceImpl implements OrdersService {

    @Resource
    OrdersMapper ordersMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Resource(name = "stringRedisTemplate")
    RedisTemplate<String,String> stringRedisTemplate;

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
    @Transactional
    public boolean insertOrder(OrderInfo info) {
        String result = stringRedisTemplate.opsForValue().get("stock:" + info.getGoodsId());
        if (result != null && Long.parseLong(result) > 0) {
            stringRedisTemplate.opsForValue().decrement("stock:" + info.getGoodsId());
            log.info("redis商品id---->" + info.getGoodsId() + "库存-1成功，现在库存---->" + result);
        }else if(result==null){
            Integer stock=goodsMapper.selectStockCountByGoodId(info.getGoodsId());
            stringRedisTemplate.opsForValue().set("stock:"+info.getGoodsId(),stock.toString());
            log.info("redis商品id---->"+info.getGoodsId()+"插入库存成功,当前库存---->"+stock);
        }
        if (goodsMapper.decStockCount(info.getGoodsId()) > 0) {
            log.info("商品id---->" + info.getGoodsId() + "数据库库存-1成功");
            boolean b = ordersMapper.insertOrderInfo(info) > 0;
            if (b) {
                return true;
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error("数据库插入订单失败，回滚");
                return false;
            }
        } else if(result!=null&&Long.parseLong(result)>0){
            stringRedisTemplate.opsForValue().increment("stock:" + info.getGoodsId());
            log.error("数据库减库存失败，redis重新+1");
            return false;
        }else {
            log.error("库存为0或null，无法购买");
            return false;
        }
    }

    @Override
    @Transactional
    @CacheEvict(key = "#info.id")
    public boolean deleteOrder(OrderInfo info) {
        if (ordersMapper.deleteOrderById(info.getId()) > 0) {
            log.info("订单id---->" + info.getId() + "数据库删除成功");
            Boolean exist = stringRedisTemplate.hasKey("stock:" + info.getGoodsId());
            if (exist != null && exist) {
                stringRedisTemplate.opsForValue().increment("stock:" + info.getGoodsId());
                log.info("redis商品id---->" + info.getGoodsId() + "库存+1成功");
            }
            if (goodsMapper.incStockCount(info.getGoodsId()) > 0) {
                log.info("数据库商品id---->" + info.getGoodsId() + "库存+1成功");
                return true;
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error("增加库存失败，回滚");
                return false;
            }
        } else {
            log.error("数据库删除订单失败");
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
