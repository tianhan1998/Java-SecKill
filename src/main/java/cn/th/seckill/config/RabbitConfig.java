package cn.th.seckill.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter myOrderMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

//    @Bean
//    public MessageConverter myObjectMessageConverter(){
//        return new SimpleMessageConverter();
//    }

}
