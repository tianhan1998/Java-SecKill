package cn.th.seckill.controller;

import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.entity.RSABean;
import cn.th.seckill.entity.Result;
import cn.th.seckill.mapper.GoodsMapper;
import cn.th.seckill.mapper.OrdersMapper;
import cn.th.seckill.utils.RSAUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static cn.th.seckill.entity.Result.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class APIController {

    @Resource(name = "redisTemplate")
    RedisTemplate<Object,Object> redisTemplate;

    @GetMapping("/getPublicKey")
    public JSONObject getPublicKey(HttpSession session){
        RSAUtils rsa=null;
        JSONObject json=new JSONObject();
        try{
            rsa= RSAUtils.getInstance();
            if(rsa!=null){
                RSABean rsaBean=rsa.getRandomKey();
                session.setAttribute("privateKey",rsaBean.getPrivateKey());
                json.put("result", successResult("获取公钥成功",rsaBean.getPublicKey()));
            }else{
                json.put("result",Result.failResult("RSA实例出现问题"));
            }
        }catch(Exception e){
            e.printStackTrace();
            json.put("result",Result.exceptionResult(e.getMessage()));
        }
            return json;
    }
    @GetMapping("/getTokenThenBuy/{id}")
    public JSONObject getTokenThenBuy(@PathVariable String id) {
        JSONObject json = new JSONObject();
        try {
            String token = (String) redisTemplate.opsForList().leftPop("token:goodsId:" + id);
            if (token != null) {
                json.put("result",successResult("取得token成功",token));
            }else{
                json.put("result",failResult("请稍后再试"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

}
