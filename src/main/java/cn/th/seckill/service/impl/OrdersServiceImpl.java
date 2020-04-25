package cn.th.seckill.service.impl;

import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.mapper.OrdersMapper;
import cn.th.seckill.service.OrdersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Resource
    OrdersMapper ordersMapper;

    @Override
    public List<OrderInfo> selectAllOrders() {
        return ordersMapper.selectAllOrders();
    }

    @Override
    public OrderInfo selectOrderById(Long id) {
        return ordersMapper.selectOrderById(id);
    }

    @Override
    public List<OrderInfo> selectAllOrdersByUserId(Long id) {
        return ordersMapper.selectAllOrdersByUserId(id);
    }
}
