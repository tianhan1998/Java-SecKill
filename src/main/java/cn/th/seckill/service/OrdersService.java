package cn.th.seckill.service;

import cn.th.seckill.entity.OrderInfo;

import java.util.List;

public interface OrdersService {
    List<OrderInfo> selectAllOrders();
    List<OrderInfo> selectAllOrdersByUserId(Long id);
    OrderInfo selectOrderById(Long id);
    boolean insertOrder(OrderInfo info);
    boolean deleteOrder(OrderInfo order);
}
