package cn.th.seckill.service.impl;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.SeckillGoods;
import cn.th.seckill.mapper.GoodsMapper;
import cn.th.seckill.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    GoodsMapper goodsMapper;

    @Override
    public List<Goods> selectAllGood() {
        return goodsMapper.selectAllGoods();
    }

    @Override
    public List<SeckillGoods> selectAllSecGood() {
        return goodsMapper.selectAllSecGood();
    }

    @Override
    public SeckillGoods selectSecGoodById(Long id) {
        return goodsMapper.selectSecGoodById(id);
    }

    @Override
    public Goods selectGoodById(Long id) {
        return goodsMapper.selectGoodById(id);
    }
}
