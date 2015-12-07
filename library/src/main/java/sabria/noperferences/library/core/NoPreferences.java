package sabria.noperferences.library.core;

import android.content.Context;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2015-10-27  14:57
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
public class NoPreferences {

    public static <T>  T  create (Context context , Class<T> instance){

        //判断是否是接口
        checkIsInterface(instance);
        //
        checkIsNotExtendingAnotherInterface(instance);

        //通过动态对象操作返回实例对象
        return (T)java.lang.reflect.Proxy.newProxyInstance(
                    instance.getClassLoader(),new Class[]{instance},
                            new ProxyHandler(context,instance,getMethodInfoCache(instance)));

    }




    private static <T> void checkIsNotExtendingAnotherInterface(Class<T> instance) {
        if (0 < instance.getInterfaces().length) {
            throw new IllegalArgumentException(
                    "Interfaces extending other interfaces are not supported at this time");
        }
    }

    private static <T> void checkIsInterface(Class<T> instance) {
        if(!instance.isInterface()){
            throw new IllegalArgumentException("Proxy must be defined by annotating an interface");
        }
    }

    private final static Map<Class<?>, Map<Method, MethodInfo>> proxyMethodInfoCache = new LinkedHashMap<>();
    private static Map<Method, MethodInfo> getMethodInfoCache(Class<?> instance) {
        synchronized (proxyMethodInfoCache){
            Map<Method, MethodInfo> methodInfoCache = proxyMethodInfoCache.get(instance);
            if(methodInfoCache == null){
                methodInfoCache = new LinkedHashMap<>();
                proxyMethodInfoCache.put(instance,methodInfoCache);
            }
            return methodInfoCache;
        }
    }


}
