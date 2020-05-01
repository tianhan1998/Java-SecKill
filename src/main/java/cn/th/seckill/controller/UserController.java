package cn.th.seckill.controller;

import cn.th.seckill.entity.Result;
import cn.th.seckill.entity.User;
import cn.th.seckill.service.UserService;
import cn.th.seckill.utils.RSAUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static cn.th.seckill.entity.Result.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService service;

    @PostMapping("/login")
    public JSONObject login(User user,HttpSession session) {
        JSONObject json = new JSONObject();
        User logUser;
        try {
            if (!StringUtils.isEmpty(user.getPassword())&&!StringUtils.isEmpty(user.getPhone())) {
//                String privateKey = (String) session.getAttribute("privateKey");
//                if (StringUtils.isEmpty(privateKey)) {
//                    json.put("result", failResult("获取公钥失败，请刷新页面"));
//                } else {
//                    String dePass = RSAUtils.decrypt(user.getPassword(), (String) session.getAttribute("privateKey"));
//                    user.setPassword(dePass);
                    logUser = service.selectUserByPhoneAndPassword(user);
                    System.out.println(logUser);
                    if (logUser != null) {
                        session.setAttribute("login_user", logUser);
                        json.put("result", successResult("登陆成功"));

                    } else {
                        json.put("result", failResult("用户名或密码错误"));
                    }
//                }
            } else {
                json.put("result", failResult("用户名和密码不能为空"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    @PostMapping("/register")
    public JSONObject reg(@Valid User user, BindingResult result, HttpSession session){
        JSONObject json=new JSONObject();
        try{
            if(result.hasErrors()){
                json.put("result", failResult(result.getAllErrors()));
                System.out.println(result.getAllErrors());
            }else {
                User temp = service.selectUserByPhone(user.getPhone());
                if (temp != null) {
                    json.put("result", failResult("此电话已被占用"));
                } else {
                    String privateKey = (String) session.getAttribute("privateKey");
                    if (StringUtils.isEmpty(privateKey)) {
                        json.put("result", failResult("你还没有获取公钥，请刷新页面"));
                    } else {
                        String dePass = RSAUtils.decrypt(user.getPassword(), privateKey);
                        System.out.println("解密---私钥" + privateKey + "解密后" + dePass);
                        user.setPassword(dePass);
                        if (service.insertUser(user) > 0) {
                            json.put("result", successResult("成功注册"));
                        } else {
                            json.put("result", failResult("数据库操作失败"));
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;

    }
    @PostMapping("/updateUserAddress")
    public JSONObject updateAddress(HttpSession session,@RequestBody User user){
        JSONObject json=new JSONObject();
        try{
            if(!StringUtils.isEmpty(user.getTrueName())&&!StringUtils.isEmpty(user.getAddress())){
                User temp_user= (User) session.getAttribute("login_user");
                user.setId(temp_user.getId());
                if(service.updateUserAddressTrueNameById(user)>0){
                    json.put("result", successResult("修改成功"));
                    temp_user.setAddress(user.getAddress());
                    temp_user.setTrueName(user.getTrueName());
                    session.setAttribute("login_user",temp_user);
                    System.out.println("session用户重置成功");
                }else{
                    json.put("result", failResult("修改失败"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
    @GetMapping("/myAccount")
    public JSONObject checkMyAccount(HttpSession session) {
        JSONObject json = new JSONObject();
        User user;
        try {
            user= (User) session.getAttribute("login_user");
            user.setPassword("");
            json.put("result", successResult("查找成功",user));
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
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
               json.put("result", failResult("此电话已被占用"));
            }else{
                json.put("result", successResult("此电话可以注册"));
            }
        }catch(Exception e){
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
            return json;
    }
}
