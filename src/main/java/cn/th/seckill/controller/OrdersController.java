package cn.th.seckill.controller;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.entity.Result;
import cn.th.seckill.entity.User;
import cn.th.seckill.entity.vo.OrdersVO;
import cn.th.seckill.service.GoodsService;
import cn.th.seckill.service.OrdersService;
import cn.th.seckill.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrdersController {

    @Resource
    OrdersService ordersService;
    @Resource
    GoodsService goodsService;
    @Resource
    UserService userService;

    @GetMapping("/order/{id}")
    public JSONObject orderDetail(@PathVariable String id) {
        JSONObject json = new JSONObject();
        OrdersVO vo;
        OrderInfo orderInfo;
        Goods good;
        User user;
        try {
            orderInfo= ordersService.selectOrderById(Long.parseLong(id));
            if(orderInfo!=null) {
                good = goodsService.selectGoodById(orderInfo.getGoodsId());
                if(good!=null) {
                    user=userService.selectUserById(orderInfo.getUserId());
                    if(user!=null) {
                        vo=new OrdersVO(good,orderInfo,user);
                        json.put("result", Result.successResult("查找成功", vo));
                    }else{
                        json.put("result",Result.failResult("获取购买人信息失败"));
                    }
                }else{
                    json.put("result",Result.failResult("获取商品信息失败"));
                }
            }else{
                json.put("result",Result.failResult("获取订单信息失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result",Result.exceptionResult(e.getMessage()));
        }
        return json;
    }
    @GetMapping("/order")
    public JSONObject selectAllOrder(HttpSession session) {
        JSONObject json = new JSONObject();
        List<OrderInfo> list=null;
        List<OrdersVO> voLists=null;
        try {
            User login_user= (User) session.getAttribute("login_user");
            list= ordersService.selectAllOrdersByUserId(login_user.getId());
            if(list.size()!=0){
                voLists=new ArrayList<>();
                for(OrderInfo orderInfo:list) {
                    Goods good=goodsService.selectGoodById(orderInfo.getGoodsId());
                        voLists.add(new OrdersVO(orderInfo) {{
                            this.setNickName(login_user.getNickname());
                            if(good!=null) {
                            this.setGoodsImg(good.getGoodsImg());
                            this.setGoodsId(good.getId());
                            }else{
                                this.setGoodsName("null");//查询商品失败
                            }
                        }});
                }
//                System.out.println(voLists);
                json.put("result", Result.successResult("查找成功", voLists));
            }else{
                json.put("result",Result.failResult("查找失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result",Result.exceptionResult(e.getMessage()));
        }
        return json;
    }
    @GetMapping("buyNow")
    public JSONObject buyNow(HttpSession session,String goodId){
        JSONObject json=new JSONObject();
        try{
            if(!StringUtils.isEmpty(goodId)) {
                Goods good = goodsService.selectGoodById(Long.parseLong(goodId));
                if (good != null) {
                    User user = (User) session.getAttribute("login_user");
                    OrdersVO ordersVO = new OrdersVO(good);
                    ordersVO.setTrueName(user.getTrueName());
                    ordersVO.setAddress(user.getAddress());
                    ordersVO.setPhone(user.getPhone());
                    json.put("result", Result.successResult("信息查找成功", ordersVO));
                } else {
                    json.put("result", Result.failResult("商品信息查找失败"));
                }
            }else{
                json.put("result",Result.failResult("参数错误"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", Result.exceptionResult(e.getMessage()));
        }
        return json;
    }
    @PostMapping("/pay")
    public JSONObject pay(@RequestBody OrderInfo order,HttpSession session){
         JSONObject json=new JSONObject();
         try{
             if(order!=null){
                 User user= (User) session.getAttribute("login_user");
                 order.setUserId(user.getId());
                if(ordersService.insertOrder(order)){
                    json.put("result",Result.successResult("下单成功，请尽快支付"));
                }else{
                    json.put("result",Result.failResult("下单失败"));
                }
             }else{
                 json.put("result",Result.failResult("参数有误"));
             }
         } catch (Exception e) {
             e.printStackTrace();
             json.put("result", Result.exceptionResult(e.getMessage()));
         }
         return json;
    }
    @DeleteMapping("/order/{id}")
    public JSONObject deleteOrder(@PathVariable String id){
        JSONObject json=new JSONObject();
        try{
            OrderInfo order=ordersService.selectOrderById(Long.parseLong(id));
            if(order!=null){
                if(ordersService.deleteOrder(order)){
                    json.put("result",Result.successResult("删除订单成功"));
                }else{
                    json.put("result",Result.failResult("删除订单失败"));
                }
            }else{
                json.put("result",Result.failResult("查找订单信息失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", Result.exceptionResult(e.getMessage()));
        }
        return json;
    }
}
