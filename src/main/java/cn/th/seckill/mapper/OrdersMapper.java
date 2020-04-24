package cn.th.seckill.mapper;

import cn.th.seckill.entity.OrderInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersMapper {
    List<OrderInfo> selectAllOrders();
    List<OrderInfo> selectAllOrdersByUserId(Long id);
}
