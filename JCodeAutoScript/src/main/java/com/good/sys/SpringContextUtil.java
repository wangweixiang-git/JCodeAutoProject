package com.good.sys;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取ApplicationContext和Object的工具类
 *
 */
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    /**
     * 获取applicationContext对象
     * 
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据bean的id来查找对象
     * 
     * @param id
     * @return
     */
    public static Object getBeanById(String id) {
        return applicationContext.getBean(id);
    }

    /**
     * 根据bean的class来查找对象
     * @param <T>
     * @param <T>
     * 
     * @param c
     * @return 
     * @return
     */
    public static <T> T getBeanByClass(Class<T> c) {
        return applicationContext.getBean(c);
    }

    /**
     * 根据bean的class来查找所有的对象(包括子类)
     * 
     * @param c
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getBeansByClass(Class c) {
        return applicationContext.getBeansOfType(c);
    }
}