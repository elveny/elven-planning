package site.elven.planning.boot.base.configuration.web.error.handler.adapter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import site.elven.planning.boot.base.configuration.web.error.annotation.ControllerError;
import site.elven.planning.boot.base.configuration.web.error.handler.ControllerErrorHandler;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ControllerErrorHandlerAdapter implements ApplicationContextAware, InitializingBean {

    public static final ReflectionUtils.MethodFilter CONTROLLER_ERROR_METHODS = method ->
            AnnotatedElementUtils.hasAnnotation(method, ControllerError.class);

    @Nullable
    private ApplicationContext applicationContext;

    private final Map<String, ControllerErrorHandlerResolver> resolvers = new LinkedHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        ListableBeanFactory beanFactory = applicationContext;
        if (applicationContext instanceof ConfigurableApplicationContext) {
            // Use internal BeanFactory for potential downcast to ConfigurableBeanFactory above
            beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        }

        // 获取所有实现了 ControllerErrorHandler 的 Bean
        Map<String, ControllerErrorHandler> controllerErrorHandlerMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, ControllerErrorHandler.class);
        for (Map.Entry<String, ControllerErrorHandler> entry : controllerErrorHandlerMap.entrySet()) {
            ControllerErrorHandler errorHandler = entry.getValue();
            // 获取所有 @ControllerError 注解的方法
            Set<Method> methods = MethodIntrospector.selectMethods(errorHandler.getClass(), CONTROLLER_ERROR_METHODS);
            for (Method method : methods) {

                // 从方法中获取 @ControllerError 注解信息
                ControllerError annotation = AnnotationUtils.findAnnotation(method, ControllerError.class);
                if(annotation != null){
                    // 从 @ControllerError 注解中获取异常类
                    Class<? extends Throwable>[] exceptionClasses = annotation.value();
                    for (Class<? extends Throwable> exceptionClass : exceptionClasses) {
                        // 创建 ControllerErrorHandlerResolver 并缓存到 resolvers 中
                        resolvers.put(exceptionClass.getName(), new ControllerErrorHandlerResolver(errorHandler, method));
                    }
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Object handle(String exception, String errorMessage){
        // 从缓存中匹配 exception 异常类
        ControllerErrorHandlerResolver controllerErrorHandlerResolver = resolvers.get(exception);
        if(controllerErrorHandlerResolver != null){
            // 执行异常处理器
            return controllerErrorHandlerResolver.handle(errorMessage);
        }

        return null;
    }
}
