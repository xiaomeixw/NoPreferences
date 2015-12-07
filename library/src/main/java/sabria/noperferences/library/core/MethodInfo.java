package sabria.noperferences.library.core;

import android.content.SharedPreferences;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import sabria.noperferences.library.anno.SpKey;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2015-10-27  15:10
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
public class MethodInfo {

    final static Map<String, Type> typeByNameLookup = new HashMap<>();
    final static Map<Class<?>, Type> typeByClassLookup = new HashMap<>();

    final Method method;

    final Class<?> returnType;
    final Type type;
    final boolean hasReturnType;
    String key;

    String _String;
    int _int;
    float _float;
    long _long;
    boolean _boolean;

    //静态构造器
    static {
        for (Type t : Type.values()){
            typeByNameLookup.put(t.name(),t);
            typeByClassLookup.put(t.clazz,t);
            typeByClassLookup.put(t.wrapperClazz,t);
        }
    }


    MethodInfo(Method method){
        this.method=method;

        for (Annotation methodAnnotation : method.getAnnotations()){

            Class<? extends Annotation> annotationType = methodAnnotation.annotationType();
            if(SpKey.class !=annotationType){
                //结束单次循环
                continue;
            }

            //ctrl + alt + t
            try {
                for(Method annotMethod : annotationType.getDeclaredMethods()){
                    final String methodName = annotMethod.getName();

                    if("key".equals(methodName)){
                        key=(String)annotMethod.invoke(methodAnnotation);
                        continue;
                    }

                    Type t = typeByNameLookup.get(methodName);
                    
                    Class<?> klazz = t.wrapperClazz;
                    Object v = annotMethod.invoke(methodAnnotation);
                    getClass().getDeclaredField(t.name()).set(this,klazz.cast(v));

                }
            } catch (IllegalArgumentException
                    | InvocationTargetException
                    | IllegalAccessException
                    | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        handlerKey(method);

        handlerNull();

        returnType=method.getReturnType();
        hasReturnType = returnType !=void.class;
        type=typeByClassLookup.get(returnType);



    }



    private static final String SET = "set";
    private static final String GET = "get";
    private void handlerKey(Method method) {
        if(key.equals(SpKey._null)){
            String name = method.getName();
            //方法名是不是set开头
            if (name.startsWith(SET)){
                key=name.replaceFirst(SET,"");
            }else if(name.startsWith(GET)){
                key=name.replaceFirst(GET,"");
            }else{
                //没有以set或者get开头
                throw new RuntimeException("@Accessor annotation on non setter/getter");
            }

        }

    }

    private void handlerNull() {
        if (_String.equals(SpKey._null)) {
            _String = null;
        }
    }


    private interface Getter {
        Object get(SharedPreferences sharedPreferences, MethodInfo methodInfo);
    }


    /**
     * 拿sp中的值
     */
    enum Type implements Getter{

        //都属于下面的类
        _String(String.class, String.class){
            @Override
            public Object get(SharedPreferences sharedPreferences, MethodInfo methodInfo) {
                return sharedPreferences.getString(methodInfo.key,methodInfo._String);
            }
        },
        _int(int.class,Integer.class){
            @Override
            public Object get(SharedPreferences sharedPreferences, MethodInfo methodInfo) {
                return sharedPreferences.getInt(methodInfo.key, methodInfo._int);
            }
        },
        _float(float.class, Float.class) {
            @Override
            public Object get(SharedPreferences sharedPreferences, MethodInfo methodInfo) {
                return sharedPreferences.getFloat(methodInfo.key, methodInfo._float);
            }
        },
        _long(long.class, Long.class) {
            @Override
            public Object get(SharedPreferences sharedPreferences, MethodInfo methodInfo) {
                return sharedPreferences.getLong(methodInfo.key, methodInfo._long);
            }
        },
        _boolean(boolean.class, Boolean.class) {
            @Override
            public Object get(SharedPreferences sharedPreferences, MethodInfo methodInfo) {
                return sharedPreferences.getBoolean(methodInfo.key, methodInfo._boolean);
            }
        };

        //枚举构造
        public final Class<?> clazz;
        public final Class<?> wrapperClazz;
        Type(Class<?> simpleClass, Class<?> wrapperClass) {
            this.clazz = simpleClass;
            this.wrapperClazz = wrapperClass;
        }
    }

}
