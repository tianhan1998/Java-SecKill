package cn.th.seckill.controller;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.SeckillGoods;
import cn.th.seckill.entity.vo.GoodsVo;
import cn.th.seckill.service.GoodsService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class GoodsController {
    @Resource
    GoodsService service;

    @GetMapping("/secGoods")
    public JSONObject getAllSecGoods() {
        List<SeckillGoods> sGoods;
        List<GoodsVo> voGoods=null;
        JSONObject json = new JSONObject();
        try {
            sGoods = service.selectAllSecGood();
            if (sGoods.size() != 0) {
                voGoods = new ArrayList<>();
                for (SeckillGoods sgood:sGoods) {
                    Goods temp_good=service.selectGoodById(sgood.getGoodsId());
                    voGoods.add(new GoodsVo(temp_good,sgood));
                }
                System.out.println(voGoods);
                json.put("data", voGoods);
                json.put("code",200);
                json.put("msg","查询成功");
            } else {
                json.put("data", null);
                json.put("code",404);
                json.put("msg","未找到秒杀商品");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code",500);
            json.put("msg",e.getMessage());
            json.put("data",null);
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
            sec_good=service.selectSecGoodById(Long.parseLong(id));
            if(sec_good!=null){
                good=service.selectGoodById(sec_good.getGoodsId());
                goodsVo=new GoodsVo(good,sec_good);
                json.put("data",goodsVo);
                json.put("code",200);
                json.put("msg","查找成功");
            }else{
                json.put("data",null);
                json.put("code",404);
                json.put("msg","未找到数据");
            }
        }catch(Exception e){
            e.printStackTrace();
            json.put("code",500);
            json.put("msg",e.getMessage());
            json.put("data",null);
        }
            return json;
    }

}
