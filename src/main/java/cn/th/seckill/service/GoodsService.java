package cn.th.seckill.service;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.SeckillGoods;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author tianh
 */
public interface GoodsService {
    List<Goods> selectAllGood();
    PageInfo<SeckillGoods> selectAllSecGood(Integer pageNum);
    SeckillGoods selectSecGoodById(Long id);
    Goods selectGoodById(Long id);
    SeckillGoods selectSecGoodByGoodId(Long id);
    int selectStockCountByGoodId(Long id);
}
