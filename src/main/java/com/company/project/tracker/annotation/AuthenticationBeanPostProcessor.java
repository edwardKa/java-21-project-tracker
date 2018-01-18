package com.company.project.tracker.annotation;

import com.company.project.tracker.exception.AuthenticationException;
import com.company.project.tracker.service.AuthenticationService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationBeanPostProcessor implements BeanPostProcessor {


    private Map<String, Class> requiredProxyBeans = new HashMap<>();

    @Autowired
    private AuthenticationService authenticationService;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();

        if (clazz.getAnnotation(RestController.class) != null && clazz.getAnnotation(NoAuthentication.class) == null) {
            requiredProxyBeans.put(beanName, clazz);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = requiredProxyBeans.get(beanName);
        if (clazz == null) {
            return bean;
        }


        return Enhancer.create(clazz, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] args) throws Throwable {

                if (method.getAnnotation(NoAuthentication.class) != null) {
                    return executeMethod(method, bean, args);
                }


                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                String sessionId = request.getHeader("Authorization");
                if (sessionId == null) {
                    throw new AuthenticationException("User is not authorized");
                }

                authenticationService.authenticate(sessionId);

                return executeMethod(method, bean, args);
            }
        });


    }

    private Object executeMethod(Method method, Object bean, Object... args) throws Throwable {
        try {
            return method.invoke(bean, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
