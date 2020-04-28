package cn.th.seckill.service.impl;

import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.mapper.GoodsMapper;
import cn.th.seckill.mapper.OrdersMapper;
import cn.th.seckill.service.OrdersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@CacheConfig(cacheManager = "redisOrderCacheManager",cacheNames = "orders")
public class OrdersServiceImpl implements OrdersService {

    @Resource
    OrdersMapper ordersMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Resource(name = "redisOrderCacheManager")
    RedisCacheManager ordersCacheManager;

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
    @CacheEvict(key = "#info.id")
    public boolean insertOrder(OrderInfo info) {
        if(ordersMapper.insertOrderInfo(info)>0){
//            int a=1/0;
            if(goodsMapper.decStockCount(info.getGoodsId()) >0){
                ordersCacheManager.getCache("orders").evict(info.getGoodsId());
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    @CacheEvict(key = "#info.id")
    public boolean deleteOrder(OrderInfo info) {
        if(ordersMapper.deleteOrderById(info.getId())>0){
            return goodsMapper.incStockCount(info.getGoodsId())>0;
        }else{
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
