package cn.th.seckill.service;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.entity.SeckillGoods;

import java.util.List;

public interface OrdersService {
    List<OrderInfo> selectAllOrders();
    List<OrderInfo> selectAllOrdersByUserId(Long id);
}
