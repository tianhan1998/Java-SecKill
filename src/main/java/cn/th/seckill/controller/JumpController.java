package cn.th.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JumpController {

   /*
   registry.addViewController("/secGoodDetail").setViewName("goods_detail");
    */
    @RequestMapping("/secGoodDetail/{id}")
    public String jumpToSecGoodDetail(Model m, @PathVariable String id){
        m.addAttribute("good_id",id);
        return "goods_detail";
    }
}
