package cn.th.seckill.service;

import cn.th.seckill.entity.OrderInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface OrdersService {
    List<OrderInfo> selectAllOrders();
    PageInfo<OrderInfo> selectAllOrdersByUserId(Long id,int pageNum);
    OrderInfo selectOrderById(Long id);
    boolean insertOrder(OrderInfo info);
    boolean deleteOrder(OrderInfo order);
    int updateOrderStatusById(Long id);
}
