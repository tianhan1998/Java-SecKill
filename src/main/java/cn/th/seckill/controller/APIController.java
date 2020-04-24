package cn.th.seckill.controller;

import cn.th.seckill.entity.RSABean;
import cn.th.seckill.entity.Result;
import cn.th.seckill.utils.RSAUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class APIController {

    @GetMapping("/getPublicKey")
    public JSONObject getPublicKey(HttpSession session){
        RSAUtils rsa=null;
        JSONObject json=new JSONObject();
        try{
            rsa= RSAUtils.getInstance();
            if(rsa!=null){
                RSABean rsaBean=rsa.getRandomKey();
                session.setAttribute("privateKey",rsaBean.getPrivateKey());
                json.put("result", new Result<>(rsaBean.getPublicKey()).successResult("获取公钥成功"));
            }else{
                json.put("result",new Result<>().failResult("RSA实例出现问题"));
            }
        }catch(Exception e){
            e.printStackTrace();
            json.put("result",new Result<>().exceptionResult(e.getMessage()));
        }
            return json;
    }
}
