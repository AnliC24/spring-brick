package com.basic.example.plugin1.config;

import com.basic.example.main.config.PluginConfiguration;
import com.gitee.starblues.realize.PluginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @author starBlues
 * @version 1.0
 */
@Configuration
public class ConfigBean {

    @Bean
    public ConfigBeanTest c(PluginUtils pluginUtils){
        PluginConfiguration mainBean = pluginUtils.getMainBean(PluginConfiguration.class);
        System.out.println("configTest: mainBean=" + mainBean);
        ConfigBeanTest configBeanTest = new ConfigBeanTest();
        configBeanTest.name = "hello";
        configBeanTest.age = 16;
        return configBeanTest;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(new StringRedisSerializer());
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    public static class ConfigBeanTest{
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "ConfigBeanTest{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
