package cn.th.seckill.service.impl;

import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.mapper.GoodsMapper;
import cn.th.seckill.mapper.OrdersMapper;
import cn.th.seckill.service.OrdersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Resource
    OrdersMapper ordersMapper;
    @Resource
    GoodsMapper goodsMapper;

    @Override
    public List<OrderInfo> selectAllOrders() {
        return ordersMapper.selectAllOrders();
    }

    @Override
    public OrderInfo selectOrderById(Long id) {
        return ordersMapper.selectOrderById(id);
    }

    @Override
    @Transactional
    public boolean insertOrder(OrderInfo info) {
        if(ordersMapper.insertOrderInfo(info)>0){
//            int a=1/0;
            return goodsMapper.decStockCount(info.getGoodsId()) > 0;
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteOrder(OrderInfo info) {
        if(ordersMapper.deleteOrderById(info.getId())>0){
            return goodsMapper.incStockCount(info.getGoodsId())>0;
        }else{
            return false;
        }
    }

    @Override
    public List<OrderInfo> selectAllOrdersByUserId(Long id) {
        return ordersMapper.selectAllOrdersByUserId(id);
    }
}
