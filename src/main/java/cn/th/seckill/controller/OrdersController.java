package cn.th.seckill.controller;

import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.service.OrdersService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class OrdersController {

    @Resource
    OrdersService service;

    @GetMapping("/order/{id}")
    public JSONObject orderDetail(@PathVariable String id) {
        JSONObject json = new JSONObject();
        List<OrderInfo> list=null;
        try {
            list=service.selectAllOrdersByUserId(Long.parseLong(id));
            if(list.size()!=0){
                json.put("data",list);
                json.put("code",200);
                json.put("msg","查询成功");
            }else{
                json.put("code",404);
                json.put("msg","未找到");
                json.put("data",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 500);
            json.put("msg", e.getMessage());
        }
        return json;
    }
//    @GetMapping("/order/{id}")
//    public JSONObject selectAllOrder(@PathVariable String id) {
//        JSONObject json = new JSONObject();
//        List<OrderInfo> list=null;
//        try {
//            list=service.selectAllOrdersByUserId(Long.parseLong(id));
//            if(list.size()!=0){
//                json.put("data",list);
//                json.put("code",200);
//                json.put("msg","查询成功");
//            }else{
//                json.put("code",404);
//                json.put("msg","未找到");
//                json.put("data",null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            json.put("code", 500);
//            json.put("msg", e.getMessage());
//        }
//        return json;
//    }
}
