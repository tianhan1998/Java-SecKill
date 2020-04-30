package cn.th.seckill.controller;

import cn.th.seckill.entity.*;
import cn.th.seckill.entity.vo.OrdersVO;
import cn.th.seckill.service.GoodsService;
import cn.th.seckill.service.OrdersService;
import cn.th.seckill.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static cn.th.seckill.entity.Result.*;

/**
 * @author tianh
 */
@RestController
public class OrdersController {

    @Resource
    OrdersService ordersService;
    @Resource
    GoodsService goodsService;
    @Resource
    UserService userService;

    @Resource(name="redisTemplate")
    RedisTemplate<Object,Object> redisTemplate;

    /**
     * 根据id获取订单信息
     * @param id 订单id
     * @return json
     */
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
                        json.put("result", successResult("查找成功", vo));
                    }else{
                        json.put("result", failResult("获取购买人信息失败"));
                    }
                }else{
                    json.put("result", failResult("获取商品信息失败"));
                }
            }else{
                json.put("result", failResult("获取订单信息失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    /**
     * 获取所有订单
     * @param session 从session中取用户id
     * @return json
     */
    @GetMapping("/order")
    public JSONObject selectAllOrder(HttpSession session,@RequestParam(value = "pageNum", defaultValue = "1") String pageNum) {
        JSONObject json = new JSONObject();
        PageInfo<OrderInfo> list=null;
        List<OrdersVO> voLists=null;
        try {
            User loginUser= (User) session.getAttribute("login_user");
            list= ordersService.selectAllOrdersByUserId(loginUser.getId(), Integer.parseInt(pageNum));
            if(list.getSize()!=0){
                voLists=new ArrayList<>();
                for(OrderInfo orderInfo:list.getList()) {
                    Goods good=goodsService.selectGoodById(orderInfo.getGoodsId());
                        voLists.add(new OrdersVO(orderInfo) {{
                            this.setTrueName(loginUser.getTrueName());
                            if(good!=null) {
                            this.setGoodsImg(good.getGoodsImg());
                            this.setGoodsId(good.getId());
                            }else{
                                this.setGoodsName("null");//查询商品失败
                            }
                        }});
                }
                Result<Object> result= successResult("查找成功",voLists);
                result.setPageInfo(list);
                json.put("result", result);
            }else{
                json.put("result", failResult("查找失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    /**
     * 传送准备购买页面数据
     * @param session session取用户信息
     * @param goodId 取准备购买的商品信息
     * @return json
     */
    @GetMapping("/buyNow")
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
                    json.put("result", successResult("信息查找成功", ordersVO));
                } else {
                    json.put("result", failResult("商品信息查找失败"));
                }
            }else{
                json.put("result", failResult("参数错误"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    /**
     * 创建订单
     * @param order 订单，从请求体中取(axios post data)
     * @param session 从session中取用户信息
     * @return json
     */
    @PostMapping("/order")
    public JSONObject createOrder(@RequestBody OrderInfo order,HttpSession session) {
        JSONObject json = new JSONObject();
        try {
            Boolean result=redisTemplate.opsForSet().isMember("checkToken:goodsId:"+order.getGoodsId(),order.getToken());
            if(result!=null&&result) {
                User user = (User) session.getAttribute("login_user");
                order.setUserId(user.getId());
                if (ordersService.insertOrder(order)) {
                    json.put("result", successResult("下单成功，请尽快支付", order));
                } else {
                    json.put("result", failResult("商品已售完"));
                }
            }else{
                json.put("result",failResult("token有误，请进入商品界面重新购买"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    /**
     * 根据id删除订单
     * @param id 删除订单id
     * @return json
     */
    @DeleteMapping("/order/{id}")
    public JSONObject deleteOrder(@PathVariable String id){
        JSONObject json=new JSONObject();
        try{
            OrderInfo order=ordersService.selectOrderById(Long.parseLong(id));
            if(order!=null){
                if(ordersService.deleteOrder(order)){
                    json.put("result", successResult("删除订单成功"));
                }else{
                    json.put("result", failResult("删除订单失败"));
                }
            }else{
                json.put("result", failResult("查找订单信息失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    /**
     * 根据订单id开始付款
     * @param id 订单id
     * @return json
     */
    @PostMapping("/pay/{id}")
    public JSONObject pay(@PathVariable String id){
        JSONObject json=new JSONObject();
        try{
            if(!StringUtils.isEmpty(id)) {
                OrderInfo info=ordersService.selectOrderById(Long.parseLong(id));
                if(info!=null) {
                    Thread.sleep(1000);
                    if (ordersService.updateOrderStatusById(Long.parseLong(id)) > 0) {
                        json.put("result", successResult("支付成功"));
                    } else {
                        json.put("result", failResult("支付失败"));
                    }
                }else{
                    json.put("result", failResult("获取订单信息失败"));
                }
            }else{
                json.put("result", failResult("参数有误"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
}
