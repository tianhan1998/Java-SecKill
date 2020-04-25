package cn.th.seckill.controller;

import cn.th.seckill.entity.Result;
import cn.th.seckill.entity.User;
import cn.th.seckill.service.UserService;
import cn.th.seckill.utils.RSAUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService service;

    @PostMapping("/login")
    public JSONObject login(User user,HttpSession session){
        JSONObject json=new JSONObject();
        User log_user;
        try{
            if(!"".equals(user.getPhone())&&!"".equals(user.getPassword())){
                String de_pass=RSAUtils.decrypt(user.getPassword(), (String) session.getAttribute("privateKey"));
                user.setPassword(de_pass);
                log_user=service.selectUserByPhoneAndPassword(user);
                if(log_user!=null){
                    session.setAttribute("login_user",log_user);
                    json.put("result",Result.successResult("登陆成功"));
                }else{
                    json.put("result",Result.failResult("用户名或密码错误"));
                }
            }else{
                json.put("result",Result.failResult("用户名和密码不能为空"));
            }
        }catch(Exception e){
            e.printStackTrace();
            json.put("result",Result.exceptionResult(e.getMessage()));
        }
            return json;
    }

    @PostMapping("/register")
    public JSONObject reg(@Valid User user, BindingResult result, HttpSession session){
        JSONObject json=new JSONObject();
        try{
            if(result.hasErrors()){
                json.put("result",Result.failResult(result.getAllErrors()));
                System.out.println(result.getAllErrors());
            }else {
                User temp = service.selectUserByPhone(user.getPhone());
                if (temp != null) {
                    json.put("result", Result.failResult("此电话已被占用"));
                } else {
                    String privateKey = (String) session.getAttribute("privateKey");
                    if ("".equals(privateKey) || privateKey == null) {
                        json.put("result", Result.failResult("你还没有获取公钥，请刷新页面"));
                    } else {
                        String de_pass = RSAUtils.decrypt(user.getPassword(), privateKey);
                        System.out.println("解密---私钥" + privateKey + "解密后" + de_pass);
                        user.setPassword(de_pass);
                        if (service.insertUser(user) > 0) {
                            json.put("result", Result.successResult("成功注册"));
                        } else {
                            json.put("result", Result.failResult("数据库操作失败"));
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            json.put("result",Result.exceptionResult(e.getMessage()));
        }
        return json;

    }

    @GetMapping("/{phone}")
    public JSONObject checkUserByPhone(@PathVariable String phone){
        JSONObject json=new JSONObject();
        User user;
        try{
            user=service.selectUserByPhone(phone);
            if(user!=null) {
               json.put("result",Result.failResult("此电话已被占用"));
            }else{
                json.put("result",Result.successResult("此电话可以注册"));
            }
        }catch(Exception e){
            e.printStackTrace();
            json.put("result",Result.exceptionResult(e.getMessage()));
        }
            return json;
    }
}
