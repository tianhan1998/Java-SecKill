package cn.th.seckill.controller;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.Result;
import cn.th.seckill.entity.SeckillGoods;
import cn.th.seckill.entity.vo.GoodsVo;
import cn.th.seckill.service.GoodsService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.th.seckill.entity.Result.*;

/**
 * @author tianh
 */
@RestController
public class GoodsController {
    @Resource
    GoodsService service;

    @GetMapping("/secGoods")
    public JSONObject getAllSecGoods(@RequestParam(value = "pageNum",defaultValue = "1") String pageNum) {
        List<GoodsVo> voGoods;
        PageInfo<SeckillGoods> pageInfo;
        JSONObject json = new JSONObject();
        try {
            pageInfo = service.selectAllSecGood(Integer.parseInt(pageNum));
            if (pageInfo.getList().size() != 0) {
                voGoods = new ArrayList<>();
                for (SeckillGoods sGood:pageInfo.getList()) {
                    Goods tempGood=service.selectGoodById(sGood.getGoodsId());
                    voGoods.add(new GoodsVo(tempGood,sGood));
                }
                System.out.println(voGoods);
                Result<Object> result=successResult("查找成功",voGoods);
                result.setPageInfo(pageInfo);
                json.put("result",result);
            } else {
                json.put("result", failResult("未找到相关商品"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    @GetMapping("/secGoods/{id}")
    public JSONObject getSecGoodDetail(@PathVariable String id){
        JSONObject json=new JSONObject();
        SeckillGoods sec_good;
        Goods good;
        GoodsVo goodsVo;
        try{
            sec_good=service.selectSecGoodByGoodId(Long.parseLong(id));
            if(sec_good!=null){
                sec_good.setStockCount(service.selectStockCountByGoodId(sec_good.getGoodsId()));
                good=service.selectGoodById(sec_good.getGoodsId());
                goodsVo=new GoodsVo(good,sec_good);
                json.put("result", successResult("查找成功",goodsVo));
            }else{
                json.put("result", failResult("未找到相关商品"));
            }
        }catch(Exception e){
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
            return json;
    }

}
