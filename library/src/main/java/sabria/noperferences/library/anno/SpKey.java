package sabria.noperferences.library.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2015-12-07  10:47
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SpKey {

    String _null = "32541023-4cfd-498d-tdg4-c200bb88745c";

    String key() default  _null;

    String _String() default _null;

    int _int() default 0;

    float _float() default 0F;

    long _long() default 0L;

    boolean _boolean() default false;
}
