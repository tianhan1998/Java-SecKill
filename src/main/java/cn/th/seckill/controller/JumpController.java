package cn.th.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tianh
 */
@Controller
public class JumpController {

    @GetMapping("/secGoodDetail/{id}")
    public String jumpToSecGoodDetail(Model m, @PathVariable String id){
        m.addAttribute("good_id",id);
        return "/goods/goods_detail";
    }
    @GetMapping("/orderDetail/{id}")
    public String jumpToOrderDetail(Model m, @PathVariable String id){
        m.addAttribute("order_id",id);
        return "/orders/order_detail";
    }
    @GetMapping("/buy/{id}")
    public String jumpToBuyNow(Model m, @PathVariable String id){
        m.addAttribute("good_id",id);
        return "/orders/buy_now";
    }
}
