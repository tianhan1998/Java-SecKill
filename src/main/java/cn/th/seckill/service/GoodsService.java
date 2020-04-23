package cn.th.seckill.service;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.SeckillGoods;

import java.util.List;

public interface GoodsService {
    List<Goods> selectAllGood();
    List<SeckillGoods> selectAllSecGood();
    SeckillGoods selectSecGoodById(Long id);
    Goods selectGoodById(Long id);
}
