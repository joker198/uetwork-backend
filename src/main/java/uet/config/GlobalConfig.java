package uet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import uet.interceptor.TokenAuthenticationInterceptor;
import uet.stereotype.SendMail;

@Configuration
public class GlobalConfig  extends WebMvcConfigurerAdapter {
//    private static ApplicationContext context =
//            new ClassPathXmlApplicationContext("Spring-Mail.xml");
//    public static SendMail mailMessages = (SendMail) context.getBean("sendMail");
    public static String sourceAddress = "../" + "users_data";

    public static String dbName = "qly_sv";

    @Autowired
    TokenAuthenticationInterceptor tokenAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenAuthenticationInterceptor);
    }
}
