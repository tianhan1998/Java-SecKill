package cn.th.seckill.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
    @Bean
    public ServletRegistrationBean<StatViewServlet> registrationBean(){
        StatViewServlet servlet=new StatViewServlet();
        ServletRegistrationBean<StatViewServlet> bean=new ServletRegistrationBean<>(servlet,"/druid/*");
        Map<String,String> param=new HashMap<>();
        param.put("loginUsername","admin");
        param.put("loginPassword","123456");
        bean.setInitParameters(param);
        return bean;
    }
    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>(new WebStatFilter());
        Map<String, String> param = new HashMap<>(2);
        param.put("exclusions", "*.js,*.css,/druid/*");
        bean.setUrlPatterns(Collections.singleton("/*"));
        bean.setInitParameters(param);
        return bean;
    }
}
