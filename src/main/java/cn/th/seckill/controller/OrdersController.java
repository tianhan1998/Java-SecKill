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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
                System.out.println(voLists);
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
}
