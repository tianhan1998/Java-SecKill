package cn.th.seckill.service;

import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface OrdersService {
    List<OrderInfo> selectAllOrders();
    PageInfo<OrderInfo> selectAllOrdersByUserId(Long id,int pageNum);
    OrderInfo selectOrderById(Long id);
    void insertOrder(OrderInfo info, User user);
    boolean deleteOrder(OrderInfo order);
    int updateOrderStatusById(Long id);
}
