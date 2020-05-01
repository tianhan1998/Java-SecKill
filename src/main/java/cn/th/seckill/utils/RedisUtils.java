package cn.th.seckill.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtils{
    @Resource(name = "redisTemplate")
    private RedisTemplate<Object,Object> redisTemplate;

    public boolean setListToken(Integer stock, Integer goodsId) {
        try {
            for (int i = 0; i < stock; i++) {
                String token=UUIDUtils.uuid();
                redisTemplate.opsForList().leftPush("token:goodsId:" + goodsId, token);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
