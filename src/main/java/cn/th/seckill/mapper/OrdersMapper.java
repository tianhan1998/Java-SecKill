package cn.th.seckill.mapper;

import cn.th.seckill.entity.OrderInfo;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersMapper {
    List<OrderInfo> selectAllOrders();
    List<OrderInfo> selectAllOrdersByUserId(Long id);
    OrderInfo selectOrderById(Long id);
    int insertOrderInfo(OrderInfo info);
    int deleteOrderById(Long id);
}
