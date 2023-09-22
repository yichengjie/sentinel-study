package com.yicj.webmvc.service.impl;

import com.yicj.webmvc.anno.HelloAnnotation;
import com.yicj.webmvc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
/**
 * @author: yicj
 * @date: 2023/9/17 10:14
 */
@Slf4j
@Service
@Qualifier
//@HelloAnnotation
public class DefaultUserService implements UserService, EnvironmentAware, InitializingBean, BeanDefinitionRegistryPostProcessor {

    private Environment environment ;

    @Override
    public String hello() {

        return "default hello world";
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment ;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("initializing bean");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        log.info("postProcessBeanDefinitionRegistry... ");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("postProcessBeanFactory... ");
    }
}
