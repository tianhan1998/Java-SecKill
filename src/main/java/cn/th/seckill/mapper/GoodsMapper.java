package cn.th.seckill.mapper;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.SeckillGoods;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tianh
 */
@Repository
public interface GoodsMapper {
    List<Goods> selectAllGoods();
    List<SeckillGoods> selectAllSecGood();
    Goods selectGoodById(Long id);
    SeckillGoods selectSecGoodById(Long id);
    int decStockCount(Long id);
    int incStockCount(Long id);
    SeckillGoods selectSecGoodByGoodId(Long id);
    Integer selectStockCountByGoodId(Long id);
}
