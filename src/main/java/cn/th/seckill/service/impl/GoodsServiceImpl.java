package cn.th.seckill.service.impl;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.SeckillGoods;
import cn.th.seckill.mapper.GoodsMapper;
import cn.th.seckill.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Log
public class GoodsServiceImpl implements GoodsService {


    @Resource
    GoodsMapper goodsMapper;
    @Resource(name = "stringRedisTemplate")
    RedisTemplate<String,String>stringRedisTemplate;

    @Override
    public List<Goods> selectAllGood() {
        return goodsMapper.selectAllGoods();
    }

    @Override
    public PageInfo<SeckillGoods> selectAllSecGood(Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        return new PageInfo<>(goodsMapper.selectAllSecGood());
    }

    @Override
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
        int result;
        String stock=stringRedisTemplate.opsForValue().get("stock:"+id);
        if(stock==null){
            result=goodsMapper.selectStockCountByGoodId(id);
            log.info("从数据库中查出秒杀商品id"+id+"的库存为---->"+result);
            stringRedisTemplate.opsForValue().set("stock:"+id, String.valueOf(result));
            log.info("将商品id"+id+"的库存数"+result+"存入redis成功");
        }else{
            result=Integer.parseInt(stock);
            log.info("从redis中查出秒杀商品id"+id+"的库存为---->"+result);
        }
        SeckillGoods seckillGoods = goodsMapper.selectSecGoodByGoodId(id);
        seckillGoods.setStockCount(result);
        return seckillGoods;
    }

    @Override
    @Cacheable(cacheNames = "stock",cacheManager = "redisObjectCacheManager")
    public int selectStockCountByGoodId(Long id) {
        int result = goodsMapper.selectStockCountByGoodId(id);
        log.info("从数据库中查出秒杀商品id"+id+"的库存为---->"+result);
        return result;
    }
}
