package sabria.noperferences.library.core;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2015-10-27  15:08
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
public class ProxyHandler implements InvocationHandler {

    private static final String NAME = "name";
    private static final String MODE = "mode";

    private final Context context;
    private final Class<?> clazz;
    private final Map<Method, MethodInfo> methodDetailsCache;

    ProxyHandler(Context context, Class<?> instance, Map<Method, MethodInfo> methodDetailsCache) {
        this.context = context;
        this.clazz = instance;
        this.methodDetailsCache = methodDetailsCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }

        String name = null;
        int mode = 0;

        //拿到注解对象上的注解
        for (Annotation annotation : clazz.getAnnotations()) {
            Class<? extends Annotation> type = annotation.annotationType();
            for (Method annotMethod : type.getDeclaredMethods()) {
                if (NAME.equals(annotMethod.getName())) {
                    name = (String) annotMethod.invoke(annotation);
                } else if (MODE.equals(annotMethod.getName())) {
                    mode = (int) annotMethod.invoke(annotation);
                }
            }
        }

        //拿sp
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, mode);

        //拿MethodInfo
        MethodInfo methodInfo = getMethodInfo(methodDetailsCache, method);

        if (methodInfo.hasReturnType) {
            //getter
            return methodInfo.type.get(sharedPreferences, methodInfo);
        } else {
            //setter
            Class<?> paramType;
            if (null == args[0]) {
                paramType = method.getParameterTypes()[0];
            } else {
                paramType = args[0].getClass();
            }
            persistenceDelegates.get(paramType).persist(sharedPreferences.edit(), methodInfo.key, args[0]);
        }

        return null;
    }

    //拿MethodInfo
    public static MethodInfo getMethodInfo(Map<Method, MethodInfo> cache, Method method) {
        synchronized (cache) {
            //从缓存中拿
            MethodInfo methodInfo = cache.get(method);
            if (methodInfo == null) {
                methodInfo = new MethodInfo(method);
                cache.put(method, methodInfo);
            }
            return methodInfo;
        }

    }


    //----------
    private interface Persister {
        void persist(SharedPreferences.Editor editor, String key, Object what);
    }

    private static final Map<Class<?>, Persister> persistenceDelegates = new HashMap<Class<?>, Persister>()
    {
        {
            put(String.class, new Persister() {
                @Override
                public void persist(SharedPreferences.Editor editor, String key, Object what) {
                    editor.putString(key, (String) what).commit();
                }
            });
            put(Integer.class, new Persister() {
                @Override
                public void persist(SharedPreferences.Editor editor, String key, Object what) {
                    editor.putInt(key, (Integer) what).commit();
                }
            });
            put(Float.class, new Persister() {
                @Override
                public void persist(SharedPreferences.Editor editor, String key, Object what) {
                    editor.putFloat(key, (Float) what).commit();
                }
            });
            put(Long.class, new Persister() {
                @Override
                public void persist(SharedPreferences.Editor editor, String key, Object what) {
                    editor.putLong(key, (Long) what).commit();
                }
            });
            put(Boolean.class, new Persister() {
                @Override
                public void persist(SharedPreferences.Editor editor, String key, Object what) {
                    editor.putBoolean(key, (Boolean) what).commit();
                }
            });


        }

    };

}
