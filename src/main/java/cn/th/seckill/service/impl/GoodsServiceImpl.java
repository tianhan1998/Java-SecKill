package cn.th.seckill.service.impl;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.SeckillGoods;
import cn.th.seckill.mapper.GoodsMapper;
import cn.th.seckill.service.GoodsService;
import org.springframework.cache.annotation.Cacheable;
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
    public List<SeckillGoods> selectAllSecGood() {//TODO 分页
        return goodsMapper.selectAllSecGood();
    }

    @Override
    @Cacheable(cacheManager = "redisSecGoodsCacheManager",cacheNames = "secGoods")
    public SeckillGoods selectSecGoodById(Long id) {
        return goodsMapper.selectSecGoodById(id);
    }

    @Override
    @Cacheable(cacheNames = "goods")
    public Goods selectGoodById(Long id) {
        return goodsMapper.selectGoodById(id);
    }

    @Override
    @Cacheable(cacheManager = "redisSecGoodsCacheManager",cacheNames = "secGoods",key = "'goodId:'+#id")
    public SeckillGoods selectSecGoodByGoodId(Long id) {
        return goodsMapper.selectSecGoodByGoodId(id);
    }
}
